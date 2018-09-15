package com.menta.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;


import com.menta.NLP.model.Document;

import com.menta.NLP.utils.ReadFiles;
import com.menta.NLP.utils.SerializeObjects;
import com.menta.NLP.preprocess.BabelnetPreprocess;





public class CorpusSerialization {
	final static Logger logger = Logger.getLogger(CorpusSerialization.class);
	
	public static void main( String[] args ) 
    {
		
			String originPath = "data/corpus/pruebaSuspect/";
			String destinyPath = "data/serialized/pruebaSuspect/";
			NumberFormat formatter = new DecimalFormat("#0.00000");
			long start = System.currentTimeMillis();
			
    		List<File> corpus = new ArrayList<File>();
  	      	File folder = new File(originPath);
  	      	ReadFiles listFiles = new ReadFiles();
  	      	corpus = listFiles.listAllFiles(folder);
    		
    		for(File file: corpus){
    			
    			try{
    				Document doc = null;
	        		Scanner s = new Scanner(new FileReader(file));
	    			String text = s.useDelimiter("\\A").next();   
	    			System.out.println(file.getName());
	    			
	    			long startDocument = System.currentTimeMillis();


	    		   			
	    			doc = new Document(file.getName(), text);
	    			doc.preprocess(destinyPath);   
	        		s.close();		
	    			

	        		long endDocument = System.currentTimeMillis();


	    			logger.info("Execution time Document "+file.getName()+" is " + formatter.format((endDocument - startDocument) / 1000d) + " seconds");
	        		
	        		
	        		
	        		SerializeObjects ser11 = new SerializeObjects(doc);
	        		ser11.SaveToDiskDocument(destinyPath);
	        		
	        		
	        		//break;
	        		
	        	
        		
		    	}catch(FileNotFoundException e){
		        		
		        		e.printStackTrace();
		        		
		        } catch (Exception e) {
		    			
		    			e.printStackTrace();
		    	}
    			

    			
    		}
			long end = System.currentTimeMillis();
			logger.info("Execution Total time is " + formatter.format((end - start) / 1000d) + " seconds");

    	
    }
}
    			
    			


    			
 
    	
    		
    		
 		
	
    		
    		
    		
    	