package com.menta.NLP.babelnet;

import com.menta.NLP.model.CollectionDoc;
import com.menta.NLP.model.Language;

public class TestLanguage {

	public static void main(String[] args) {
		
		CollectionDoc doc = new CollectionDoc(Language.SPANISH);
		
	
		System.out.println(doc.getLan());
		
		

	}

}
