using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Make2AntNamespace.Make;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Make
{
    class AddRule : MakeWorkloadAction
    {
        public string IdRule { get; set; }

        public string IdFileDep { get; set; }

        public string IdShellLine { get; set; }

        public string NameRule { get; set; }

        public string NameFileDep { get; set; }

        public string CommandShellLine { get; set; }

        public override void Perform(Model sourceModel)
        {
            var makefile = (IMakefile)sourceModel.RootElements[0];

            var rule = new Rule
            {
                ID = IdRule,
                Name = NameRule
            };

            var dependencyFileDep = new FileDep
            {
                ID = IdFileDep,
                Name = NameFileDep
            };
            rule.Dependencies.Add(dependencyFileDep);

            var shellLine = new ShellLine
            {
                ID = IdShellLine,
                Command = CommandShellLine,
                Display = false
            };
            rule.ShellLines.Add(shellLine);

            makefile.Elements.Add(rule);
        }

        public override int ElementIndex { get; set; }
    }
}
