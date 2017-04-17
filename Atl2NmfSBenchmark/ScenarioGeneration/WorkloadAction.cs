using System.Collections.Generic;
using System.Text;
using Families2PersonsNamespace.Families;
using NMF.Expressions;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration
{
    /// <summary>
    /// The abstract base class for elements of a generated workload on family
    /// </summary>
    abstract class FamilyWorkloadAction
    {
        public abstract void Perform(Model sourceModel);

        public abstract int FamilyIndex { get; set; }
    }

    /// <summary>
    /// The abstract base class for elements of a generated workload on make
    /// </summary>
    abstract class MakeWorkloadAction
    {
        public abstract void Perform(Model sourceModel);

        public abstract int ElementIndex { get; set; }
    }
}
