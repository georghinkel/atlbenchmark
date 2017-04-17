using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Make2AntNamespace.Make;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Make
{
    class ChangeShellLineCommand : MakeWorkloadAction
    {
        public string NewCommand { get; set; }

        public string NewValue { get; set; }

        public override void Perform(Model sourceModel)
        {
            var shellLines = sourceModel.Descendants().OfType<IShellLine>().ToList();

            if (shellLines.Count == 0)
                return;

            if (ElementIndex >= shellLines.Count)
            {
                ElementIndex = shellLines.Count - 1;
            }

            var shellLine = shellLines[ElementIndex];
            shellLine.Command = NewCommand;
        }

        public override int ElementIndex { get; set; }
    }
}
