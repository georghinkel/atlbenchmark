﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Families2PersonsNamespace.Families;
using NMF.Expressions;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Families
{
    class RenameSon : FamilyWorkloadAction
    {
        public int SonIndex { get; set; }

        public string NewFirstName { get; set; }

        public override void Perform(Model sourceModel)
        {
            if (sourceModel.RootElements.Count == 0)
            {
                //create a new family instead
                var addNewFamily = new AddNewFamily {LastName = "FamilyName"};
                addNewFamily.Perform(sourceModel);
                return;
            }

            if (FamilyIndex >= sourceModel.RootElements.Count)
            {
                FamilyIndex = sourceModel.RootElements.Count - 1;
            }

            var family = (IFamily)sourceModel.RootElements.ElementAt(FamilyIndex);
            if (family.Sons.Count == 0)
                return;

            if (SonIndex >= family.Sons.Count)
            {
                SonIndex = family.Sons.Count - 1;
            }

            family.Sons[SonIndex].FirstName = NewFirstName;
        }

        public override int FamilyIndex { get; set; }
    }
}
