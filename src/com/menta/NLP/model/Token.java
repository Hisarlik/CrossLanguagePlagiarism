package com.menta.NLP.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import com.menta.NLP.babelnet.BabelNetUtils;

import edu.mit.jwi.item.POS;
import it.uniroma1.lcl.babelnet.data.BabelPOS;

public class Token implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String form, lemma, tagFreeling;
	BabelPOS tagBabelNet;

	
	// Constructors
	
	public Token(String form) {
		super();
		this.form = form;
	}
	
	
	public Token(String form, String lemma) {
		super();
		this.form = form;
		this.lemma = lemma;
	}
	
	public Token(String form, String lemma, String tagFreeling) {
		super();
		this.form = form;
		this.lemma = lemma;
		this.tagFreeling = tagFreeling;
	}


	public String getForm() {
		return form;
	}


	public void setForm(String form) {
		this.form = form;
	}


	public String getLemma() {
		return lemma;
	}


	public void setLemma(String lemma) {
		this.lemma = lemma;
	}


	public String getTagFreeling() {
		return tagFreeling;
	}


	public void setTagFreeling(String tagFreeling) {
		this.tagFreeling = tagFreeling;
	}


	public BabelPOS getTagBabelNet() {
		return tagBabelNet;
	}


	public void setTagBabelNet(String tagBabelNet) {
		this.tagBabelNet = BabelNetUtils.getPosBabel(tagBabelNet);
	}

	
	
	


	// Getters and setters
//
//	 private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
//	    {      
//		 	form = aInputStream.readUTF();
//		 	lemma = aInputStream.readUTF();
//		 	tagFreeling = aInputStream.readUTF();
//		 	
//
//	    }
//	 
//	    private void writeObject(ObjectOutputStream aOutputStream) throws IOException
//	    {
//	        aOutputStream.writeUTF(form);
//	        aOutputStream.writeUTF(lemma);	    
//	        aOutputStream.writeUTF(tagFreeling);	        
//
//	    }
	
	

}
