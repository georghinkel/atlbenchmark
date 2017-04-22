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

            if (macro == null)
            {
                makefile.Elements.Add(new Macro
                {
                    ID = Guid.NewGuid().ToString(),
                    Name = NewName,
                    Value = NewValue
                });
            }
            else
            {
                macro.Name = NewName;
                macro.Value = NewValue;
            }
        }

        public override int ElementIndex { get; set; }
    }
}
