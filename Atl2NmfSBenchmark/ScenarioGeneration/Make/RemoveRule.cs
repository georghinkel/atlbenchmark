using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Make2AntNamespace.Make;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Make
{
    class RemoveRule : MakeWorkloadAction
    {
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

            var rule = rules[ElementIndex];

            for (int i = 0; i < rule.Dependencies.Count;)
            {
                rule.Dependencies.ElementAt(0).Delete();
            }

            for (int i = 0; i < rule.ShellLines.Count;)
            {
                rule.ShellLines.ElementAt(0).Delete();
            }

            rule.Delete();
        }

        public override int ElementIndex { get; set; }
    }
}
