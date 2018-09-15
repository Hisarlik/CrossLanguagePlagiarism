package com.menta.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import com.menta.NLP.model.Document;
import com.menta.NLP.model.TextFragment;
import com.menta.NLP.model.Token;
import com.menta.NLP.utils.ApplicationGetProperties;
import com.menta.NLP.utils.CSVUtils;
import com.menta.NLP.utils.ReadFiles;
import com.menta.NLP.utils.SerializeObjects;
import com.menta.NLP.preprocess.BabelnetPreprocess;

import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.data.BabelPOS;
import it.uniroma1.lcl.jlt.util.Language;



public class Score {
	final static Logger logger = Logger.getLogger(Score.class);
	static String CSV_SUSPECT_CASES_FILE = "data/suspectCasesPruebaSuspect.csv";
	static String CSV_REAL_CASES_FILE = "data/realCasesPruebaSuspect.csv";
	static HashMap<String, ArrayList<String>> mapSuspect;
	static HashMap<String, ArrayList<String>> mapReal;
	
	public static void main( String[] args ) 
    {
		

		
    	try{
    		
    		String pathSuspicious = "data/serialized/pruebaSuspect/";
			String pathSource = "data/serialized/pruebaOriginal/";
			NumberFormat formatter = new DecimalFormat("#0.00000");
			long start = System.currentTimeMillis();
			
    		List<File> corpusSuspicious = new ArrayList<File>();
    		List<File> corpusSource = new ArrayList<File>();
    		List<String> listPlagiarism;
  	      	File folderSuspicious = new File(pathSuspicious);
  	      	File folderSource = new File(pathSource);  	      	
  	      	ReadFiles listFiles = new ReadFiles();
  	      	corpusSuspicious = listFiles.listAllFilesSerialized(folderSuspicious);
  	      	corpusSource = listFiles.listAllFilesSerialized(folderSource);
    		
    		int totalPlagio = 0;
    		
    		// Creación de fichero para guardar los casos de plagio

			FileWriter writer = new FileWriter(CSV_SUSPECT_CASES_FILE);
    		
    		
    		
    		for(File fileSuspicious : corpusSuspicious){
    			
    			// Instancia de lista de positivos para un determinado sospechoso
    			listPlagiarism = new ArrayList<String>();
    			

	    		SerializeObjects ser12 = new SerializeObjects(pathSuspicious);
	    		ser12.LoadFromDiskDocument(fileSuspicious.getPath());
	    		Document doc11 = ser12.doc;
	    		ser12.LoadFromDiskTextsFragment(pathSuspicious,doc11.getName());
	    		doc11.setFragments(ser12.textFragments);
	    		logger.info("Score archivo: "+doc11.getName());
	    		
    			// Se añade en la primera posición el archivo sospechoso
    			listPlagiarism.add(doc11.getName());
	    		
	    		
	    	
	    		for(File fileSource : corpusSource){
	    			if(fileSuspicious.getName().equals(fileSource.getName())){
	    				logger.info("Mismo archivo"+fileSuspicious.getName());
	    				break;
	    			}
	    			logger.info("Test con source: "+fileSource.getName());
	    			SerializeObjects ser22 = new SerializeObjects(pathSource);
	        		ser22.LoadFromDiskDocument(fileSource.getPath());
	        		Document doc22 = ser22.doc;    
	        		ser22.LoadFromDiskTextsFragment(pathSource,doc22.getName());
	        		doc22.setFragments(ser22.textFragments);
	        	
		    		
	        		Graph graph1 = null;
	        		Graph graph2 = null;
	        		
	        		
	        	    List<Graph> original = new ArrayList<Graph>();
	        	    List<Graph> sus = new ArrayList<Graph>();
	        	    
	        	  
	        		for(TextFragment tt: doc11.getFragments()){
	        	
	        			graph1 = tt.getGraphTextFragment();   		 			
	        			original.add(graph1);
	        			
	        			//logger.info("Número de nodos: "+graph1.getNodeCount());	
	        		}
	        		
	        	    //logger.info("Sospechos");
	        		for(TextFragment tt: doc22.getFragments()){

	     
	        			graph2 = tt.getGraphTextFragment();
	        			sus.add(graph2);
	        			//logger.info("Número de nodos: "+graph2.getNodeCount());	
	        		}
	        		

	        		BabelnetPreprocess bp = new BabelnetPreprocess();
	        		
	        		Double maxTotal=0.0;
	        		int candidato=0;
	        		for(Graph g1: original){
		        
	        			Double maxFragmento = 0.0;
	        			for(Graph g2: sus){
	        				
	        		  		Double similar = bp.compareGraphs(g1, g2); 	  
	        		  		if(similar>maxFragmento){
	        		  			maxFragmento=similar;
	        		  		}
	        		  		
        		  			
	        		  		logger.info("El grado de similitud es:"+similar);	        		  		

	        	    		
	        				
	        			}
	        			logger.info("El max del Fragmento es:"+maxFragmento);	       
	        			if(maxFragmento>maxTotal)
	        				maxTotal=maxFragmento;
	        			if(maxFragmento>0.3){
	        				candidato++;
	        			}else{
	        				candidato=0;
	        			}
	        			if(candidato==3){
	        				logger.info("PLAGIO!!!");
	        				logger.info("Nombre documento:"+doc22.getName());
	        				totalPlagio++;
	        				//listPlagiarism.add(doc22.getName());
	        				if(!listPlagiarism.contains(doc22.getName())){
	        					listPlagiarism.add(doc22.getName());
							 }
	        			}
	        			
	        			
	        		}
	        		logger.info("");
        			logger.info("El max del Documento es:"+maxTotal);
            		// Se guarda el listado de positivos
            		
        			//break;
        			
        			
        			
		    		
		    		
		    		
	    		}
	    		CSVUtils.writeLine(writer, listPlagiarism, '\t');
	    		
	    		
    		}
    		// se guardan todos los casos y se cierra el archivo
		    writer.flush();
		    writer.close();
    		logger.info("Totales:"+totalPlagio);
    		
    			
    		compareResults();
    	
    		
    	} catch (Exception e) {
			
			e.printStackTrace();
		}
    }
	
	public static void compareResults() {
		

		
		mapSuspect = recoverDataFromCsv(CSV_SUSPECT_CASES_FILE);
		mapReal = recoverDataFromCsv(CSV_REAL_CASES_FILE);
		

		
		scoreDataset();

	
		
		
		
	}
	
	public static HashMap<String, ArrayList<String>> recoverDataFromCsv(String path){
		
		HashMap<String, ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
		
		try{
	        Scanner scanner = new Scanner(new File(path));
	        while (scanner.hasNext()) {
	            List<String> line = CSVUtils.parseLine(scanner.nextLine());
	            //System.out.println("Document: " + line.get(0) + "Suspect Cases: "+ line.size());
	            String key = line.remove(0);
	            map.put(key, (ArrayList<String>) line);
	          
	                        
	        }
	        scanner.close();
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return map;
		
	}
	
	public static double scoreDataset(){
		
		double value, f1scoreValue;
		value = 0;
		
		f1scoreValue = f1score();
		System.out.println("f1score:"+f1scoreValue);
		
		return f1scoreValue;
		
	}
	
	public static double precision(){
		
		double prec=0;
		int detections = 0;
		int real=0;
		
		for(Map.Entry<String, ArrayList<String>> entry : mapSuspect.entrySet()) {
		    String key = entry.getKey();
		    ArrayList<String> value = entry.getValue();
		    //System.out.println("Key:"+key+", value:"+value);
		    for(String suspect: value){
		    	detections++;
		    	if(mapReal.get(key)!=null){
		    		//System.out.println("Key SI:"+key);
		    		//System.out.println("Encontrado:"+mapReal.get(key).contains(suspect));
		    		if(mapReal.get(key).contains(suspect)){
		    			real++;
		    		}
		    	}
		    }

		}
		
	    System.out.println("real:"+real+", detections:"+detections);
	    prec = real/(double)detections;		
		
		
		return prec;
	}
	
	public static double recall(){
		double recall=0;
		
		int detections = 0;
		int all=0;
		
		for(Map.Entry<String, ArrayList<String>> entry : mapReal.entrySet()) {
		    String key = entry.getKey();
		    ArrayList<String> value = entry.getValue();
		    //System.out.println("Key:"+key+", value:"+value);
		    for(String suspect: value){
		    	all++;
		    	if(mapSuspect.get(key)!=null){
		    		//System.out.println("Key SI:"+key);
		    		//System.out.println("Encontrado:"+mapReal.get(key).contains(suspect));
		    		if(mapSuspect.get(key).contains(suspect)){
		    			detections++;
		    		}
		    	}
		    }

		}
	    System.out.println("All:"+all+", detections:"+detections);
	    recall = detections/(double)all;
		
		return recall;
	}
	public static double f1score(){
		double f1score=0;
		double precision = precision();
		System.out.println("Precision:"+precision);
		double recall = recall();
		System.out.println("recall:"+recall);
		
		if(precision!=0 || recall !=0){
			f1score = (2*precision*recall)/(precision + recall);
		}
		return f1score;
	}
	
	

}
