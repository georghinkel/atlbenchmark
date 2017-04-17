using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Families2PersonsNamespace.Families;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Families
{
    public static class FamiliesGenerator
    {
        public static Model GenerateFamilies(int numberOfElements)
        {
            var rand = new Random();

            int elementCounter = 0;
            var numberOfFamilies = numberOfElements/10;
            var model = new Model();
            var families = new List<Family>();

            for (int i = 0; i < numberOfFamilies; i++)
            {
                var family = new Family {LastName = "LastName" + elementCounter};
                elementCounter++;

                var father = new Member
                {
                    FirstName = "Father Name " + elementCounter,
                    FamilyFather = family
                };
                family.Father = father;
                elementCounter++;

                var mother = new Member
                {
                    FirstName = "Mother Name" + elementCounter,
                    FamilyMother = family
                };
                family.Mother = mother;
                elementCounter++;

                for (int j = 0; j < 2; j++)
                {
                    var son = new Member
                    {
                        FirstName = "Son Name " + elementCounter,
                        FamilySon = family
                    };
                    family.Sons.Add(son);
                    elementCounter++;
                }

                for (int j = 0; j < 2; j++)
                {
                    var daughter = new Member
                    {
                        FirstName = "Daughter Name " + elementCounter,
                        FamilyDaughter = family
                    };
                    family.Daughters.Add(daughter);
                    elementCounter++;
                }

                model.RootElements.Add(family);
                families.Add(family);
            }

            for (int i = elementCounter; i < numberOfElements; i++)
            {
                var familyIndex = rand.Next(numberOfFamilies);

                if (rand.NextDouble() > 0.5)
                {
                    var family = families[familyIndex];
                    var daughter = new Member
                    {
                        FirstName = "Daughter Name " + elementCounter,
                        FamilyDaughter = family
                    };

                    family.Daughters.Add(daughter);
                    elementCounter++;
                }
                else
                {
                    var family = families[familyIndex];
                    var son = new Member
                    {
                        FirstName = "Son Name " + elementCounter,
                        FamilySon = family
                    };

                    family.Sons.Add(son);
                    elementCounter++;
                }
            }

            return model;
        }

        internal static List<FamilyWorkloadAction> GenerateChangeWorkload(Model model, int workloadItems)
        {
            var families = model.RootElements.OfType<Families2PersonsNamespace.Families.Family>().ToList();


            var rand = new Random();
            var workload = new List<FamilyWorkloadAction>();
            var familyCount = families.Count;

            for (int i = 0; i < workloadItems; i++)
            {
                if (familyCount == 0)
                {
                    // Add new family
                    workload.Add(new AddNewFamily()
                    {
                        LastName = "LastName"
                    });

                    continue;
                }

                var dice = rand.NextDouble();
                var familyIndex = rand.Next(familyCount - 1);
                var family = families[familyIndex];

                if (dice < 0.10)
                {
                    // Remove son
                    workload.Add(new RemoveSon()
                    {
                        SonIndex = rand.Next(family.Sons.Count),
                        FamilyIndex = familyIndex
                    });
                }
                else if (dice < 0.20)
                {
                    // Remove daughter
                    workload.Add(new RemoveDaughter()
                    {
                        DaughterIndex = rand.Next(family.Daughters.Count),
                        FamilyIndex = familyIndex
                    });
                }
                else if (dice < 0.45)
                {
                    // Add new son
                    workload.Add(new AddNewSon()
                    {
                        FirstName = "Son Name added " + (family.Sons.Count + 1),
                        FamilyIndex = familyIndex
                    });
                }
                else if (dice < 0.70)
                {
                    // Add new daughter
                    workload.Add(new AddNewSon()
                    {
                        FirstName = "Daughter Name added " + (family.Daughters.Count + 1),
                        FamilyIndex = familyIndex
                    });
                }
                else if (dice < 0.80)
                {
                    // Rename son
                    workload.Add(new RenameSon()
                    {
                        SonIndex = rand.Next(family.Sons.Count),
                        NewFirstName = "New Son name " + (family.Sons.Count + 1),
                        FamilyIndex = familyIndex
                    });
                }
                else if (dice < 0.90)
                {
                    // Rename daughter
                    workload.Add(new RenameDaughter()
                    {
                        DaughterIndex = rand.Next(family.Daughters.Count),
                        NewFirstName = "New daughter name " + (family.Daughters.Count + 1),
                        FamilyIndex = familyIndex
                    });
                }
                else if (dice < 0.92)
                {
                    // Remove family
                    workload.Add(new RemoveFamilie()
                    {
                        FamilyIndex = familyIndex
                    });
                }
                else
                {
                    // Add new family
                    workload.Add(new AddNewFamily()
                    {
                        LastName = "LastName"
                    });
                }
            }
            return workload;
        }
    }
}
