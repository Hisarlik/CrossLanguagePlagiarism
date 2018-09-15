package com.menta.NLP.model;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;

public class TextFragment implements Serializable{
	


	
	// Constructors
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TextFragment() {
		super();
	

	}
	public String name;
	public List<Token> tokens;
	public String language;
	public String document;
	public Graph graphTextFragment;
	public String path;

	public Graph getGraphTextFragment() {
		return graphTextFragment;
	}

	public void setGraphTextFragment(Graph graphTextFragment) {
		this.graphTextFragment = graphTextFragment;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public List<Token> getBabelNetTokens(){
		List<Token> lt = new ArrayList<Token>();
		for(Token t: this.tokens){
			if(t.getTagBabelNet()!=null){
				lt.add(t);
			}
		}
		return lt;
	}
	
	 private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
	    {      
		 	name= aInputStream.readUTF();
		 	language = aInputStream.readUTF();
		 	document = aInputStream.readUTF();
		 	tokens = (List<Token>) aInputStream.readObject();
		 	path = aInputStream.readUTF();
    		graphTextFragment = new SingleGraph("name");
    		try {
				graphTextFragment.read(path+"tf/"+document+"/"+name+".dgs");
			} catch (ElementNotFoundException | GraphParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	 
	    private void writeObject(ObjectOutputStream aOutputStream) throws ClassNotFoundException, IOException
	    {
	    	try {
	    		String pathFinal = this.path+"tf/"+document;
				File dir = new File(pathFinal);
				dir.mkdirs();
		    	graphTextFragment.write(pathFinal+"/"+name+".dgs");
		    	aOutputStream.writeUTF(name);
		    	aOutputStream.writeUTF(language);
		    	aOutputStream.writeUTF(document);
		        aOutputStream.writeObject(tokens);
		        aOutputStream.writeUTF(path);
		        
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	        
	        

	    }
	    
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}


}
