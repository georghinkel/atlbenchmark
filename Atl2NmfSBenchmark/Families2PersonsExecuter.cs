using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using Families2PersonsNamespace;
using Families2PersonsNamespace.Families;
using NMF.Expressions.Linq;
using NMF.Models;
using NMF.Models.Repository;
using NMF.Synchronizations.ATLBenchmark.ScenarioGeneration;
using NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Families;
using NMF.Transformations;

namespace NMF.Synchronizations.ATLBenchmark
{
    class Families2PersonsExecuter
    {
        private static Families2Persons families2Persons = new Families2Persons();

        /// <summary>
        /// Measures the polling algorithm versus the incremental algorithm for the given parameters
        /// </summary>
        /// <param name="sizes">The values for n</param>
        /// <param name="iterations">The amount of iterations</param>
        /// <param name="workloadSize">The workload size</param>
        public static void Measure(int[] sizes, int iterations, int workloadSize)
        {
            if (Directory.Exists("familyInputModels"))
                Directory.Delete("familyInputModels", true);

            var times = new long[sizes.Length, iterations, 5];

            // Run an iteration for the smallest input size to warm up the CLR
            RunIteration(times, sizes[0], 0, 0, workloadSize);

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
            var familiesModel = FamiliesGenerator.GenerateFamilies(n);
            var startRule = families2Persons.SynchronizationRule<Families2Persons.Model2ModelMainRule>();

            var inputBatchModelContainer = new InputModelContainer(CopyFamilyModel(familiesModel));
            var outputBatchModelContainer = new OutputModelContainer(new Model());

            watch.Start();
            families2Persons.Synchronize(startRule, ref inputBatchModelContainer, ref outputBatchModelContainer, SynchronizationDirection.LeftToRight, ChangePropagationMode.None);
            watch.Stop();
            times[sizeIdx, iteration, 0] = watch.Elapsed.Ticks * 100;

            var inputIncModelContainer = new InputModelContainer(CopyFamilyModel(familiesModel));
            var outputIncModelContainer = new OutputModelContainer(new Model());

            watch.Restart();
            families2Persons.Synchronize(startRule, ref inputIncModelContainer, ref outputIncModelContainer, SynchronizationDirection.LeftToRight, ChangePropagationMode.OneWay);
            watch.Stop();
            times[sizeIdx, iteration, 1] = watch.Elapsed.Ticks * 100;

            var workload = FamiliesGenerator.GenerateChangeWorkload(familiesModel, workloadSize);
            PlayBatchNet(times, sizeIdx, iteration, startRule, watch, ref inputBatchModelContainer, ref outputBatchModelContainer, workload);
            PlayIncremental(times, sizeIdx, iteration, watch, inputIncModelContainer, workload);
            PlayChangesOnly(times, sizeIdx, iteration, watch, CopyFamilyModel(familiesModel), workload);

            var inputModelContainer = new InputModelContainer(CopyFamilyModel(familiesModel));
            var outputModelContainer = new OutputModelContainer(new Model());

            CreateModelsForAtlTransformation(n, iteration, workload, startRule, inputModelContainer, outputModelContainer);
        }

        private static void CreateModelsForAtlTransformation(int n, int iteration, List<FamilyWorkloadAction> workload, Families2Persons.Model2ModelMainRule startRule, InputModelContainer inputModelContainer, OutputModelContainer outputModelContainer)
        {
            var outputRepository = new ModelRepository();

            Directory.CreateDirectory("familyInputModels");
            Directory.CreateDirectory("familyInputModels\\" + n);
            Directory.CreateDirectory("familyInputModels\\" + n + "\\" + iteration);

            families2Persons.Synchronize(startRule, ref inputModelContainer, ref outputModelContainer, SynchronizationDirection.LeftToRight, ChangePropagationMode.OneWay);

            for (int index = 0; index < workload.Count; index++)
            {
                var item = workload[index];
                item.Perform(inputModelContainer.IN);
                outputRepository.Save(inputModelContainer.IN, "familyInputModels\\" + n + "\\" + iteration + "\\" + "inputModel" + index + ".xmi");
            }
        }

        private static void PlayIncremental(long[,,] times, int sizeIdx, int iteration, Stopwatch watch, InputModelContainer inputModelContainer, List<FamilyWorkloadAction> workload)
        {
            watch.Restart();
            foreach (var item in workload)
            {
                item.Perform(inputModelContainer.IN);
            }
            watch.Stop();

            times[sizeIdx, iteration, 3] = watch.Elapsed.Ticks * 100;
        }

        private static void PlayChangesOnly(long[,,] times, int sizeIdx, int iteration, Stopwatch watch, Model inputModel, List<FamilyWorkloadAction> workload)
        {
            watch.Restart();
            foreach (var item in workload)
            {
                item.Perform(inputModel);
            }
            watch.Stop();

            times[sizeIdx, iteration, 4] = watch.Elapsed.Ticks * 100;
        }

        private static void PlayBatchNet(long[,,] times, int sizeIdx, int iteration, Families2Persons.Model2ModelMainRule startRule, Stopwatch watch, ref InputModelContainer inputModelContainer, ref OutputModelContainer outputModelContainer, List<FamilyWorkloadAction> workload)
        {
            watch.Restart();
            foreach (var item in workload)
            {
                item.Perform(inputModelContainer.IN);
                RerunBatchSynchronization(startRule, ref inputModelContainer, ref outputModelContainer);
            }
            watch.Stop();

            times[sizeIdx, iteration, 2] = watch.Elapsed.Ticks * 100;
        }

        private static void RerunBatchSynchronization(Families2Persons.Model2ModelMainRule startRule, ref InputModelContainer inputModelContainer, ref OutputModelContainer outputModelContainer)
        {
            outputModelContainer = new OutputModelContainer(new Model());

            families2Persons.Synchronize(startRule, ref inputModelContainer, ref outputModelContainer, SynchronizationDirection.LeftToRight, ChangePropagationMode.None);
        }

        private static Model CopyFamilyModel(Model sourceModel)
        {
            var targetModel = new Model();

            var families = sourceModel.RootElements.OfType<Family>();
            foreach (var sourceFamily in families)
            {
                var targetFamily = new Family { LastName = sourceFamily.LastName };

                var father = new Member
                {
                    FirstName = sourceFamily.Father.FirstName,
                    FamilyFather = targetFamily
                };
                targetFamily.Father = father;

                var mother = new Member
                {
                    FirstName = sourceFamily.Mother.FirstName,
                    FamilyMother = targetFamily
                };
                targetFamily.Mother = mother;

                foreach (var sourceSon in sourceFamily.Sons)
                {
                    var targetSon = new Member
                    {
                        FirstName = sourceSon.FirstName,
                        FamilySon = targetFamily
                    };

                    targetFamily.Sons.Add(targetSon);
                }

                foreach (var sourceDaughter in sourceFamily.Daughters)
                {
                    var targetDaughter = new Member
                    {
                        FirstName = sourceDaughter.FirstName,
                        FamilyDaughter = targetFamily
                    };

                    targetFamily.Daughters.Add(targetDaughter);
                }

                targetModel.RootElements.Add(targetFamily);
            }

            return targetModel;
        }

        /// <summary>
        /// Writes the results to a file
        /// </summary>
        private static void WriteResultsToCsv(int[] sizes, int iterations, long[,,] times)
        {
            using (var sw = new StreamWriter("familyResults.csv"))
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
