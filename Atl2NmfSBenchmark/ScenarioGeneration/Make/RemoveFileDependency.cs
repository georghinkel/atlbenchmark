using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Make2AntNamespace.Make;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Make
{
    class RemoveFileDependency : MakeWorkloadAction
    {
        public override void Perform(Model sourceModel)
        {
            var dependencyFileDeps = sourceModel.Descendants().OfType<IFileDep>().ToList();

            if (dependencyFileDeps.Count == 0)
                return;

            if (ElementIndex >= dependencyFileDeps.Count)
            {
                ElementIndex = dependencyFileDeps.Count - 1;
            }

            var dependencyFileDep = dependencyFileDeps[ElementIndex];
            dependencyFileDep.Delete();
        }

        public override int ElementIndex { get; set; }
    }
}
