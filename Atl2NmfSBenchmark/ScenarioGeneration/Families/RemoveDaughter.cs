using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Families2PersonsNamespace.Families;
using NMF.Expressions;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Families
{
    class RemoveDaughter : FamilyWorkloadAction
    {
        public int DaughterIndex { get; set; }

        public override void Perform(Model sourceModel)
        {
            if (sourceModel.RootElements.Count == 0)
            {
                //create a new family instead
                var addNewFamily = new AddNewFamily { LastName = "FamilyName" };
                addNewFamily.Perform(sourceModel);
                return;
            }

            if (FamilyIndex >= sourceModel.RootElements.Count)
            {
                FamilyIndex = sourceModel.RootElements.Count - 1;
            }

            var family = (IFamily)sourceModel.RootElements.ElementAt(FamilyIndex);
            if (family.Daughters.Count == 0)
                return;

            if (DaughterIndex >= family.Daughters.Count)
            {
                DaughterIndex = family.Daughters.Count - 1;
            }

            var daughter = family.Daughters.ElementAt(DaughterIndex);
            family.Daughters.Remove(daughter);
        }

        public override int FamilyIndex { get; set; }
    }
}
