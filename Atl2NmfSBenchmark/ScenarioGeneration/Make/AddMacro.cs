using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Make2AntNamespace.Make;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Make
{
    class AddMacro : MakeWorkloadAction
    {
        public string Id { get; set; }

        public string Name { get; set; }

        public string Value { get; set; }

        public override void Perform(Model sourceModel)
        {
            var makefile = (IMakefile)sourceModel.RootElements[0];

            var macro = new Macro
            {
                ID = Id,
                Name = Name,
                Value = Value
            };

            makefile.Elements.Add(macro);
        }

        public override int ElementIndex { get; set; }
    }
}
