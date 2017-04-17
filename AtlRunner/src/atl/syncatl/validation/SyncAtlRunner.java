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

import org.eclipse.m2m.atl.emftvm.profiler.StopWatch;

public class SyncAtlRunner {

	public static void main(String[] args) throws IOException, InterruptedException  {

		try {
//	        Process ps=Runtime.getRuntime().exec(new String[]{"java","-jar","syncatl\\BiXM.jar",  "--f", "--trans", "file:syncatl\\Make2Ant.asm", "--src", "IN=syncatl\\Source.Make", "Make=syncatl\\Make.ecore", "--tgt", "OUT=syncatl\\target.Ant", "Ant=syncatl\\Ant.ecore"});
//	        ps.waitFor();
//	        java.io.InputStream is=ps.getInputStream();
//	        byte b[]=new byte[is.available()];
//	        is.read(b,0,b.length);
//	        System.out.println(new String(b));
//
//	        
//	        //@java -ea -jar ../BiXM.jar --s --trans file:Make2Ant.asm --src IN=Source.Make Make=Make.ecore --tgt OUT=target-modified.Ant Ant=Ant.ecore --modifiedSrc IN=source-modified.Make --outSrc IN=source-synchronized.Make --outTgt OUT=target-synchronized.Ant --checkif
//	        Process ps2=Runtime.getRuntime().exec(new String[]{"java", "-jar", "syncatl\\BiXM.jar", "--s", "--trans", "file:syncatl\\Make2Ant.asm", "--src", "IN=syncatl\\Source.Make", "Make=syncatl\\Make.ecore", "--tgt", "OUT=syncatl\\target-modified.Ant", "Ant=syncatl\\Ant.ecore", "--modifiedSrc", "IN=syncatl\\source-modified.Make", "--outSrc", "IN=syncatl\\source-synchronized.Make", "--outTgt", "OUT=syncatl\\target-synchronized.Ant"});
//	        ps2.waitFor();
//	        java.io.InputStream is2=ps2.getInputStream();
//	        byte b2[]=new byte[is2.available()];
//	        is2.read(b2,0,b2.length);
//	        System.out.println(new String(b2));	        
	        
//-----
//	        Process ps=Runtime.getRuntime().exec(new String[]{"java","-jar","syncatl\\BiXM.jar",  "--f", "--trans", "file:syncatl\\Make2Ant.asm", "--src", "IN=syncatl\\makeInputModels\\10\\0\\inputModel0.xmi", "Make=syncatl\\Make.ecore", "--tgt", "OUT=syncatl\\targetModel.xmi", "Ant=syncatl\\Ant.ecore"});
//	        ps.waitFor();
//	        java.io.InputStream is=ps.getInputStream();
//	        byte b[]=new byte[is.available()];
//	        is.read(b,0,b.length);
//	        System.out.println(new String(b));
//
//	        //																																																										Hier immer das Ergebnis von vorher
////@java -ea -jar BiXM.jar --s --trans file:Make2Ant.asm 																									--src IN=Source.Make 										Make=Make.ecore 		--tgt 	OUT=target-modified.Ant 			Ant=Ant.ecore 			--modifiedSrc	 IN=source-modified.Make 								--outSrc 	IN=source-synchronized.Make			 --outTgt 		OUT=target-synchronized.Ant --checkif
//	        
//	        Process ps2=Runtime.getRuntime().exec(new String[]{"java", "-jar", "syncatl\\BiXM.jar", "--s", "--trans", "file:syncatl\\Make2Ant.asm", "--src", "IN=syncatl\\makeInputModels\\10\\0\\inputModel0.xmi", "Make=syncatl\\Make.ecore", "--tgt", "OUT=syncatl\\targetModel.xmi", "Ant=syncatl\\Ant.ecore", "--modifiedSrc", "IN=syncatl\\makeInputModels\\10\\0\\inputModel1.xmi", "--outSrc", "IN=syncatl\\source-synchronized.xmi", "--outTgt", "OUT=syncatl\\target-synchronized.xmi"});
//	        ps2.waitFor();
//	        java.io.InputStream is2=ps2.getInputStream();
//	        byte b2[]=new byte[is2.available()];
//	        is2.read(b2,0,b2.length);
//	        System.out.println(new String(b2));	        
//	        
//	        
//	        
//	        Process ps3=Runtime.getRuntime().exec(new String[]{"java", "-jar", "syncatl\\BiXM.jar", "--s", "--trans", "file:syncatl\\Make2Ant.asm", "--src", "IN=syncatl\\makeInputModels\\10\\0\\inputModel1.xmi", "Make=syncatl\\Make.ecore", "--tgt", "OUT=syncatl\\target-synchronized.xmi", "Ant=syncatl\\Ant.ecore", "--modifiedSrc", "IN=syncatl\\makeInputModels\\10\\0\\inputModel2.xmi", "--outSrc", "IN=syncatl\\source-synchronized1.xmi", "--outTgt", "OUT=syncatl\\target-synchronized1.xmi"});
//	        ps3.waitFor();
//	        java.io.InputStream is3=ps3.getInputStream();
//	        byte b3[]=new byte[is3.available()];
//	        is3.read(b3,0,b3.length);
//	        System.out.println(new String(b3));	        


	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        //iterations
		int n = 3;
		int workloadSize = 100;
		int[] sizes = new int[] {10,100,200,300,400,500,600,700,800,900,1000};

		double[][] syncTimes = new double[ sizes.length ][ n ];
	
		String basePath = "syncatl\\makeInputModels\\";
        String targetBasePath = "syncatl\\targetModels\\";
		
		for(int j=0; j<sizes.length; j++) {
		
			for(int iteration=0; iteration<n; iteration++) {			
				int size = sizes[j];			
				System.out.println("SyncATL transformation run [Model size: " + size +"; Iteration: " + iteration + ";]");
				Double overallTime = 0.0;
				
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
								
					final StopWatch stopwatch = new StopWatch();
					stopwatch.start();
					
			        Process ps2=Runtime.getRuntime().exec(new String[]{"java", "-jar", "syncatl\\BiXM.jar", "--s", "--trans", "file:syncatl\\Make2Ant.asm", "--src", "IN="+sourceModel, "Make=syncatl\\Make.ecore", "--tgt", "OUT="+targetModel, "Ant=syncatl\\Ant.ecore", "--modifiedSrc", "IN="+modifiedSourceModel, "--outSrc", "IN="+synchronizedSourceModel, "--outTgt", "OUT="+synchronizedTargetModel});
			        ps2.waitFor();
			        java.io.InputStream is2=ps2.getInputStream();
			        byte b2[]=new byte[is2.available()];
			        is2.read(b2,0,b2.length);
			        System.out.println(new String(b2));	      
					
					stopwatch.stop();
		
					Double workloadTime = stopwatch.getDuration() / 1000000.0;
					overallTime += workloadTime;
				}
				syncTimes[j][iteration] = overallTime;
			}

		}

		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator('.'); 
		DecimalFormat df = new DecimalFormat("0.00", otherSymbols);

		try(FileWriter fw = new FileWriter("syncatl\\syncAtlResult.txt", false);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw))
				{
			        for (int sizeIdx = 0; sizeIdx < sizes.length; sizeIdx++)
			        {
			    		List<Double> syncAtlList = new ArrayList<Double>();

			            int sizeN = sizes[sizeIdx];
			            
			            for (int iteration = 0; iteration < n; iteration++)
			            {
			                double syncTime = (syncTimes[sizeIdx][iteration]);
			                syncAtlList.add(syncTimes[sizeIdx][iteration]);
			            }
		
			            Collections.sort(syncAtlList);
			            double median = syncAtlList.get(syncAtlList.size() / 2);            
					    out.println(sizeN +" " + df.format(median));			            
			        }
				} catch (IOException e) {
				    //exception handling left as an exercise for the reader
				}
		
		try(FileWriter fw1 = new FileWriter("syncatl\\syncAtlResultAllValues.txt", false);
				BufferedWriter bw1 = new BufferedWriter(fw1);
				PrintWriter out1 = new PrintWriter(bw1))
				{
			
			        for (int sizeIdx = 0; sizeIdx < sizes.length; sizeIdx++)
			        {
			    		List<Double> syncAtlList = new ArrayList<Double>();
			            int sizeN = sizes[sizeIdx];
			            
			            for (int iteration = 0; iteration < n; iteration++)
			            {
			                double syncTime = (syncTimes[sizeIdx][iteration]);  
			                syncAtlList.add(syncTimes[sizeIdx][iteration]);
			            }
		
			            Collections.sort(syncAtlList);
			            out1.println("size: " + sizeN);
			            
			            for (int i = 0; i < syncAtlList.size(); i++)
			            {
						    out1.println(df.format(syncAtlList.get(i)));
			            }
			        }
			
				} catch (IOException e) {
				    //exception handling left as an exercise for the reader
				}
	}
}
