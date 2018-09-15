package com.menta.NLP.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;

import com.menta.NLP.preprocess.BabelnetPreprocess;
import com.menta.NLP.preprocess.FreelingPreprocess;



public class Document implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String document;
	private Language lan;
	List<TextFragment> fragments;


	// Constructors
	
	public Document(String name, String document) {
		super();
		this.setName(name);
		this.document = document;

	}

	
	
	// Methods
	
	public void preprocess(String path) throws Exception {


		FreelingPreprocess freeling = new FreelingPreprocess(path);
		freeling.preprocess(this);
		
	}
	
	public Double similarityDocument(Document doc){
		BabelnetPreprocess babelnetPre = new BabelnetPreprocess();
		for(TextFragment tf: this.getFragments()){
			for(TextFragment doc_tf: doc.getFragments()){
				Double similarityGraph = babelnetPre.compareGraphs(tf.getGraphTextFragment(),doc_tf.getGraphTextFragment());
			}
		}
		
		
		return 0.0;
	}

	
	// Getters and setters

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}



	public Language getLan() {
		return lan;
	}



	public void setLan(Language lan) {
		this.lan = lan;
	}



	public List<TextFragment> getFragments() {
		return fragments;
	}



	public void setFragments(List<TextFragment> fragments) {
		this.fragments = fragments;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	
    private void writeObject(ObjectOutputStream aOutputStream) throws ClassNotFoundException, IOException
    {
    	try {
	    	
	    	aOutputStream.writeUTF(name);
	    	aOutputStream.writeUTF(document);
	        aOutputStream.writeObject(lan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	 private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
	    {      
		 			 
		 	name= aInputStream.readUTF();
		 	document= aInputStream.readUTF();
		 	lan= (Language) aInputStream.readObject();
		 	

	    }

	
	
	
	

}
