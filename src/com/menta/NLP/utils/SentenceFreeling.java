package com.menta.NLP.utils;

import java.io.Serializable;
import java.util.List;
import com.menta.NLP.model.Token;

public class SentenceFreeling implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<Token> tokens;

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	
	
	
	
	
	
	
}



