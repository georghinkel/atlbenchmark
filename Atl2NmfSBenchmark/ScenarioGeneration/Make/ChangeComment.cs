using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Make2AntNamespace.Make;
using NMF.Models;

namespace NMF.Synchronizations.ATLBenchmark.ScenarioGeneration.Make
{
    class ChangeComment : MakeWorkloadAction
    {
        public string NewText { get; set; }

        public override void Perform(Model sourceModel)
        {
            var makefile = (IMakefile)sourceModel.RootElements[0];

            var comment = makefile.Comment;
            comment.Text = NewText;
        }

        public override int ElementIndex { get; set; }
    }
}
