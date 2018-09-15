package com.menta.NLP.model;

public enum Language {
	
	ENGLISH ("en"),
	SPANISH ("es")
	;
	
	private final String langCode;

    Language(String lang) {
        this.langCode = lang;
    }

	public String getLangCode() {
		return langCode;
	}
    
    

}
