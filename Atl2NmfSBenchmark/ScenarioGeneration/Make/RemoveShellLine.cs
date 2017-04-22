using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Make2AntNamespace.Make;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Make
{
    class RemoveShellLine : MakeWorkloadAction
    {
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

            if (rule == null || rule.ShellLines.Count == 0) return;

            rule.ShellLines[rule.ShellLines.Count - 1].Delete();
        }

        public override int ElementIndex { get; set; }
    }
}
