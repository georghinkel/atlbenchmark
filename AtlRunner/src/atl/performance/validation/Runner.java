package atl.performance.validation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.OptionalDouble;

public class Runner {

	public static void main(String[] args) throws IOException {
//		RunA2BContainment();
//		
//		RunA2BHelperWithoutContext();
//		
//		RunA2BMultipleInputAndOutput();
//		
//		RunA2BMultipleInputDifferentType();
//		
//		RunA2BMultipleInputSameType();
//		
//		RunA2BMultipleOutput();
//		
//		RunFamilies2Persons();
//		
//		RunInheritance();
//		
//		RunLazyRulesWithMultipleTargetPatternElements();
//		
		RunUniqueLazyRulesWithMultipleTargetPatternElements();
//		
//		RunMake2Ant();
//		
//		RunMatchedRulesWithMultipleTargetPatternElements();
//		
//		RunPetriNet2Grafcet();
//		
//		RunPetriNet2PathExp();
//		
//		RunPetriNet2PNML();
//		
//		RunPortV2();
//		
//		RunPortV3();
//		
//		RunPortV4();
//		
//		RunTestTransformation();
//		
//		RunXML2Book();
//		
//		RunXML2DXF();
//		
//		RunXML2XML();
//		
	}
	
	
	private static void RunXML2XML() throws IOException {
		String transformationLocationDefault = "resources/XML2XML/XML2XML.asm";
		String transformationLocationEmftvm = "resources/XML2XML/XML2XML.emftvm";
		
		String sourceMetamodelPath = "resources/XML2XML/XML.ecore";
		String sourceMetamodelName = "XML";
		
		String targetMetamodelPath = "resources/XML2XML/XML.ecore";
		String targetMetamodelName = "XML";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath,
				targetMetamodelName));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/XML2XML/SampleInputXML.xmi",
				"IN",
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/XML2XML/ExpectedOutputXML.xmi",
				"OUT",
				targetMetamodelPath,
				targetMetamodelName));	

		String parentLocation = "resources/XML2XML\\";
		String moduleName = "XML2XML";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunXML2DXF() throws IOException {
		String transformationLocationDefault = "resources/XML2DXF/XML2DXF.asm";
		String transformationLocationEmftvm = "resources/XML2DXF/XML2DXF.emftvm";
		
		String sourceMetamodelPath = "resources/XML2DXF/XML.ecore";
		String sourceMetamodelName = "XML";
		
		String targetMetamodelPath = "resources/XML2DXF/DXF.ecore";
		String targetMetamodelName = "DXF";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath,
				targetMetamodelName));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/XML2DXF/SampleInputXML.xmi",
				"IN",
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/XML2DXF/ExpectedOutputDXF.xmi",
				"OUT",
				targetMetamodelPath,
				targetMetamodelName));	

		String parentLocation = "resources/XML2DXF\\";
		String moduleName = "XML2DXF";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunXML2Book() throws IOException {
		String transformationLocationDefault = "resources/XML2Book/XML2Book.asm";
		String transformationLocationEmftvm = "resources/XML2Book/XML2Book.emftvm";
		
		String sourceMetamodelPath = "resources/XML2Book/XML.ecore";
		String sourceMetamodelName = "XML";
		
		String targetMetamodelPath = "resources/XML2Book/Book.ecore";
		String targetMetamodelName = "Book";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath,
				targetMetamodelName));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/XML2Book/SampleInputXML.xmi",
				"IN",
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/XML2Book/ExpectedOutputBook.xmi",
				"OUT",
				targetMetamodelPath,
				targetMetamodelName));	

		String parentLocation = "resources/XML2Book\\";
		String moduleName = "XML2Book";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunTestTransformation() throws IOException {
		String transformationLocationDefault = "resources/TestTransformation/TestTransformation.asm";
		String transformationLocationEmftvm = "resources/TestTransformation/TestTransformation.emftvm";
		
		String sourceMetamodelPath = "resources/TestTransformation/Families.ecore";
		String sourceMetamodelName = "Families";
		
		String targetMetamodelPath = "resources/TestTransformation/Persons.ecore";
		String targetMetamodelName = "Persons";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath,
				targetMetamodelName));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/TestTransformation/SampleFamilies.xmi",
				"IN",
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/TestTransformation/ExpectedOutputPersons.xmi",
				"OUT",
				targetMetamodelPath,
				targetMetamodelName));	

		String parentLocation = "resources/TestTransformation\\";
		String moduleName = "TestTransformation";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunPortV4() throws IOException {
		String transformationLocationDefault = "resources/PortV4/PortV4.asm";
		String transformationLocationEmftvm = "resources/PortV4/PortV4.emftvm";
		
		String sourceMetamodelPath = "resources/PortV4/TypeA.ecore";
		String sourceMetamodelName = "TypeA";
		
		String targetMetamodelPath = "resources/PortV4/TypeB.ecore";
		String targetMetamodelName = "TypeB";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath,
				targetMetamodelName));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/PortV4/SampleInput.xmi",
				"inA",
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/PortV4/ExpectedOutput.xmi",
				"inB",
				targetMetamodelPath,
				targetMetamodelName));	

		String parentLocation = "resources/PortV4\\";
		String moduleName = "PortV4";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunPortV3() throws IOException {
		String transformationLocationDefault = "resources/PortV3/PortV3.asm";
		String transformationLocationEmftvm = "resources/PortV3/PortV3.emftvm";
		
		String sourceMetamodelPath = "resources/PortV3/TypeA.ecore";
		String sourceMetamodelName = "TypeA";
		
		String targetMetamodelPath = "resources/PortV3/TypeB.ecore";
		String targetMetamodelName = "TypeB";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath,
				targetMetamodelName));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/PortV3/SampleInput.xmi",
				"inA",
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/PortV3/ExpectedOutput.xmi",
				"inB",
				targetMetamodelPath,
				targetMetamodelName));	

		String parentLocation = "resources/PortV3\\";
		String moduleName = "PortV3";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}

	private static void RunPortV2() throws IOException {
		String transformationLocationDefault = "resources/PortV2/PortV2.asm";
		String transformationLocationEmftvm = "resources/PortV2/PortV2.emftvm";
		
		String sourceMetamodelPath = "resources/PortV2/TypeA.ecore";
		String sourceMetamodelName = "TypeA";
		
		String targetMetamodelPath = "resources/PortV2/TypeB.ecore";
		String targetMetamodelName = "TypeB";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath,
				targetMetamodelName));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/PortV2/SampleInput.xmi",
				"inA",
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/PortV2/ExpectedOutput.xmi",
				"inB",
				targetMetamodelPath,
				targetMetamodelName));	

		String parentLocation = "resources/PortV2\\";
		String moduleName = "PortV2";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunPetriNet2PNML() throws IOException {
		String transformationLocationDefault = "resources/PetriNet2PNML/PetriNet2PNML.asm";
		String transformationLocationEmftvm = "resources/PetriNet2PNML/PetriNet2PNML.emftvm";
		
		String sourceMetamodelPath = "resources/PetriNet2PNML/PetriNet.ecore";
		String sourceMetamodelName = "PetriNet";
		
		String targetMetamodelPath = "resources/PetriNet2PNML/PNML.ecore";
		String targetMetamodelName = "PNML";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath,
				targetMetamodelName));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/PetriNet2PNML/SampleInput.xmi",
				"IN",
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/PetriNet2PNML/ExpectedOutput.xmi",
				"OUT",
				targetMetamodelPath,
				targetMetamodelName));	

		String parentLocation = "resources/PetriNet2PNML\\";
		String moduleName = "PetriNet2PNML";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunPetriNet2PathExp() throws IOException {
		String transformationLocationDefault = "resources/PetriNet2PathExp/PetriNet2PathExp.asm";
		String transformationLocationEmftvm = "resources/PetriNet2PathExp/PetriNet2PathExp.emftvm";
		
		String sourceMetamodelPath = "resources/PetriNet2PathExp/PetriNet.ecore";
		String sourceMetamodelName = "PetriNet";
		
		String targetMetamodelPath = "resources/PetriNet2PathExp/PathExp.ecore";
		String targetMetamodelName = "PathExp";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath,
				targetMetamodelName));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/PetriNet2PathExp/SamplePetrinet.xmi",
				"IN",
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/PetriNet2PathExp/ExpectedOutputPathExp.xmi",
				"OUT",
				targetMetamodelPath,
				targetMetamodelName));	

		String parentLocation = "resources/PetriNet2PathExp\\";
		String moduleName = "PetriNet2PathExp";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunPetriNet2Grafcet() throws IOException {
		String transformationLocationDefault = "resources/PetriNet2Grafcet/PetriNet2Grafcet.asm";
		String transformationLocationEmftvm = "resources/PetriNet2Grafcet/PetriNet2Grafcet.emftvm";
		
		String sourceMetamodelPath = "resources/PetriNet2Grafcet/PetriNet.ecore";
		String sourceMetamodelName = "PetriNet";
		
		String targetMetamodelPath = "resources/PetriNet2Grafcet/Grafcet.ecore";
		String targetMetamodelName = "Grafcet";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath,
				targetMetamodelName));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/PetriNet2Grafcet/SamplePetriNet.xmi",
				"IN",
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/PetriNet2Grafcet/ExpectedOutputGrafcet.xmi",
				"OUT",
				targetMetamodelPath,
				targetMetamodelName));	

		String parentLocation = "resources/PetriNet2Grafcet\\";
		String moduleName = "PetriNet2Grafcet";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunMatchedRulesWithMultipleTargetPatternElements() throws IOException {
		String transformationLocationDefault = "resources/MatchedRulesWithMultipleTargetPatternElements/MatchedRulesWithMultipleTargetPatternElements.asm";
		String transformationLocationEmftvm = "resources/MatchedRulesWithMultipleTargetPatternElements/MatchedRulesWithMultipleTargetPatternElements.emftvm";
		
		String sourceMetamodelPath1 = "resources/MatchedRulesWithMultipleTargetPatternElements/TypeA.ecore";
		String sourceMetamodelName1 = "TypeA";

		String targetMetamodelPath1 = "resources/MatchedRulesWithMultipleTargetPatternElements/TypeB.ecore";
		String targetMetamodelName1 = "TypeB";
		
		String targetMetamodelPath2 = "resources/MatchedRulesWithMultipleTargetPatternElements/TypeC.ecore";
		String targetMetamodelName2 = "TypeC";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath1,
				sourceMetamodelName1));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath1,
				targetMetamodelName1));
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath2,
				targetMetamodelName2));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/MatchedRulesWithMultipleTargetPatternElements/SampleInputTypeA.xmi",
				"IN1",
				sourceMetamodelPath1,
				sourceMetamodelName1));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/MatchedRulesWithMultipleTargetPatternElements/ExpectedOutputTypeB.xmi",
				"OUT1",
				targetMetamodelPath1,
				targetMetamodelName1));	
		targetModelInfos.add(new ModelInfo(
				"resources/MatchedRulesWithMultipleTargetPatternElements/ExpectedOutputTypeC.xmi",
				"OUT2",
				targetMetamodelPath2,
				targetMetamodelName2));	

		String parentLocation = "resources/MatchedRulesWithMultipleTargetPatternElements\\";
		String moduleName = "MatchedRulesWithMultipleTargetPatternElements";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunMake2Ant() throws IOException {
		String transformationLocationDefault = "resources/Make2Ant/Make2Ant.asm";
		String transformationLocationEmftvm = "resources/Make2Ant/Make2Ant.emftvm";
		
		String sourceMetamodelPath = "resources/Make2Ant/Make.ecore";
		String sourceMetamodelName = "Make";
		
		String targetMetamodelPath = "resources/Make2Ant/Ant.ecore";
		String targetMetamodelName = "Ant";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath,
				targetMetamodelName));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/Make2Ant/SampleInputMake.xmi",
				"IN",
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/Make2Ant/ExpectedOutputAnt.xmi",
				"OUT",
				targetMetamodelPath,
				targetMetamodelName));	

		String parentLocation = "resources/Make2Ant\\";
		String moduleName = "Make2Ant";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunLazyRulesWithMultipleTargetPatternElements() throws IOException {
		String transformationLocationDefault = "resources/LazyRulesWithMultipleTargetPatternElements/LazyRulesWithMultipleTargetPatternElements.asm";
		String transformationLocationEmftvm = "resources/LazyRulesWithMultipleTargetPatternElements/LazyRulesWithMultipleTargetPatternElements.emftvm";
		
		String sourceMetamodelPath1 = "resources/LazyRulesWithMultipleTargetPatternElements/TypeA.ecore";
		String sourceMetamodelName1 = "TypeA";

		String targetMetamodelPath1 = "resources/LazyRulesWithMultipleTargetPatternElements/TypeB.ecore";
		String targetMetamodelName1 = "TypeB";
		
		String targetMetamodelPath2 = "resources/LazyRulesWithMultipleTargetPatternElements/TypeC.ecore";
		String targetMetamodelName2 = "TypeC";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath1,
				sourceMetamodelName1));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath1,
				targetMetamodelName1));
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath2,
				targetMetamodelName2));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/LazyRulesWithMultipleTargetPatternElements/SampleInputTypeA.xmi",
				"IN1",
				sourceMetamodelPath1,
				sourceMetamodelName1));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/LazyRulesWithMultipleTargetPatternElements/ExpectedOutputTypeB.xmi",
				"OUT1",
				targetMetamodelPath1,
				targetMetamodelName1));	
		targetModelInfos.add(new ModelInfo(
				"resources/LazyRulesWithMultipleTargetPatternElements/ExpectedOutputTypeC.xmi",
				"OUT2",
				targetMetamodelPath2,
				targetMetamodelName2));	

		String parentLocation = "resources/LazyRulesWithMultipleTargetPatternElements\\";
		String moduleName = "LazyRulesWithMultipleTargetPatternElements";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunUniqueLazyRulesWithMultipleTargetPatternElements() throws IOException {
		String transformationLocationDefault = "resources/UniqueLazyRulesWithMultipleTargetPatternElements/UniqueLazyRulesWithMultipleTargetPatternElements.asm";
		String transformationLocationEmftvm = "resources/UniqueLazyRulesWithMultipleTargetPatternElements/UniqueLazyRulesWithMultipleTargetPatternElements.emftvm";
		
		String sourceMetamodelPath1 = "resources/UniqueLazyRulesWithMultipleTargetPatternElements/TypeA.ecore";
		String sourceMetamodelName1 = "TypeA";

		String targetMetamodelPath1 = "resources/UniqueLazyRulesWithMultipleTargetPatternElements/TypeB.ecore";
		String targetMetamodelName1 = "TypeB";
		
		String targetMetamodelPath2 = "resources/UniqueLazyRulesWithMultipleTargetPatternElements/TypeC.ecore";
		String targetMetamodelName2 = "TypeC";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath1,
				sourceMetamodelName1));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath1,
				targetMetamodelName1));
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath2,
				targetMetamodelName2));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/UniqueLazyRulesWithMultipleTargetPatternElements/SampleInputTypeA.xmi",
				"IN1",
				sourceMetamodelPath1,
				sourceMetamodelName1));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/UniqueLazyRulesWithMultipleTargetPatternElements/ExpectedOutputTypeB.xmi",
				"OUT1",
				targetMetamodelPath1,
				targetMetamodelName1));	
		targetModelInfos.add(new ModelInfo(
				"resources/UniqueLazyRulesWithMultipleTargetPatternElements/ExpectedOutputTypeC.xmi",
				"OUT2",
				targetMetamodelPath2,
				targetMetamodelName2));	

		String parentLocation = "resources/UniqueLazyRulesWithMultipleTargetPatternElements\\";
		String moduleName = "UniqueLazyRulesWithMultipleTargetPatternElements";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunInheritance() throws IOException {
		String transformationLocationDefault = "resources/Inheritance/Inheritance.asm";
		String transformationLocationEmftvm = "resources/Inheritance/Inheritance.emftvm";
		
		String sourceMetamodelPath = "resources/Inheritance/TypeA.ecore";
		String sourceMetamodelName = "TypeA";
		
		String targetMetamodelPath = "resources/Inheritance/TypeB.ecore";
		String targetMetamodelName = "TypeB";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath,
				targetMetamodelName));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/Inheritance/SampleInput.xmi",
				"IN",
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/Inheritance/ExpectedOutput.xmi",
				"OUT",
				targetMetamodelPath,
				targetMetamodelName));	

		String parentLocation = "resources/Inheritance\\";
		String moduleName = "Inheritance";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunA2BMultipleOutput() throws IOException {
		String transformationLocationDefault = "resources/A2BMultipleOutput/A2BMultipleOutput.asm";
		String transformationLocationEmftvm = "resources/A2BMultipleOutput/A2BMultipleOutput.emftvm";
		
		String sourceMetamodelPath1 = "resources/A2BMultipleOutput/TypeA.ecore";
		String sourceMetamodelName1 = "TypeA";

		String targetMetamodelPath1 = "resources/A2BMultipleOutput/TypeB.ecore";
		String targetMetamodelName1 = "TypeB";
		
		String targetMetamodelPath2 = "resources/A2BMultipleOutput/TypeC.ecore";
		String targetMetamodelName2 = "TypeC";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath1,
				sourceMetamodelName1));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath1,
				targetMetamodelName1));
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath2,
				targetMetamodelName2));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/A2BMultipleOutput/SampleInput.xmi",
				"IN1",
				sourceMetamodelPath1,
				sourceMetamodelName1));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/A2BMultipleOutput/ExpectedOutputTypeB.xmi",
				"OUT1",
				targetMetamodelPath1,
				targetMetamodelName1));	
		targetModelInfos.add(new ModelInfo(
				"resources/A2BMultipleOutput/ExpectedOutputTypeC.xmi",
				"OUT2",
				targetMetamodelPath2,
				targetMetamodelName2));	

		String parentLocation = "resources/A2BMultipleOutput\\";
		String moduleName = "A2BMultipleOutput";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunA2BMultipleInputSameType() throws IOException {
		String transformationLocationDefault = "resources/A2BMultipleInputSameType/A2BMultipleInputSameType.asm";
		String transformationLocationEmftvm = "resources/A2BMultipleInputSameType/A2BMultipleInputSameType.emftvm";
		
		String sourceMetamodelPath1 = "resources/A2BMultipleInputSameType/TypeA.ecore";
		String sourceMetamodelName1 = "TypeA";

		String targetMetamodelPath1 = "resources/A2BMultipleInputSameType/TypeB.ecore";
		String targetMetamodelName1 = "TypeB";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath1,
				sourceMetamodelName1));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath1,
				targetMetamodelName1));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/A2BMultipleInputSameType/SampleInput1.xmi",
				"IN1",
				sourceMetamodelPath1,
				sourceMetamodelName1));
		sourceModelInfos.add(new ModelInfo(
				"resources/A2BMultipleInputSameType/SampleInput2.xmi",
				"IN2",
				sourceMetamodelPath1,
				sourceMetamodelName1));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/A2BMultipleInputSameType/ExpectedOutput.xmi",
				"OUT",
				targetMetamodelPath1,
				targetMetamodelName1));	

		String parentLocation = "resources/A2BMultipleInputSameType\\";
		String moduleName = "A2BMultipleInputSameType";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunA2BMultipleInputDifferentType() throws IOException {
		String transformationLocationDefault = "resources/A2BMultipleInputDifferentType/A2BMultipleInputDifferentType.asm";
		String transformationLocationEmftvm = "resources/A2BMultipleInputDifferentType/A2BMultipleInputDifferentType.emftvm";
		
		String sourceMetamodelPath1 = "resources/A2BMultipleInputDifferentType/TypeA.ecore";
		String sourceMetamodelName1 = "TypeA";
		
		String sourceMetamodelPath2 = "resources/A2BMultipleInputDifferentType/TypeC.ecore";
		String sourceMetamodelName2 = "TypeC";
		
		String targetMetamodelPath1 = "resources/A2BMultipleInputDifferentType/TypeB.ecore";
		String targetMetamodelName1 = "TypeB";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath1,
				sourceMetamodelName1));
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath2,
				sourceMetamodelName2));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath1,
				targetMetamodelName1));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/A2BMultipleInputDifferentType/SampleInputTypeA.xmi",
				"IN1",
				sourceMetamodelPath1,
				sourceMetamodelName1));
		sourceModelInfos.add(new ModelInfo(
				"resources/A2BMultipleInputDifferentType/SampleInputTypeC.xmi",
				"IN2",
				sourceMetamodelPath2,
				sourceMetamodelName2));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/A2BMultipleInputDifferentType/ExpectedOutput.xmi",
				"OUT",
				targetMetamodelPath1,
				targetMetamodelName1));	

		String parentLocation = "resources/A2BMultipleInputDifferentType\\";
		String moduleName = "A2BMultipleInputDifferentType";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunA2BMultipleInputAndOutput() throws IOException {
		String transformationLocationDefault = "resources/A2BMultipleInputAndOutput/A2BMultipleInputAndOutput.asm";
		String transformationLocationEmftvm = "resources/A2BMultipleInputAndOutput/A2BMultipleInputAndOutput.emftvm";
		
		String sourceMetamodelPath1 = "resources/A2BMultipleInputAndOutput/TypeA.ecore";
		String sourceMetamodelName1 = "TypeA";
		
		String sourceMetamodelPath2 = "resources/A2BMultipleInputAndOutput/TypeB.ecore";
		String sourceMetamodelName2 = "TypeB";
		
		String targetMetamodelPath1 = "resources/A2BMultipleInputAndOutput/TypeC.ecore";
		String targetMetamodelName1 = "TypeC";
		
		String targetMetamodelPath2 = "resources/A2BMultipleInputAndOutput/TypeD.ecore";
		String targetMetamodelName2 = "TypeD";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath1,
				sourceMetamodelName1));
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath2,
				sourceMetamodelName2));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath1,
				targetMetamodelName1));
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath2,
				targetMetamodelName2));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/A2BMultipleInputAndOutput/SampleInputTypeA.xmi",
				"IN1",
				sourceMetamodelPath1,
				sourceMetamodelName1));
		sourceModelInfos.add(new ModelInfo(
				"resources/A2BMultipleInputAndOutput/SampleInputTypeB1.xmi",
				"IN2",
				sourceMetamodelPath2,
				sourceMetamodelName2));
		sourceModelInfos.add(new ModelInfo(
				"resources/A2BMultipleInputAndOutput/SampleInputTypeB2.xmi",
				"IN3",
				sourceMetamodelPath2,
				sourceMetamodelName2));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/A2BMultipleInputAndOutput/ExpectedOutputTypeC.xmi",
				"OUT1",
				targetMetamodelPath1,
				targetMetamodelName1));	
		targetModelInfos.add(new ModelInfo(
				"resources/A2BMultipleInputAndOutput/ExpectedOutputTypeD.xmi",
				"OUT2",
				targetMetamodelPath2,
				targetMetamodelName2));	

		String parentLocation = "resources/A2BMultipleInputAndOutput\\";
		String moduleName = "A2BMultipleInputAndOutput";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunA2BHelperWithoutContext() throws IOException {
		String transformationLocationDefault = "resources/A2BHelperWithoutContext/A2BHelperWithoutContext.asm";
		String transformationLocationEmftvm = "resources/A2BHelperWithoutContext/A2BHelperWithoutContext.emftvm";
		
		String sourceMetamodelPath = "resources/A2BHelperWithoutContext/TypeA.ecore";
		String sourceMetamodelName = "TypeA";
		
		String targetMetamodelPath = "resources/A2BHelperWithoutContext/TypeB.ecore";
		String targetMetamodelName = "TypeB";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath,
				targetMetamodelName));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/A2BHelperWithoutContext/SampleInput.xmi",
				"IN",
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/A2BHelperWithoutContext/ExpectedOutput.xmi",
				"OUT",
				targetMetamodelPath,
				targetMetamodelName));	

		String parentLocation = "resources/A2BHelperWithoutContext\\";
		String moduleName = "A2BHelperWithoutContext";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunA2BContainment() throws IOException {
		String transformationLocationDefault = "resources/A2BContainment/A2BContainment.asm";
		String transformationLocationEmftvm = "resources/A2BContainment/A2BContainment.emftvm";
		
		String sourceMetamodelPath = "resources/A2BContainment/TypeA.ecore";
		String sourceMetamodelName = "TypeA";
		
		String targetMetamodelPath = "resources/A2BContainment/TypeB.ecore";
		String targetMetamodelName = "TypeB";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath,
				targetMetamodelName));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/A2BContainment/SampleInput.xmi",
				"IN",
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/A2BContainment/ExpectedOutput.xmi",
				"OUT",
				targetMetamodelPath,
				targetMetamodelName));	

		String parentLocation = "resources/A2BContainment\\";
		String moduleName = "A2BContainment";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	private static void RunFamilies2Persons() throws IOException {
		String transformationLocationDefault = "resources/Families2Persons/Families2Persons.asm";
		String transformationLocationEmftvm = "resources/Families2Persons/Families2Persons.emftvm";
		
		String sourceMetamodelPath = "resources/Families2Persons/Families.ecore";
		String sourceMetamodelName = "Families";
		
		String targetMetamodelPath = "resources/Families2Persons/Persons.ecore";
		String targetMetamodelName = "Persons";
		
		List<MetamodelInfo> sourceMetamodelInfos = new ArrayList<MetamodelInfo>();
		sourceMetamodelInfos.add(new MetamodelInfo(
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<MetamodelInfo> targetMetamodelInfos = new ArrayList<MetamodelInfo>();
		targetMetamodelInfos.add(new MetamodelInfo(
				targetMetamodelPath,
				targetMetamodelName));
		
		List<ModelInfo> sourceModelInfos = new ArrayList<ModelInfo>();
		sourceModelInfos.add(new ModelInfo(
				"resources/Families2Persons/SampleFamilies.xmi",
				"IN",
				sourceMetamodelPath,
				sourceMetamodelName));
		
		List<ModelInfo> targetModelInfos = new ArrayList<ModelInfo>();
		targetModelInfos.add(new ModelInfo(
				"resources/Families2Persons/ExpectedPersons.xmi",
				"OUT",
				targetMetamodelPath,
				targetMetamodelName));	

		String parentLocation = "resources/Families2Persons\\";
		String moduleName = "Families2Persons";

		RunTransformation(transformationLocationDefault, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);
	}
	
	
	
	private static void RunTransformation(String transformationLocationDefault, String transformationLocationEmftvm, List<MetamodelInfo> sourceMetamodelInfos, List<MetamodelInfo> targetMetamodelInfos, List<ModelInfo> sourceModelInfos, List<ModelInfo> targetModelInfos,
			String parentLocation, String moduleName) throws IOException {
		ArrayList<Double> emftTimesWithoutInitialization = new ArrayList<Double>();
		ArrayList<Double> emftTimesWithInitialization = new ArrayList<Double>();
		ArrayList<Double> defaultTimesWithoutInitialization = new ArrayList<Double>();

		int n = 50;

		EmftVmRunner emftRunner = new EmftVmRunner();
		emftRunner.run(emftTimesWithoutInitialization, emftTimesWithInitialization, n, transformationLocationEmftvm, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos, parentLocation, moduleName);

		DefaultVmRunner defaultRunner = new DefaultVmRunner();
		defaultRunner.run(defaultTimesWithoutInitialization, n, transformationLocationDefault, sourceMetamodelInfos, targetMetamodelInfos, sourceModelInfos, targetModelInfos);
		
		Collections.sort(emftTimesWithoutInitialization);
		Collections.sort(emftTimesWithInitialization);
		Collections.sort(defaultTimesWithoutInitialization);
		
		Double emftTimeWithoutInitializationMedian = emftTimesWithoutInitialization.get(emftTimesWithoutInitialization.size() / 2);
		Double emftTimeWithInitializationMedian = emftTimesWithInitialization.get(emftTimesWithInitialization.size() / 2);
		Double defaultTimesWithoutInitDoubleMedian = defaultTimesWithoutInitialization.get(defaultTimesWithoutInitialization.size() / 2);

		
		OptionalDouble emftTimeWithoutInitializationDouble = emftTimesWithoutInitialization.stream().mapToDouble(d -> d).filter(x -> x < emftTimeWithoutInitializationMedian * 2).average();
		Double emftTimeWithoutInitialization = emftTimeWithoutInitializationDouble.orElse(-1);
		
		OptionalDouble emftTimeWithInitDouble = emftTimesWithInitialization.stream().mapToDouble(d -> d).filter(x -> x < emftTimeWithInitializationMedian * 2).average();
		Double emftTimeWithInitialization = emftTimeWithInitDouble.orElse(-1);

		OptionalDouble defaultTimesWithoutInitDouble = defaultTimesWithoutInitialization.stream().mapToDouble(d -> d).filter(x -> x < defaultTimesWithoutInitDoubleMedian * 2).average();
		Double defaultTimeWithoutInitialization = defaultTimesWithoutInitDouble.orElse(-1);
		
		
		
		
		
		
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator('.'); 
		DecimalFormat df = new DecimalFormat("#.00", otherSymbols);

		
		try(FileWriter fw = new FileWriter("..\\ATL2NMFSynchronizations\\performance_results\\" + moduleName + "_results.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw))
			{
			    out.println("5 " + df.format(defaultTimeWithoutInitialization) + " \"ATL default VM without initialization\"");
			    out.println("6 " + df.format(emftTimeWithoutInitialization) + " \"ATL EMFTVM  without initialization\"");
			    out.println("7 " + df.format(emftTimeWithInitialization) + " \"ATL EMFTVM  with initialization\"");
			} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
	}

}
