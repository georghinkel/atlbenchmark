package atl.performance.validation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
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

public class EmftVmRunner {


	public void run(List<Double> timesWithoutInitialization, List<Double> timesWithInitialization, int n,
			String transformationLocation, List<MetamodelInfo> sourcemmInfos, List<MetamodelInfo> targetmmInfos, List<ModelInfo> inputModelInfos, List<ModelInfo> outputModelInfos,
			String parentLocation, String moduleName) throws IOException {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("emftvm", new EMFTVMResourceFactoryImpl());

		runWithoutInitialization(timesWithoutInitialization, n, transformationLocation, sourcemmInfos, targetmmInfos, inputModelInfos, outputModelInfos, parentLocation, moduleName);
		runWithInitialization(timesWithInitialization, n, transformationLocation, sourcemmInfos, targetmmInfos, inputModelInfos, outputModelInfos, parentLocation, moduleName);
	}

	private void runWithInitialization(List<Double> times, int n, String transformationLocation, List<MetamodelInfo> sourcemmInfos, List<MetamodelInfo> targetmmInfos, List<ModelInfo> inputModelInfos, List<ModelInfo> outputModelInfos,
			String parentLocation, String moduleName) {
		
		ResourceSet resourceSet = new ResourceSetImpl();
		ExecEnv env = EmftvmFactory.eINSTANCE.createExecEnv();
		
		// Source metamodel
		for(MetamodelInfo sourceMetamodelInfo : sourcemmInfos) {
			URI sourcemmUri = URI.createURI(sourceMetamodelInfo.path);
			Metamodel sourcemm = EmftvmFactory.eINSTANCE.createMetamodel();
			sourcemm.setResource(resourceSet.getResource(sourcemmUri, true));
			env.registerMetaModel(sourceMetamodelInfo.name, sourcemm);
			registerPackages(resourceSet, sourcemm.getResource());
		}
		
		// Target metamodel
		for(MetamodelInfo targetMetamodelInfo : targetmmInfos) {
			URI targetmmUri = URI.createURI(targetMetamodelInfo.path);
			Metamodel targetmm = EmftvmFactory.eINSTANCE.createMetamodel();
			targetmm.setResource(resourceSet.getResource(targetmmUri, true));
			env.registerMetaModel(targetMetamodelInfo.name, targetmm);
			registerPackages(resourceSet, targetmm.getResource());
		}
		
		// Input model
		for(ModelInfo inputModelInfo : inputModelInfos) {
			URI inputUri = URI.createURI(inputModelInfo.path, true);
			Model input = EmftvmFactory.eINSTANCE.createModel();
			input.setResource(resourceSet.getResource(inputUri, true));
			env.registerInputModel(inputModelInfo.name, input);
		}
		
		// Output model
		for(ModelInfo outputModelInfo : outputModelInfos) {
			URI outputUri = URI.createFileURI(outputModelInfo.path);
			Model output = EmftvmFactory.eINSTANCE.createModel();
			output.setResource(resourceSet.createResource(outputUri));
			env.registerOutputModel(outputModelInfo.name, output);
		}

		// Load and run module		
		ModuleResolver mr = new DefaultModuleResolver(parentLocation, new ResourceSetImpl());
		TimingData td = new TimingData();

		Module module = env.loadModule(mr, moduleName);
		td.finishLoading();
		
		final StopWatch stopwatchInitial = new StopWatch();
		stopwatchInitial.start();
		
		env.run(td);
		
		stopwatchInitial.stop();
		td.finish();

		for(int i=0; i < n+5; i++) {
			env.clearModels();
			// Input model
			for(ModelInfo inputModelInfo : inputModelInfos) {
				URI inputUri = URI.createURI(inputModelInfo.path, true);
				Model input = EmftvmFactory.eINSTANCE.createModel();
				input.setResource(resourceSet.getResource(inputUri, true));
				env.registerInputModel(inputModelInfo.name, input);
			}
			
			// Output model
			for(ModelInfo outputModelInfo : outputModelInfos) {
				URI outputUri = URI.createFileURI(outputModelInfo.path);
				Model output = EmftvmFactory.eINSTANCE.createModel();
				output.setResource(resourceSet.createResource(outputUri));
				env.registerOutputModel(outputModelInfo.name, output);
			}

			// Load and run module		
			td = new TimingData();
			td.finishLoading();
			
			final StopWatch stopwatch = new StopWatch();
			stopwatch.start();
			
			env.run(td);
			
			stopwatch.stop();
			td.finish();
			
			if(i >= 5) {
				Double time = stopwatch.getDuration() / 1000000.0;
				times.add(time);
			}
		}
	}
	
	private void runWithoutInitialization(List<Double> times, int n, String transformationLocation, List<MetamodelInfo> sourcemmInfos, List<MetamodelInfo> targetmmInfos, List<ModelInfo> inputModelInfos, List<ModelInfo> outputModelInfos,
			String parentLocation, String moduleName) {
		
		for(int i=0; i < n+5; i++) {
			ResourceSet resourceSet = new ResourceSetImpl();
			ExecEnv env = EmftvmFactory.eINSTANCE.createExecEnv();
			
			// Source metamodel
			for(MetamodelInfo sourceMetamodelInfo : sourcemmInfos) {
				URI sourcemmUri = URI.createURI(sourceMetamodelInfo.path);
				Metamodel sourcemm = EmftvmFactory.eINSTANCE.createMetamodel();
				sourcemm.setResource(resourceSet.getResource(sourcemmUri, true));
				env.registerMetaModel(sourceMetamodelInfo.name, sourcemm);
				registerPackages(resourceSet, sourcemm.getResource());
			}
			
			// Target metamodel
			for(MetamodelInfo targetMetamodelInfo : targetmmInfos) {
				URI targetmmUri = URI.createURI(targetMetamodelInfo.path);
				Metamodel targetmm = EmftvmFactory.eINSTANCE.createMetamodel();
				targetmm.setResource(resourceSet.getResource(targetmmUri, true));
				env.registerMetaModel(targetMetamodelInfo.name, targetmm);
				registerPackages(resourceSet, targetmm.getResource());
			}
			
			// Input model
			for(ModelInfo inputModelInfo : inputModelInfos) {
				URI inputUri = URI.createURI(inputModelInfo.path, true);
				Model input = EmftvmFactory.eINSTANCE.createModel();
				input.setResource(resourceSet.getResource(inputUri, true));
				env.registerInputModel(inputModelInfo.name, input);
			}
			
			// Output model
			for(ModelInfo outputModelInfo : outputModelInfos) {
				URI outputUri = URI.createFileURI(outputModelInfo.path);
				Model output = EmftvmFactory.eINSTANCE.createModel();
				output.setResource(resourceSet.createResource(outputUri));
				env.registerOutputModel(outputModelInfo.name, output);
			}

			// Load and run module		
			ModuleResolver mr = new DefaultModuleResolver(parentLocation, new ResourceSetImpl());
			TimingData td = new TimingData();
			
			final StopWatch stopwatch = new StopWatch();
			stopwatch.start();
			
			Module module = env.loadModule(mr, moduleName);
			td.finishLoading();
			env.run(td);
			
			stopwatch.stop();
			
			td.finish();

			if(i >= 5) {
				Double time = stopwatch.getDuration() / 1000000.0;
				times.add(time);
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
