package atl.performance.validation;

import org.eclipse.m2m.atl.core.IReferenceModel;

public class ReferenceModel {

	public ReferenceModel(IReferenceModel model, String location) {
		this.model = model;
		this.location = location;
	}
	
	
	public IReferenceModel model;
	public String location;
	
}
