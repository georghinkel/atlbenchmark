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
            var rules = makefile.Elements.OfType<IRule>().ToList();

            if (rules.Count == 0)
                return;

            if (ElementIndex >= rules.Count)
            {
                ElementIndex = rules.Count - 1;
            }

            var shellLine = new ShellLine
            {
                ID = Id,
                Command = Command,
                Display = false
            };

            rules[ElementIndex].ShellLines.Add(shellLine);
        }

        public override int ElementIndex { get; set; }
    }
}
