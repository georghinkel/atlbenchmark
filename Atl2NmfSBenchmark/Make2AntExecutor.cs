using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using Make2AntNamespace;
using Make2AntNamespace.Make;
using NMF.Models;
using NMF.Models.Repository;
using NMF.Synchronizations.ATLBenchmark.ScenarioGeneration;
using NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Make;
using NMF.Transformations;
using InputModelContainer = Make2AntNamespace.InputModelContainer;
using OutputModelContainer = Make2AntNamespace.OutputModelContainer;

namespace NMF.Synchronizations.ATLBenchmark
{
    class Make2AntExecutor
    {
        private static Make2Ant make2Ant = new Make2Ant(null, null);

        /// <summary>
        /// Measures the polling algorithm versus the incremental algorithm for the given parameters
        /// </summary>
        /// <param name="sizes">The values for n</param>
        /// <param name="iterations">The amount of iterations</param>
        /// <param name="workloadSize">The workload size</param>
        public static void Measure(int[] sizes, int iterations, int workloadSize)
        {
            if(Directory.Exists("makeInputModels"))
                Directory.Delete("makeInputModels", true);

            var times = new long[sizes.Length, iterations, 5];

            for (int sizeIdx = 0; sizeIdx < sizes.Length; sizeIdx++)
            {
                var n = sizes[sizeIdx];

                for (int iteration = 0; iteration < iterations; iteration++)
                {
                    Console.WriteLine("Generating workload for n={0},iteration={1}...", n, iteration);
                    RunIteration(times, n, sizeIdx, iteration, workloadSize);

                    GC.Collect();
                    GC.WaitForPendingFinalizers();
                }
            }

            WriteResultsToCsv(sizes, iterations, times);
        }

        private static void RunIteration(long[,,] times, int n, int sizeIdx, int iteration, int workloadSize)
        {
            var watch = new Stopwatch();
            var makeModel = MakeGenerator.GenerateMake(n);
            var startRule = make2Ant.SynchronizationRule<Make2Ant.Model2ModelMainRule>();

            var inputBatchModelContainer = new InputModelContainer(CopyMakeModel(makeModel));
            var outputBatchModelContainer = new OutputModelContainer(new Model());

            Make2Ant.InputModelContainer = inputBatchModelContainer;
            Make2Ant.OutputModelContainer = outputBatchModelContainer;

            watch.Start();
            make2Ant.Synchronize(startRule, ref inputBatchModelContainer, ref outputBatchModelContainer, SynchronizationDirection.LeftToRight, ChangePropagationMode.None);
            watch.Stop();
            times[sizeIdx, iteration, 0] = watch.Elapsed.Ticks * 100;

            var inputIncModelContainer = new InputModelContainer(CopyMakeModel(makeModel));
            var outputIncModelContainer = new OutputModelContainer(new Model());

            Make2Ant.InputModelContainer = inputBatchModelContainer;
            Make2Ant.OutputModelContainer = outputBatchModelContainer;

            watch.Restart();
            make2Ant.Synchronize(startRule, ref inputIncModelContainer, ref outputIncModelContainer, SynchronizationDirection.LeftToRight, ChangePropagationMode.OneWay);
            watch.Stop();
            times[sizeIdx, iteration, 1] = watch.Elapsed.Ticks * 100;

            var workload = MakeGenerator.GenerateChangeWorkload(makeModel, workloadSize);
            PlayBatchNet(times, sizeIdx, iteration, startRule, watch, ref inputBatchModelContainer, ref outputBatchModelContainer, workload);
            PlayIncremental(times, sizeIdx, iteration, watch, inputIncModelContainer, outputIncModelContainer, workload);
            PlayChangesOnly(times, sizeIdx, iteration, watch, CopyMakeModel(makeModel), workload);

            var inputModelContainer = new InputModelContainer(CopyMakeModel(makeModel));
            var outputModelContainer = new OutputModelContainer(new Model());

            Make2Ant.InputModelContainer = inputBatchModelContainer;
            Make2Ant.OutputModelContainer = outputBatchModelContainer;

            CreateModelsForAtlTransformation(n, iteration, workload, startRule, inputModelContainer, outputModelContainer);
        }

        private static void CreateModelsForAtlTransformation(int n, int iteration, List<MakeWorkloadAction> workload, Make2Ant.Model2ModelMainRule startRule, InputModelContainer inputModelContainer, OutputModelContainer outputModelContainer)
        {
            var outputRepository = new ModelRepository();

            Directory.CreateDirectory("makeInputModels");
            Directory.CreateDirectory("makeInputModels\\" + n);
            Directory.CreateDirectory("makeInputModels\\" + n + "\\" + iteration);

            make2Ant.Synchronize(startRule, ref inputModelContainer, ref outputModelContainer, SynchronizationDirection.LeftToRight, ChangePropagationMode.OneWay);

            for (int index = 0; index < workload.Count; index++)
            {
                var item = workload[index];
                item.Perform(inputModelContainer.IN);
                outputRepository.Save(inputModelContainer.IN, "makeInputModels\\" + n + "\\" + iteration + "\\" + "inputModel" + index + ".xmi");
            }
        }

        private static void PlayIncremental(long[,,] times, int sizeIdx, int iteration, Stopwatch watch, InputModelContainer inputModelContainer, OutputModelContainer outputModelContainer, List<MakeWorkloadAction> workload)
        {
            Make2Ant.InputModelContainer = inputModelContainer;
            Make2Ant.OutputModelContainer = outputModelContainer;

            watch.Restart();
            foreach (var item in workload)
            {
                item.Perform(inputModelContainer.IN);
            }
            watch.Stop();

            times[sizeIdx, iteration, 3] = watch.Elapsed.Ticks * 100;
        }

        private static void PlayChangesOnly(long[,,] times, int sizeIdx, int iteration, Stopwatch watch, Model inputModel, List<MakeWorkloadAction> workload)
        {
            watch.Restart();
            foreach (var item in workload)
            {
                item.Perform(inputModel);
            }
            watch.Stop();

            times[sizeIdx, iteration, 4] = watch.Elapsed.Ticks * 100;
        }

        private static void PlayBatchNet(long[,,] times, int sizeIdx, int iteration, Make2Ant.Model2ModelMainRule startRule, Stopwatch watch, ref InputModelContainer inputModelContainer, ref OutputModelContainer outputModelContainer, List<MakeWorkloadAction> workload)
        {
            Make2Ant.InputModelContainer = inputModelContainer;
            Make2Ant.OutputModelContainer = outputModelContainer;

            watch.Restart();
            foreach (var item in workload)
            {
                item.Perform(inputModelContainer.IN);
                RerunBatchSynchronization(startRule, ref inputModelContainer, ref outputModelContainer);
            }
            watch.Stop();

            times[sizeIdx, iteration, 2] = watch.Elapsed.Ticks * 100;
        }

        private static void RerunBatchSynchronization(Make2Ant.Model2ModelMainRule startRule, ref InputModelContainer inputModelContainer, ref OutputModelContainer outputModelContainer)
        {
            outputModelContainer = new OutputModelContainer(new Model());

            make2Ant.Synchronize(startRule, ref inputModelContainer, ref outputModelContainer, SynchronizationDirection.LeftToRight, ChangePropagationMode.None);
        }

        private static Model CopyMakeModel(Model sourceModel)
        {
            var targetModel = new Model();

            var makefile = (Makefile) sourceModel.RootElements[0];

            var newMakefile = new Makefile
            {
                ID = makefile.ID,
                Name = makefile.Name,
                Comment = new Comment()
                {
                    ID = makefile.Comment.ID,
                    Text = makefile.Comment.Text
                }
            };

            foreach (var element in makefile.Elements)
            {
                if (element is Rule)
                {
                    var rule = element as Rule;

                    var newRule = new Rule
                    {
                        ID = rule.ID,
                        Name = rule.Name
                    };

                    foreach (var shellLine in rule.ShellLines)
                    {
                        var newShellLine = new ShellLine
                        {
                            ID = shellLine.ID,
                            Display = shellLine.Display,
                            Command = shellLine.Command
                        };

                        newRule.ShellLines.Add(newShellLine);
                    }

                    foreach (var dependency in rule.Dependencies)
                    {
                        if (dependency is FileDep)
                        {
                            var fileDep = dependency as FileDep;

                            var newFileDep = new FileDep
                            {
                                ID = fileDep.ID,
                                Name = fileDep.Name
                            };

                            newRule.Dependencies.Add(newFileDep);
                        }
                    }

                    newMakefile.Elements.Add(newRule);
                }
                else if (element is Macro)
                {
                    var macro = element as Macro;

                    var newMacro = new Macro
                    {
                        ID = macro.ID,
                        Name = macro.Name,
                        Value = macro.Value
                    };

                    newMakefile.Elements.Add(newMacro);
                }
            }

            targetModel.RootElements.Add(newMakefile);
            return targetModel;
        }

        /// <summary>
        /// Writes the results to a file
        /// </summary>
        private static void WriteResultsToCsv(int[] sizes, int iterations, long[,,] times)
        {
            using (var sw = new StreamWriter("makeResults.csv"))
            {
                sw.WriteLine("Size;Run;InitBatch;InitInc;UpdatesBatch;UpdatesInc;ChangesOnly");
                for (int sizeIdx = 0; sizeIdx < sizes.Length; sizeIdx++)
                {
                    var n = sizes[sizeIdx];
                    for (int iteration = 0; iteration < iterations; iteration++)
                    {
                        var batchTime = (times[sizeIdx, iteration, 0] + times[sizeIdx, iteration, 2]);
                        var incTime = (times[sizeIdx, iteration, 1] + times[sizeIdx, iteration, 3]);

                        sw.WriteLine($"{n};{iteration};{times[sizeIdx, iteration, 0]};{times[sizeIdx, iteration, 1]};{times[sizeIdx, iteration, 2]};{times[sizeIdx, iteration, 3]};{times[sizeIdx, iteration, 4]}");
                    }
                }
            }
        }
    }
}
