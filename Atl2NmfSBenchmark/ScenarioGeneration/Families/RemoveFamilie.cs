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
    class RemoveFamilie : FamilyWorkloadAction
    {
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

            var family = (IFamily) sourceModel.RootElements.ElementAt(FamilyIndex);

            family.Father.Delete();
            family.Mother.Delete();

            for(int i=0; i < family.Sons.Count;)
            {
                family.Sons.ElementAt(0).Delete();
            }

            for (int i = 0; i < family.Daughters.Count;)
            {
                family.Daughters.ElementAt(0).Delete();
            }

            family.Delete();
        }

        public override int FamilyIndex { get; set; }

    }
}
