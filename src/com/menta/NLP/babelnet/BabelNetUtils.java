package com.menta.NLP.babelnet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.menta.NLP.model.Token;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.babelnet.BabelSynsetIDRelation;
import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;
import it.uniroma1.lcl.babelnet.data.BabelPOS;
import it.uniroma1.lcl.jlt.util.Language;

public class BabelNetUtils {
	
	private BabelNet bn;
	final static Logger logger = Logger.getLogger(BabelNetUtils.class);
	public BabelNetUtils(BabelNet bn){
		
		this.bn = bn;
	}
	
	
	
	public static BabelPOS getPosBabel(String pos){
		
		switch(pos) {
		   case "ADJECTIVE" :
		      return BabelPOS.ADJECTIVE;
		       
		   case "NOUN" :
		      return BabelPOS.NOUN;
		    
		   case "ADVERB" :
			      return BabelPOS.ADVERB;
			      
		   case "VERB" :
			      return BabelPOS.VERB;
		
		}
		return null;
		
	}
	
	public static Language getLanguageBabel(String language){
		
		switch(language) {
		   case "es" :
		      return Language.ES;
		       
		   case "en" :
			  return Language.EN;
		    

		
		}
		return null;
		
	}
	
	public List<BabelSynset> findSynsetsFromToken(Token t, String lan){
		
		List<BabelSynset> byl = null;
	
		try {
			logger.debug("");			
			logger.debug("-----------------------");		
			logger.debug("Lemma: "+t.getLemma());
			logger.debug("Lenguage: "+lan);			
			logger.debug("tag: "+ t.getTagBabelNet());			
			
			 byl = bn.getSynsets(t.getLemma(), getLanguageBabel(lan), t.getTagBabelNet());
			
			for(BabelSynset bs: byl){
				logger.debug(bs.toString()+"   "+bs.getId());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return byl;
	
	}
	
	public List<BabelSynsetID> retrieveNeighborsBabelSynset(BabelSynsetID synId){
		
        BabelSynset by;
        List<BabelSynsetID> listBabelSynsets = null;
		try {
		    	listBabelSynsets= new ArrayList<BabelSynsetID>();
				by = bn.getSynset(synId);
				for(BabelSynsetIDRelation edge : by.getEdges()) {
//			            System.out.println(by.getId()+"\t"+by.getMainSense(Language.ES).getLemma()+" - "
//			                + edge.getPointer()+" - "
//			                + edge.getBabelSynsetIDTarget());
			            BabelSynsetID bs = edge.getBabelSynsetIDTarget();
			            listBabelSynsets.add(bs);
			    }
				
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

       
		
		
		return listBabelSynsets;
		
	}
	
	public BabelSynset getBabelSynset(String nameSynset){
		
		BabelSynset by = null;
		
		 try {
			 
			by = bn.getSynset(new BabelSynsetID(nameSynset));
		} catch (IOException | InvalidBabelSynsetIDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return by;
		
	}
	
	
	
	

}
