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

            makefile.Elements.RemoveAt(ElementIndex);
        }

        public override int ElementIndex { get; set; }
    }
}
