using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Families2PersonsNamespace.Families;
using Make2AntNamespace.Make;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Make
{
    class ChangeMacro : MakeWorkloadAction
    {
        public string NewName { get; set; }

        public string NewValue { get; set; }

        public override void Perform(Model sourceModel)
        {
            var makefile = (Makefile)sourceModel.RootElements[0];
            var macros = makefile.Elements.OfType<IMacro>().ToList();

            if (macros.Count == 0)
                return;

            if (ElementIndex >= macros.Count)
            {
                ElementIndex = macros.Count - 1;
            }

            var macro = macros[ElementIndex];
            macro.Value = NewValue;
            macro.Name = NewName;
        }

        public override int ElementIndex { get; set; }
    }
}
