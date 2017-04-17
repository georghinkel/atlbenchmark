package atl.performance.validation;

public class ModelInfo {

	public ModelInfo(String path, String name, String metamodelPath, String metamodelName) {
		this.path = path;
		this.name = name;
		
		this.metamodelPath = metamodelPath;
		this.metamodelName = metamodelName;
	}
	
	public String path;
	public String name;
	
	public String metamodelPath;
	public String metamodelName;
}
