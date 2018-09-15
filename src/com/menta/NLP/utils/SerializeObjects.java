package com.menta.NLP.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.menta.NLP.model.Document;
import com.menta.NLP.model.TextFragment;
import com.menta.main.Score;


public class SerializeObjects {
	final static Logger logger = Logger.getLogger(SerializeObjects.class);
	public Document doc;
	public TextFragment tf;
	public List<TextFragment> textFragments;
	public String pathFiles;

	

	public SerializeObjects(){
		
		
	}
	public SerializeObjects(String path){
		
		this.pathFiles = path;
	}
	
	public SerializeObjects(Document document){
		this.doc = document;
	}
	

	
	public void SaveToDiskDocument(String path){
	
		try {
			File dir = new File(path);
			dir.mkdirs();
	        FileOutputStream fileOut =
	        new FileOutputStream(path+"/"+this.doc.getName()+".ser");
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(this.doc);
	        out.close();
	        fileOut.close();
	        System.out.printf("Serialized data is saved: "+this.doc.getName()+".ser");
	     } catch (IOException i) {
	        i.printStackTrace();
	     }
	}
	
	public void LoadFromDiskDocument(String name){
		
	    try
	    {
	        FileInputStream fis = new FileInputStream(name);
	        ObjectInputStream ois = new ObjectInputStream(fis);
	        this.doc = (Document) ois.readObject();
	        
	        
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace(); 
	        logger.error("Error al recuperar:" +name);
	    }
	     
	 }
	
	public String SaveToDiskTextFragment(String path, TextFragment tf){
		
		String completePath = null;
		try {
			
			File dir = new File(path);
			dir.mkdirs();
			completePath = path+"/"+tf.getName()+".tf";
	        FileOutputStream fileOut =
	        new FileOutputStream(completePath);
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(tf);
	        out.close();
	        fileOut.close();
	        tf.setGraphTextFragment(null);
	       
	     } catch (IOException i) {
	        i.printStackTrace();
	     }
		return completePath;
	}
	
	public TextFragment LoadFromDiskTextFragment(String path){
		
		try
	    {
	        FileInputStream fis = new FileInputStream(path);
	        ObjectInputStream ois = new ObjectInputStream(fis);
	        tf = (TextFragment) ois.readObject();
	        
	        
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace(); 
	        logger.error("Error al recuperar el grafo :" +path);
	        
	    }
		
		return tf;
		
					
		
	}
	
	public void LoadFromDiskTextsFragment(String path, String name){
		
		textFragments = new ArrayList<TextFragment>();
		
		try
	    {
			String completePath =  path+"tf/"+name;
			int numberFiles = new File(completePath).listFiles().length;
			//System.out.println("Numero de Fragmentos:"+numberFiles/2);
			for(int i=0;(i<(numberFiles/2));i++){
				String pathTextFragment = completePath + "/" + name + i + ".tf";
				TextFragment tf = LoadFromDiskTextFragment(pathTextFragment);
				this.textFragments.add(tf);
				
			}
	        
	        
	        
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace(); 
	    }
		
					
		
	}
	
	
	

}
