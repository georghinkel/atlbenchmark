package atl.performance.validation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
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
import org.eclipse.m2m.atl.emftvm.profiler.StopWatch;
import org.eclipse.m2m.atl.engine.emfvm.launch.EMFVMLauncher;


public class DefaultVmRunner {

	public void run(List<Double> timesWithoutInitialization, int n,
			String transformationLocation, List<MetamodelInfo> sourcemmInfos, List<MetamodelInfo> targetmmInfos, List<ModelInfo> inputModelInfos, List<ModelInfo> outputModelInfos) throws IOException {
	
		for(int i=0; i < n+5; i++) {		
			try {
				ModelFactory modelFactory = new EMFModelFactory();
				IInjector injector = new EMFInjector();
				IExtractor extractor = new EMFExtractor();

				ILauncher transformationLauncher = new EMFVMLauncher();
				transformationLauncher.initialize(new HashMap<String, Object>());
				
				List<ReferenceModel> referenceModels = new ArrayList<ReferenceModel>();

				//initialize metamodels
				for(MetamodelInfo sourcemmInfo : sourcemmInfos) {
					IReferenceModel sourceMetamodel = modelFactory.newReferenceModel();
					injector.inject(sourceMetamodel, sourcemmInfo.path);
					
					referenceModels.add(new ReferenceModel(sourceMetamodel, sourcemmInfo.path));				
				}
				
				for(MetamodelInfo targetmmInfo : targetmmInfos) {
					IReferenceModel targetMetamodel = modelFactory.newReferenceModel();
					injector.inject(targetMetamodel, targetmmInfo.path);
					
					referenceModels.add(new ReferenceModel(targetMetamodel, targetmmInfo.path));				
				}

				//initialize models
				for(ModelInfo inputModelInfo : inputModelInfos) {
					
					ReferenceModel referenceModel = referenceModels.stream()
							.filter(x -> x.location.equals(inputModelInfo.metamodelPath))
				            .findFirst()
				            .get();
					
					IModel sourceModel = modelFactory.newModel(referenceModel.model);
					injector.inject(sourceModel, inputModelInfo.path);
					
					transformationLauncher.addInModel(sourceModel, inputModelInfo.name, inputModelInfo.metamodelName);
				}
				
				for(ModelInfo outputModelInfo : outputModelInfos) {	
					ReferenceModel referenceModel = referenceModels.stream()
							.filter(x -> x.location.equals(outputModelInfo.metamodelPath))
				            .findFirst()
				            .get();

					IModel targetModel = modelFactory.newModel(referenceModel.model);
					injector.inject(targetModel, outputModelInfo.path);
					
					transformationLauncher.addOutModel(targetModel, outputModelInfo.name, outputModelInfo.metamodelName);
				}
				
				final StopWatch stopwatch = new StopWatch();
				stopwatch.start();
				
				transformationLauncher.launch(ILauncher.RUN_MODE, new NullProgressMonitor(), new HashMap<String, Object>(),
						new FileInputStream(transformationLocation));

				stopwatch.stop();

				if(i >= 5) {
					Double time = stopwatch.getDuration() / 1000000.0;
					timesWithoutInitialization.add(time);
				}
				
//				extractor.extract(targetModel, outputModelLocation);	
				
//				//Unload all models and metamodels (EMF-specific)
//				EMFModelFactory emfModelFactory = (EMFModelFactory) modelFactory;
//				emfModelFactory.unload((EMFModel) sourceModel);
//				emfModelFactory.unload((EMFModel) targetModel);
//				emfModelFactory.unload((EMFReferenceModel) sourceMetamodel);
//				emfModelFactory.unload((EMFReferenceModel) targetMetamodel);

			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
}
