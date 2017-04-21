package atl.syncatl.validation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SyncAtlRunner {

	public static void main(String[] args) throws IOException, InterruptedException  {
		
        //iterations
		int n = 10;
		int workloadSize = 20;
		int[] sizes = new int[] { 10, 50, 100, 500, 1000, 5000, 10000 };

		long[][] syncTimes = new long[ sizes.length ][ n ];
	
		String basePath = "..\\makeInputModelsSync\\";
        String targetBasePath = "..\\makeTargetModels\\";
		
		for(int j=0; j<sizes.length; j++) {
		
			for(int iteration=0; iteration<n; iteration++) {			
				int size = sizes[j];			
				System.out.println("SyncATL transformation run [Model size: " + size +"; Iteration: " + iteration + ";]");
				long overallTime = 0;
				
				//initial transformation
				String initSourceModel = basePath + size + "\\" + iteration + "\\" + "inputModel0.xmi";
				String initTargetModel = targetBasePath + size + "\\" + iteration + "\\" + "targetModel0.xmi"; 
				
		        Process ps=Runtime.getRuntime().exec(new String[]{"java","-jar","syncatl\\BiXM.jar",  "--f", "--trans", "file:syncatl\\Make2Ant.asm", "--src", "IN="+initSourceModel, "Make=syncatl\\Make.ecore", "--tgt", "OUT="+initTargetModel, "Ant=syncatl\\Ant.ecore"});
		        ps.waitFor();
		        java.io.InputStream is=ps.getInputStream();
		        byte b[]=new byte[is.available()];
		        is.read(b,0,b.length);
		        System.out.println(new String(b));
							
				for(int i=0; i<workloadSize-1; i++) {	
					String sourceModel = basePath + size + "\\" + iteration + "\\" + "inputModel" + i +".xmi";
					String targetModel = targetBasePath + size + "\\" + iteration + "\\" + "targetModel" + i + ".xmi"; 
					
					String modifiedSourceModel = basePath + size + "\\" + iteration + "\\" + "inputModel" + (i+1) +".xmi";
								
					String synchronizedSourceModel = targetBasePath + size + "\\" + iteration + "\\" + "sourceModel" + (i+1) + ".xmi"; 
					String synchronizedTargetModel = targetBasePath + size + "\\" + iteration + "\\" + "targetModel" + (i+1) + ".xmi"; 
								
					long start = System.nanoTime();
					
			        Process ps2=Runtime.getRuntime().exec(new String[]{"java", "-jar", "syncatl\\BiXM.jar", "--s", "--trans", "file:syncatl\\Make2Ant.asm", "--src", "IN="+sourceModel, "Make=syncatl\\Make.ecore", "--tgt", "OUT="+targetModel, "Ant=syncatl\\Ant.ecore", "--modifiedSrc", "IN="+modifiedSourceModel, "--outSrc", "IN="+synchronizedSourceModel, "--outTgt", "OUT="+synchronizedTargetModel});
			        ps2.waitFor();
			        java.io.InputStream is2=ps2.getInputStream();
			        byte b2[]=new byte[is2.available()];
			        is2.read(b2,0,b2.length);
			        
			        long end = System.nanoTime();
			        
			        System.out.println(new String(b2));	      
		
					overallTime += end - start;
				}
				syncTimes[j][iteration] = overallTime;
			}

		}

		try(FileWriter fw = new FileWriter("..\\syncAtlResult.csv", false);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw))
				{
					out.println("Size;Iteration;SyncATL");
			        for (int sizeIdx = 0; sizeIdx < sizes.length; sizeIdx++)
			        {
			            int sizeN = sizes[sizeIdx];
			            
			            for (int iteration = 0; iteration < n; iteration++)
			            {
			                long syncTime = (syncTimes[sizeIdx][iteration]);
			                out.println(sizeN + ";" + iteration + ";" + syncTime);
			            }			            
			        }
				} catch (IOException e) {
				    //exception handling left as an exercise for the reader
				}
	}
}
