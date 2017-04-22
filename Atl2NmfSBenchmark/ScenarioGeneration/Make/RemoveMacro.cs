using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Make2AntNamespace.Make;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Make
{
    class RemoveMacro : MakeWorkloadAction
    {
        public override void Perform(Model sourceModel)
        {
            var makefile = (Makefile)sourceModel.RootElements[0];

            if (ElementIndex >= makefile.Elements.Count)
            {
                ElementIndex = 0;
            }

            var macro = makefile.Elements[ElementIndex] as IMacro;
            while (macro == null && ElementIndex < makefile.Elements.Count - 1)
            {
                ElementIndex++;
                macro = makefile.Elements[ElementIndex] as IMacro;
            }

            if (macro == null) return;
            macro.Delete();
        }

        public override int ElementIndex { get; set; }
    }
}
