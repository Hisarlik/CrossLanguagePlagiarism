package com.menta.NLP.preprocess;

import java.io.File;

import org.apache.log4j.Logger;
import org.graphstream.graph.implementations.SingleGraph;

import com.menta.NLP.model.CollectionDoc;
import com.menta.NLP.model.Document;
import com.menta.NLP.model.TextFragment;
import com.menta.NLP.utils.ApplicationGetProperties;
import com.menta.NLP.utils.FreelingQueries;
import com.menta.NLP.utils.SerializeObjects;
import com.menta.NLP.model.Token;

public class FreelingPreprocess {
	
	final static Logger logger = Logger.getLogger(FreelingPreprocess.class);	
	String pathSerialized;
	
	
	public FreelingPreprocess(String path){
		this.pathSerialized = path;
	}
	
	public void preprocess(CollectionDoc c) throws Exception {
		for(int i=0; i<c.size(); i++) {
			Document d = c.get(i);
			preprocess(d);
		}
	}
	
	public void preprocess(Document d){
		
		
		FreelingQueries.query(d);
		int i=0;
		
		logger.info("Fragmentos totales de "+d.getName()+" :"+d.getFragments().size());
		for (TextFragment sf: d.getFragments()) {
			logger.info("Procesando fragmento "+(i+1)+" de: "+d.getName());
			sf.setPath(this.pathSerialized);
			sf.setName(d.getName()+i);
			sf.setDocument(d.getName());
			String completePath = preprocess(sf);
			sf.setName(completePath);

			i++;
			
		}

		
	}
	
	public String preprocess(TextFragment tf){
		
		
		// Search BabelNet Tags of Tokens
		ApplicationGetProperties properties = new ApplicationGetProperties();
		
		for(Token t: tf.getTokens()){
			
			String tag = properties.getInstance("pos_"+tf.getLanguage()+".properties")
								   .getProperty(t.getTagFreeling());
			
			if(tag == null){
				tag = properties.getInstance("pos_"+tf.getLanguage()+".properties")
						   .getProperty(t.getTagFreeling().substring(0,1));
			}
			if(tag != null){
				t.setTagBabelNet(tag);
			}
		}
		
		
	
		BabelnetPreprocess bnPreprocess = new BabelnetPreprocess(new SingleGraph("Graph 1"));
		bnPreprocess.buildGraph(tf);
		SerializeObjects ser = new SerializeObjects();
		System.out.println(tf);
		System.out.println();
		String path = this.pathSerialized+"tf/"+tf.getDocument();
		File dir = new File(path);
		dir.mkdirs();
		String completePath = ser.SaveToDiskTextFragment(path, tf);
		return completePath;
		
		
		
	}
	
}
