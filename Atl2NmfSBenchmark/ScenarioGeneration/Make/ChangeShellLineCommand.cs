﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Make2AntNamespace.Make;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Make
{
    class ChangeShellLineCommand : MakeWorkloadAction
    {
        public int ShellCommandIndex { get; set; }

        public string NewCommand { get; set; }

        public string NewValue { get; set; }

        public override void Perform(Model sourceModel)
        {
            var makefile = sourceModel.RootElements[0] as Makefile;

            if (ElementIndex >= makefile.Elements.Count)
            {
                ElementIndex = 0;
            }

            var rule = makefile.Elements[ElementIndex] as IRule;
            while (rule == null && ElementIndex < makefile.Elements.Count - 1)
            {
                ElementIndex++;
                rule = makefile.Elements[ElementIndex] as IRule;
            }

            if (rule == null) return;

            if (rule.ShellLines.Count > 0)
            {
                var shellLine = rule.ShellLines[ShellCommandIndex >= rule.ShellLines.Count ? 0 : ShellCommandIndex];
                shellLine.Command = NewCommand;
            }
            else
            {
                rule.ShellLines.Add(new ShellLine
                {
                    ID = Guid.NewGuid().ToString(),
                    Command = NewCommand,
                    Display = true
                });
            }
        }

        public override int ElementIndex { get; set; }
    }
}
