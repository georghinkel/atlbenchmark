package atl.change.performance.validation;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.m2m.atl.core.ModelFactory;
import org.eclipse.m2m.atl.core.emf.EMFModel;
import org.eclipse.m2m.atl.core.emf.EMFModelFactory;
import org.eclipse.m2m.atl.core.emf.EMFReferenceModel;
import org.eclipse.m2m.atl.emftvm.EmftvmFactory;
import org.eclipse.m2m.atl.emftvm.ExecEnv;
import org.eclipse.m2m.atl.emftvm.Metamodel;
import org.eclipse.m2m.atl.emftvm.Model;
import org.eclipse.m2m.atl.emftvm.Module;
import org.eclipse.m2m.atl.emftvm.impl.resource.EMFTVMResourceFactoryImpl;
import org.eclipse.m2m.atl.emftvm.profiler.StopWatch;
import org.eclipse.m2m.atl.emftvm.util.DefaultModuleResolver;
import org.eclipse.m2m.atl.emftvm.util.ModuleResolver;
import org.eclipse.m2m.atl.emftvm.util.TimingData;

import atl.performance.validation.MetamodelInfo;
import atl.performance.validation.ModelInfo;

public class EmftVmChangeRunner {

	public void run(long[][] emftTimes, String basePath, int[] sizes, int n, int workloadSize,
			String transformationLocation, MetamodelInfo sourcemmInfo, MetamodelInfo targetmmInfo, ModelInfo sourceModelInfo, ModelInfo targetModelInfo,
			String parentLocation, String moduleName) throws IOException {
		
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("emftvm", new EMFTVMResourceFactoryImpl());

		ResourceSet resourceSet = new ResourceSetImpl();
		ExecEnv env = EmftvmFactory.eINSTANCE.createExecEnv();
			
		// Source metamodel
		URI sourcemmUri = URI.createURI(sourcemmInfo.path);
		Metamodel sourcemm = EmftvmFactory.eINSTANCE.createMetamodel();
		sourcemm.setResource(resourceSet.getResource(sourcemmUri, true));
		env.registerMetaModel(sourcemmInfo.name, sourcemm);
		registerPackages(resourceSet, sourcemm.getResource());

		// Target metamodel
		URI targetmmUri = URI.createURI(targetmmInfo.path);
		Metamodel targetmm = EmftvmFactory.eINSTANCE.createMetamodel();
		targetmm.setResource(resourceSet.getResource(targetmmUri, true));
		env.registerMetaModel(targetmmInfo.name, targetmm);
		registerPackages(resourceSet, targetmm.getResource());

		for(int j=0; j<sizes.length; j++) {
			
			for(int iteration=0; iteration<n; iteration++) {
				int size = sizes[j];
				
				System.out.println("EmftVm transformation run [Model size: " + size +"; Iteration: " + iteration + ";]");
				
				long overallTime = 0;
				
				for(int i=0; i<workloadSize; i++) {	
					env.clearModels();

					String inputModelPath = basePath + size + "/" + iteration + "/" + "inputModel" + i + ".xmi";
					
					// Input model
					URI inputUri = URI.createURI(inputModelPath, true);
					Model input = EmftvmFactory.eINSTANCE.createModel();
					input.setResource(resourceSet.getResource(inputUri, true));
					env.registerInputModel(sourceModelInfo.name, input);

					// Output model
					URI outputUri = URI.createFileURI(targetModelInfo.path);
					Model output = EmftvmFactory.eINSTANCE.createModel();
					output.setResource(resourceSet.createResource(outputUri));
					env.registerOutputModel(targetModelInfo.name, output);

					// Load and run module		
					ModuleResolver mr = new DefaultModuleResolver(parentLocation, new ResourceSetImpl());
					TimingData td = new TimingData();

					Module module = env.loadModule(mr, moduleName);
					td.finishLoading();
					
					long start = System.nanoTime();
					
					env.run(td);
					
					long end = System.nanoTime();
					td.finish();

					long workloadTime = (end - start);
					overallTime += workloadTime;
					
					//Unload all models
					input.getResource().unload();
					output.getResource().unload();
					
					System.gc();
					System.runFinalization();
				}
				
				emftTimes[j][iteration] = overallTime;
			}
		}
	}
	
	private static void registerPackages(ResourceSet rs, Resource resource) {
		EObject eObject = resource.getContents().get(0);
		if (eObject instanceof EPackage) {
			EPackage p = (EPackage)eObject;
			rs.getPackageRegistry().put(p.getNsURI(), p);
		}
	}
}
