using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Families2PersonsNamespace.Families;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Families
{
    class AddNewSon : FamilyWorkloadAction
    {
        public string FirstName { get; set; }

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
            family.Sons.Add(new Member()
            {
                FirstName = FirstName,
                FamilySon = family
            });
        }

        public override int FamilyIndex { get; set; }
    }
}
