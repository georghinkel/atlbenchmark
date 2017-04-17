using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Make2AntNamespace.Make;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Make
{
    class AddFileDependency : MakeWorkloadAction
    {
        public string Id { get; set; }

        public string Name { get; set; }

        public override void Perform(Model sourceModel)
        {
            var makefile = (Makefile) sourceModel.RootElements[0];
            var rules = makefile.Elements.OfType<IRule>().ToList();

            if (rules.Count == 0)
                return;

            if (ElementIndex >= rules.Count)
            {
                ElementIndex = rules.Count - 1;
            }

            var dependencyFileDep = new FileDep
            {
                ID = Id,
                Name = Name
            };

            rules[ElementIndex].Dependencies.Add(dependencyFileDep);
        }

        public override int ElementIndex { get; set; }
    }
}
