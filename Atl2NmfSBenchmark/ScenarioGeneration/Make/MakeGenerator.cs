using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Families2PersonsNamespace.Families;
using Make2AntNamespace.Make;
using NMF.Models;
using NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Families;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Make
{
    public static class MakeGenerator
    {
        public static Model GenerateMake(int numberOfElements)
        {
            var rand = new Random();
            var rules = new List<Rule>();

            int idCounter = 0;

            var model = new Model();

            var makefile = new Makefile
            {
                ID = idCounter.ToString(),
                Name = "makefile name"
            };
            model.RootElements.Add(makefile);
            idCounter++;

            var comment = new Comment
            {
                ID = idCounter.ToString(),
                Text = "comment text" + idCounter
            };
            makefile.Comment = comment;
            idCounter++;

            //~10% macros
            for (int i = 0; i < (numberOfElements / 10); i++)
            {
                var macro = new Macro
                {
                    ID = idCounter.ToString(),
                    Name = "macro name" + idCounter,
                    Value = "macro value" + idCounter
                };
                idCounter++;

                makefile.Elements.Add(macro);
            }

            //~25% rules
            for (int i = 0; i < (numberOfElements / 4); i++)
            {
                var rule = new Rule
                {
                    ID = idCounter.ToString(),
                    Name = "RuleName" + idCounter
                };
                idCounter++;

                rules.Add(rule);
                makefile.Elements.Add(rule);

                var dependencyFileDep = new FileDep
                {
                    ID = idCounter.ToString(),
                    Name = "fileName" + idCounter
                };
                rule.Dependencies.Add(dependencyFileDep);
                idCounter++;

                var shellLine = new ShellLine
                {
                    ID = idCounter.ToString(),
                    Command = "command shell line " + idCounter
                };
                var dice = rand.NextDouble();
                shellLine.Display = dice > 0.5;
                rule.ShellLines.Add(shellLine);
                idCounter++;
            }

            for (int i = idCounter; i < numberOfElements; i++)
            {
                var ruleNumber = rand.Next(rules.Count - 1);

                var dice = rand.NextDouble();
                if (dice < 0.5)
                {
                    var shellLine = new ShellLine
                    {
                        ID = idCounter.ToString(),
                        Command = "command shell line " + idCounter
                    };
                    var displayDice = rand.NextDouble();
                    shellLine.Display = dice > 0.5;
                    rules[ruleNumber].ShellLines.Add(shellLine);
                    idCounter++;
                }
                else
                {
                    var dependencyFileDep = new FileDep
                    {
                        ID = idCounter.ToString(),
                        Name = "fileName" + idCounter
                    };
                    rules[ruleNumber].Dependencies.Add(dependencyFileDep);
                    idCounter++;
                }
            }

            return model;
        }

        internal static List<MakeWorkloadAction> GenerateChangeWorkload(Model model, int workloadItems)
        {
            var makefile = (IMakefile) model.RootElements[0];

            var idCounter = model.Descendants().Count();

            var fileDepsCounter = makefile.Descendants().OfType<IFileDep>().Count();
            var macroCounter = makefile.Descendants().OfType<IMacro>().Count();
            var shellLineCounter = makefile.Descendants().OfType<IShellLine>().Count();
            var ruleCounter = makefile.Descendants().OfType<IRule>().Count();

            var rand = new Random();
            var workload = new List<MakeWorkloadAction>();

            for (int i = 0; i < workloadItems; i++)
            {
                var dice = rand.NextDouble();

                if (dice < 0.04 && macroCounter > 0)
                {
                    // Remove Macro (4%)
                    var elementIndex = rand.Next(macroCounter - 1);
                    workload.Add(new RemoveMacro()
                    {
                        ElementIndex = elementIndex
                    });

                    macroCounter--;
                }
                else if (dice < 0.11)
                {
                    // Add Macro (7%)
                    workload.Add(new AddMacro()
                    {
                        Id = idCounter.ToString(),
                        Name = "macro name" + idCounter,
                        Value = "macro value" + idCounter
                    });

                    macroCounter++;
                    idCounter++;
                }
                else if (dice < 0.18 && macroCounter > 0)
                {
                    // Change Macro (7%)
                    var elementIndex = rand.Next(macroCounter - 1);

                    workload.Add(new ChangeMacro()
                    {
                        ElementIndex = elementIndex,
                        NewName = "new macro name",
                        NewValue = "new macro value"
                    });
                }
                else if (dice < 0.28)
                {
                    // Add FileDependency (10%)
                    workload.Add(new AddFileDependency()
                    {
                        Id = idCounter.ToString(),
                        Name = "file dependency name" + idCounter
                    });

                    fileDepsCounter++;
                    idCounter++;
                }
                else if (dice < 0.38 && fileDepsCounter > 0)
                {
                    // Change FileDependency (10%)
                    var elementIndex = rand.Next(fileDepsCounter - 1);

                    workload.Add(new ChangeFileDependency()
                    {
                        ElementIndex = elementIndex,
                        NewName = "new macro name",
                        NewValue = "new macro value"
                    });
                }
                else if (dice < 0.43 && fileDepsCounter > 0)
                {
                    // Remove FileDependency (5%)
                    var elementIndex = rand.Next(fileDepsCounter - 1);
                    workload.Add(new RemoveFileDependency()
                    {
                        ElementIndex = elementIndex
                    });

                    fileDepsCounter--;
                }
                else if (dice < 0.53)
                {
                    // Add ShellLine (10%)
                    workload.Add(new AddShellLine()
                    {
                        Id = idCounter.ToString(),
                        Command = "shellLine command" + idCounter
                    });

                    shellLineCounter++;
                    idCounter++;
                }
                else if (dice < 0.63 && shellLineCounter > 0)
                {
                    // Change ShellLine (10%)
                    var elementIndex = rand.Next(shellLineCounter - 1);

                    workload.Add(new ChangeShellLineCommand()
                    {
                        ElementIndex = elementIndex,
                        NewCommand = "new shellLine command",
                        NewValue = "new shellLine value"
                    });
                }
                else if (dice < 0.68 && shellLineCounter > 0)
                {
                    // Remove ShellLine (5%)
                    var elementIndex = rand.Next(shellLineCounter - 1);
                    workload.Add(new RemoveShellLine()
                    {
                        ElementIndex = elementIndex
                    });

                    shellLineCounter--;
                }
                else if (dice < 0.82)
                {
                    // Add Rule (15%)
                    var addRule = new AddRule();
                    addRule.IdRule = idCounter.ToString();
                    idCounter++;
                    addRule.IdFileDep = idCounter.ToString();
                    idCounter++;
                    addRule.IdShellLine = idCounter.ToString();
                    idCounter++;
                    addRule.NameRule = "rule name" + addRule.IdRule;
                    addRule.NameFileDep = "fileDep name" + addRule.IdFileDep;
                    addRule.CommandShellLine = "command shellLine" + addRule.IdShellLine;

                    workload.Add(addRule);

                    shellLineCounter++;
                    fileDepsCounter++;
                    ruleCounter++;
                }
                else if (dice < 0.92 && ruleCounter > 0)
                {
                    // Change Rule (10%)
                    var elementIndex = rand.Next(ruleCounter - 1);

                    workload.Add(new ChangeRule()
                    {
                        ElementIndex = elementIndex,
                        NewName = "new rule name"
                    });
                }
                else if (dice < 0.97 && ruleCounter > 1)
                {
                    //Emftvm requires at least one rule
                    // Remove Rule (5%)
                    var elementIndex = rand.Next(ruleCounter - 1);
                    workload.Add(new RemoveRule()
                    {
                        ElementIndex = elementIndex
                    });

                    ruleCounter--;
                }
                else
                {
                    // Change Comment (3%)
                    workload.Add(new ChangeComment()
                    {
                        NewText = "new comment text"
                    });
                }
            }

            return workload;
        }
    }
}
