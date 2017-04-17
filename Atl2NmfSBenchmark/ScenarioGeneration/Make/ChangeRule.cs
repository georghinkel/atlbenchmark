using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Make2AntNamespace.Make;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Make
{
    class ChangeRule : MakeWorkloadAction
    {
        public string NewName { get; set; }

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
            rule.Name = NewName;
        }

        public override int ElementIndex { get; set; }
    }
}
