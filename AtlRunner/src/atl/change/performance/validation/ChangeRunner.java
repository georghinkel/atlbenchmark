package atl.change.performance.validation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import atl.performance.validation.MetamodelInfo;
import atl.performance.validation.ModelInfo;

public class ChangeRunner {

	public static void main(String[] args) throws IOException {
		RunMake2Ant();
		RunFamilies2Persons();
	}

	private static void RunMake2Ant() throws IOException {
		String transformationLocationDefault = "resources/M2A/Make2Ant.asm";
		String transformationLocationEmftvm = "resources/M2A/Make2Ant.emftvm";
		
		String sourceMetamodelPath = "resources/M2A/Make.ecore";
		String sourceMetamodelName = "Make";
		
		String targetMetamodelPath = "resources/M2A/Ant.ecore";
		String targetMetamodelName = "Ant";
		
		MetamodelInfo sourceMetamodelInfo = new MetamodelInfo(
				sourceMetamodelPath,
				sourceMetamodelName);
		
		MetamodelInfo targetMetamodelInfo = new MetamodelInfo(
				targetMetamodelPath,
				targetMetamodelName);
		
		ModelInfo sourceModelInfo = new ModelInfo(
				"",
				"IN",
				sourceMetamodelPath,
				sourceMetamodelName);
		
		ModelInfo targetModelInfo = new ModelInfo(
				"resources/M2A/target.xmi",
				"OUT",
				targetMetamodelPath,
				targetMetamodelName);	

		String parentLocation = "resources/M2A\\";
		String moduleName = "Make2Ant";

		
		//the basePath must point to the XMI-Models created by the NMF S. benchmark
		String basePath = "../makeInputModels/";
		String resultFilename = "../makeResultsAtl.csv";
		
		RunTransformation(basePath, resultFilename, transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfo, targetMetamodelInfo, sourceModelInfo, targetModelInfo, parentLocation, moduleName);
	}
	
	private static void RunFamilies2Persons() throws IOException {
		String transformationLocationDefault = "resources/Families2Persons/Families2Persons.asm";
		String transformationLocationEmftvm = "resources/Families2Persons/Families2Persons.emftvm";
		
		String sourceMetamodelPath = "resources/Families2Persons/Families.ecore";
		String sourceMetamodelName = "Families";
		
		String targetMetamodelPath = "resources/Families2Persons/Persons.ecore";
		String targetMetamodelName = "Persons";
		
		MetamodelInfo sourceMetamodelInfo = new MetamodelInfo(
				sourceMetamodelPath,
				sourceMetamodelName);
		
		MetamodelInfo targetMetamodelInfo = new MetamodelInfo(
				targetMetamodelPath,
				targetMetamodelName);
		
		ModelInfo sourceModelInfo = new ModelInfo(
				"",
				"IN",
				sourceMetamodelPath,
				sourceMetamodelName);
		
		ModelInfo targetModelInfo = new ModelInfo(
				"resources/Families2Persons/ExpectedPersons.xmi",
				"OUT",
				targetMetamodelPath,
				targetMetamodelName);	

		String parentLocation = "resources/Families2Persons\\";
		String moduleName = "Families2Persons";

		
		String basePath = "../familyInputModels/";
		String resultFilename = "../familyResultsAtl.csv";
		
		RunTransformation(basePath, resultFilename, transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfo, targetMetamodelInfo, sourceModelInfo, targetModelInfo, parentLocation, moduleName);
	}
	
	private static void RunTransformation(String basePath, String resultFilename, String transformationLocationDefault, String transformationLocationEmftvm, MetamodelInfo sourcemmInfo, MetamodelInfo targetmmInfo, ModelInfo sourceModelInfo, ModelInfo targetModelInfo,
			String parentLocation, String moduleName) throws IOException {

		int iterations = 10;
		int workloadSize = 20;
		int[] sizes = new int[] {10,50,100,500,1000,5000,10000,50000};
		
		long[][] emftTimes = new long[ sizes.length ][ iterations ];
		long[][] defaultTimes = new long[ sizes.length ][ iterations ];
		
		EmftVmChangeRunner emftRunner = new EmftVmChangeRunner();
		emftRunner.run(emftTimes, basePath, sizes, iterations, workloadSize, transformationLocationEmftvm, sourcemmInfo, targetmmInfo, sourceModelInfo, targetModelInfo, parentLocation, moduleName);

		DefaultVmChangeRunner defaultRunner = new DefaultVmChangeRunner();
		defaultRunner.run(defaultTimes, basePath, sizes, iterations, workloadSize, transformationLocationDefault, sourcemmInfo, targetmmInfo, sourceModelInfo, targetModelInfo);

		try (FileWriter fw = new FileWriter(resultFilename, false);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			
			out.println("Size;Run;Default;Emftvm");
			
			for (int sizeIdx = 0; sizeIdx < sizes.length; sizeIdx++) {
				int n = sizes[sizeIdx];

				for (int iteration = 0; iteration < iterations; iteration++) {
					long emftTime = (emftTimes[sizeIdx][iteration]);
					long defaultTime = (defaultTimes[sizeIdx][iteration]);

					out.println(n + ";" + iteration + ";" + defaultTime + ";" + emftTime);
				}
			}

		} catch (IOException e) {
			// exception handling left as an exercise for the reader
		}
	}
}
