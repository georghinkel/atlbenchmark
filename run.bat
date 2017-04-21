Atl2NmfSBenchmark\bin\x64\Release\ATLBenchmark.exe Make2Ant
Atl2NmfSBenchmark\bin\x64\Release\ATLBenchmark.exe Families2Persons
cd AtlRunner
java -jar atlrunner.jar
cd ..
Atl2NmfSBenchmark\bin\x64\Release\ATLBenchmark.exe Make2AntSyncAtl
cd AtlRunner
java -jar syncatl.jar
RScript createGraphs.r