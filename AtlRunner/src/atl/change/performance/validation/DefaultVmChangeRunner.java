package atl.change.performance.validation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.m2m.atl.core.IExtractor;
import org.eclipse.m2m.atl.core.IInjector;
import org.eclipse.m2m.atl.core.IModel;
import org.eclipse.m2m.atl.core.IReferenceModel;
import org.eclipse.m2m.atl.core.ModelFactory;
import org.eclipse.m2m.atl.core.emf.EMFExtractor;
import org.eclipse.m2m.atl.core.emf.EMFInjector;
import org.eclipse.m2m.atl.core.emf.EMFModel;
import org.eclipse.m2m.atl.core.emf.EMFModelFactory;
import org.eclipse.m2m.atl.core.emf.EMFReferenceModel;
import org.eclipse.m2m.atl.core.launch.ILauncher;
import org.eclipse.m2m.atl.emftvm.EmftvmFactory;
import org.eclipse.m2m.atl.emftvm.Model;
import org.eclipse.m2m.atl.emftvm.Module;
import org.eclipse.m2m.atl.emftvm.profiler.StopWatch;
import org.eclipse.m2m.atl.emftvm.util.DefaultModuleResolver;
import org.eclipse.m2m.atl.emftvm.util.ModuleResolver;
import org.eclipse.m2m.atl.emftvm.util.TimingData;
import org.eclipse.m2m.atl.engine.emfvm.launch.EMFVMLauncher;

import atl.performance.validation.MetamodelInfo;
import atl.performance.validation.ModelInfo;
import atl.performance.validation.ReferenceModel;

public class DefaultVmChangeRunner {

	public void run(long[][] defaultTimes, String basePath, int[] sizes, int n, int workloadSize,
			String transformationLocation, MetamodelInfo sourcemmInfo, MetamodelInfo targetmmInfo, ModelInfo inputModelInfo, ModelInfo outputModelInfo) throws IOException {
	
		for(int j=0; j<sizes.length; j++) {
			
			for(int iteration=0; iteration<n; iteration++) {
				int size = sizes[j];
				
				System.out.println("DefaultVm transformation run [Model size: " + size +"; Iteration: " + iteration + ";]");
				
				long overallTime = 0;
				
				for(int i=0; i<workloadSize; i++) {	
					String inputModelPath = basePath + size + "/" + iteration + "/" + "inputModel" + i + ".xmi";
					
					try {
						//Initializations
						ModelFactory modelFactory = new EMFModelFactory();
						IInjector injector = new EMFInjector();
						IExtractor extractor = new EMFExtractor();

						ILauncher transformationLauncher = new EMFVMLauncher();
						transformationLauncher.initialize(new HashMap<String, Object>());
						
						//initialize metamodels
						IReferenceModel sourceMetamodel = modelFactory.newReferenceModel();
						injector.inject(sourceMetamodel, sourcemmInfo.path);

						IReferenceModel targetMetamodel = modelFactory.newReferenceModel();
						injector.inject(targetMetamodel, targetmmInfo.path);

						//initialize models
						IModel sourceModel = modelFactory.newModel(sourceMetamodel);
						injector.inject(sourceModel, inputModelPath);
						transformationLauncher.addInModel(sourceModel, inputModelInfo.name, inputModelInfo.metamodelName);	
				
						IModel targetModel = modelFactory.newModel(targetMetamodel);
						injector.inject(targetModel, outputModelInfo.path);						
						transformationLauncher.addOutModel(targetModel, outputModelInfo.name, outputModelInfo.metamodelName);
							
						long start = System.nanoTime();
						
						transformationLauncher.launch(ILauncher.RUN_MODE, new NullProgressMonitor(), new HashMap<String, Object>(),
								new FileInputStream(transformationLocation));

						long end = System.nanoTime();

						long workloadTime = (end - start);
						overallTime += workloadTime;

						//Unload all models and metamodels (EMF-specific)
						EMFModelFactory emfModelFactory = (EMFModelFactory) modelFactory;
						emfModelFactory.unload((EMFModel) sourceModel);
						emfModelFactory.unload((EMFModel) targetModel);
						emfModelFactory.unload((EMFReferenceModel) sourceMetamodel);
						emfModelFactory.unload((EMFReferenceModel) targetMetamodel);

					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
				
				defaultTimes[j][iteration] = overallTime;
			}
		}
	}
}
