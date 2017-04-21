using System;
using System.Diagnostics;

namespace NMF.Synchronizations.ATLBenchmark
{
    class Program
    {
        static void Main(string[] args)
        {
            if (args.Length != 1) throw new Exception("The supplied mode is not valid.");
            if (args[0] == "Make2Ant")
            {
                Make2AntExecutor.Measure(sizes: new[] { 10, 50, 100, 500, 1000, 5000, 10000, 50000 }, iterations: 10, workloadSize: 20);
            }
            else if (args[0] == "Families2Persons")
            {
                Families2PersonsExecuter.Measure(sizes: new[] { 10, 50, 100, 500, 1000, 5000, 10000, 50000 }, iterations: 10, workloadSize: 20);
            }
            else if (args[0] == "Make2AntSyncAtl")
            {
                //this executor was used for the performance comparison with SyncATL
                Make2AntSyncAtlExecutor.Measure(sizes: new[] { 10, 100 }, iterations: 10, workloadSize: 20);
            }
        }
    }
}
