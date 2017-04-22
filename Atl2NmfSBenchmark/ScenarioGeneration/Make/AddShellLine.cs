using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Make2AntNamespace.Make;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Make
{
    class AddShellLine : MakeWorkloadAction
    {
        public string Id { get; set; }

        public string Command { get; set; }

        public override void Perform(Model sourceModel)
        {
            var makefile = (Makefile)sourceModel.RootElements[0];

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

            var shellLine = new ShellLine
            {
                ID = Id,
                Command = Command,
                Display = false
            };

            rule.ShellLines.Add(shellLine);
        }

        public override int ElementIndex { get; set; }
    }
}
