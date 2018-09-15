package com.menta.NLP.babelnet;

import java.io.IOException;
import java.util.List;

import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.babelnet.BabelSynsetIDRelation;
import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;
import it.uniroma1.lcl.babelnet.data.BabelPOS;
import it.uniroma1.lcl.jlt.util.Language;

public class App {

	public static void main(String[] args) {
		
		
		try {
			BabelNet bn = BabelNet.getInstance();
			
			
			List<BabelSynset> byl = bn.getSynsets("you", Language.EN, BabelPOS.NOUN);
			
			for(BabelSynset bs: byl){
				System.out.println(bs.getMainGloss(Language.EN));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 }		
		
//		 try {
//			BabelNet bn = BabelNet.getInstance();
//			    BabelSynset by = bn.getSynset(new BabelSynsetID("bn:00044492n"));
//			    for(BabelSynsetIDRelation edge : by.getEdges()) {
//			        System.out.println(by.getId()+"\t"+by.getMainSense(Language.EN).getLemma()+" - "
//			            + edge.getPointer()+" - "
//			            + edge.getBabelSynsetIDTarget());
//			    }
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvalidBabelSynsetIDException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    }
		
		

	

}
