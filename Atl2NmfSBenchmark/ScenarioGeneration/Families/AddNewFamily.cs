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
    class AddNewFamily : FamilyWorkloadAction
    {
        public string LastName { get; set; }

        public override void Perform(Model sourceModel)
        {
            var family = new Family();
            family.LastName = LastName;

            var father = new Member
            {
                FirstName = "Father Name",
                FamilyFather = family
            };
            family.Father = father;

            var mother = new Member
            {
                FirstName = "Mother Name",
                FamilyMother = family
            };
            family.Mother = mother;

            for (int j = 0; j < 2; j++)
            {
                var son = new Member
                {
                    FirstName = "Son Name " + j,
                    FamilySon = family
                };
                family.Sons.Add(son);
            }

            for (int j = 0; j < 2; j++)
            {
                var daughter = new Member
                {
                    FirstName = "Daughter Name " + j,
                    FamilyDaughter = family
                };
                family.Daughters.Add(daughter);
            }

            sourceModel.RootElements.Add(family);
        }

        public override int FamilyIndex { get; set; }
    }
}
