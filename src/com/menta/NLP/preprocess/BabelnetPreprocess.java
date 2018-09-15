package com.menta.NLP.preprocess;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;


import com.menta.NLP.model.TextFragment;
import com.menta.NLP.model.Token;

import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;


import org.apache.log4j.Logger;


import com.menta.NLP.babelnet.BabelNetUtils;

public class BabelnetPreprocess {

	final static Logger logger = Logger.getLogger(BabelnetPreprocess.class);


	

	public Graph graph;
	BabelNetUtils bnUtils;
    String synsetSearched = null;
    static List<String> originalSynsets = new ArrayList<String>();
    static List<BabelSynset> allSynsets = new ArrayList<BabelSynset>();
    public List<BabelSynset> pathInside = new ArrayList<BabelSynset>();
	
	public BabelnetPreprocess(){
		

	}
    
    
    public BabelnetPreprocess(Graph graph){
		
		this.graph = graph;
	}
	
	
	public Graph buildGraph(TextFragment tf){
		
		
		//Init Graph
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Graph graph =  initGraph(tf);
		graph.setNullAttributesAreErrors(true);
		//graph.addAttribute("ui.stylesheet", "node { text-alignment: above; text-size: 40 }");
		graph.addAttribute("ui.stylesheet", "node { size-mode: fit; text-size:10;text-background-mode:plain;text-background-color:yellow;}");

		searchRelations();
		
		// Display Graph
		for (Node n : graph) {

			if(n!=null && n.getOutDegree()>0){
				logger.debug("ID:"+n.getId()+" ,NOMBRE: "+n.getAttribute("name")+" ,GRADO: "+n.getDegree()+" ,INPUT DEGREE:"+n.getInDegree()+" ,OUTPUT DEGREE:"+n.getOutDegree());
			}
	    }
		
		logger.info("Número de nodos final: "+numberNodes());	
	

		tf.setGraphTextFragment(graph);
		
		return graph;
		
	}
	
	// Compare 2 Graph 
	public Double compareGraphs(Graph graph1, Graph graph2){
		
		Double intersection = similarityIntersection(graph1, graph2);
		Double union = similarityUnion(graph1, graph2);

		
		return 2*intersection/union;
	}
	
	private Graph graphsUnion(Graph graph1, Graph graph2) {
		
		Graph graph = new SingleGraph("Union");
		
		for (Node node : graph1) {
	
			if(graph.getNode(node.getId())==null){
				//logger.info("ID:"+node.getId()+" ,NOMBRE: "+node.getAttribute("name")+" e()+");
				graph.addNode(node.getId());

			}
	    }
		for (Node node : graph2) {
			
			if(graph.getNode(node.getId())==null){
				//logger.info("ID:"+node.getId()+" ,NOMBRE: "+node.getAttribute("name")+" e()+");
				graph.addNode(node.getId());

			}
	    }
		
		return graph;
	}


	private Graph graphsIntersection(Graph graph1, Graph graph2) {
		Graph graph = new SingleGraph("Inter");
		
		for (Node node : graph1) {
	
			if(graph2.getNode(node.getId())!=null){
				Node node2 = graph2.getNode(node.getId());
				//logger.info("ID:"+node.getId()+" ,NOMBRE: "+node.getAttribute("name")+" e()+");
				Node end = graph.addNode(node.getId());
				Double value = (double) ((node.getDegree() + node2.getDegree())/2);
				end.setAttribute("valueEdge", value);
//				n.setAttribute("name", no.toString());
//				n.setAttribute("synId", syn.getId());
//				n.setAttribute("lemma", t.getLemma());
//				n.setAttribute("listSynsets", listSynsets);
//				n.setAttribute("synset", syn);
//				n.addAttribute("ui.style", "fill-color: rgb(0,100,255);");
			}
	    }
		return graph;
	}	
	
	
	private Double similarityUnion(Graph graph1, Graph graph2) {
		//Graph d = graphsUnion(graph1,graph2);
		
		Double g1 = nodeWeightReal(graph1);
		Double g2 = nodeWeightReal(graph2);
		
		return g1+g2;
	}



	private Double similarityIntersection(Graph graph1, Graph graph2) {
		Graph d = graphsIntersection(graph1,graph2);
		return nodeWeight(d);
	}
	
	private Double nodeWeight(Graph graph) {
		Double value = 0.0;
		for (Node node : graph) {
			
			if(graph.getNode(node.getId())!=null){
			 
				Double weight = (Double) node.getAttribute("valueEdge");
				value += weight;

			}
	    }
		return (double) value;
	}

	private Double nodeWeightReal(Graph graph) {
		Double value = 0.0;
		for (Node node : graph) {
			
			if(graph.getNode(node.getId())!=null){
			 
				Double weight = (double) node.getDegree();
				value += weight;

			}
	    }
		return (double) value;
	}

	private Graph initGraph(TextFragment tf){
		
	
		
		bnUtils = new BabelNetUtils(BabelNet.getInstance());
		
		
		// Para cada token que cumpla los requisitos se busca su synsets
		for(Token t: tf.getBabelNetTokens()){
			
			List<BabelSynset> listSynsets = bnUtils.findSynsetsFromToken(t, tf.language);
			logger.debug("------------------------------------------");
			logger.debug("Lemma: "+t.getLemma());
			for(BabelSynset syn: listSynsets){
				if(searchNodeById(syn.getId()) == null){
					BabelnetPreprocess.originalSynsets.add(syn.getId().toString());
					logger.debug("Syn: "+syn+" , ID: "+syn.getId());
					Node n = graph.addNode((syn.getId().toString()));
					n.setAttribute("name", syn.toString());
					n.setAttribute("synId", syn.getId());
					n.setAttribute("lemma", t.getLemma());
					n.setAttribute("listSynsets", listSynsets);
					n.setAttribute("synset", syn);
					n.addAttribute("ui.style", "fill-color: rgb(0,100,255);");
				}
			}
					
		}
		logger.info("Número de nodos inicial: "+numberNodes());
		return graph;
		
	}


	private void searchRelations(){
		List<BabelSynset> listNodes = new ArrayList<BabelSynset>();
		for (Node node : graph) {
			
			BabelSynset by = bnUtils.getBabelSynset(node.getId());
			if(by !=null){
				listNodes.add(by);
			}
	    }
		
		int total = listNodes.size();
		for (BabelSynset by : listNodes) {
			this.pathInside = new ArrayList<BabelSynset>();
			logger.debug("-------------------------");
			logger.debug("Nodo: "+by.getId()+" , Nombre: "+by+ " ,quedan: "+total--);
			
			if(by !=null){
				this.synsetSearched = by.getId().toString();
				
				int nivel=0;
				searchFromOneNode(by,2,nivel);
			}
	    }
		
	}
	
	private void searchFromOneNode(BabelSynset synset, int maxPath,int nivel){

		nivel++;

		if(maxPath>0){
			this.pathInside.add(synset);
			List<BabelSynsetID> listSynsets = bnUtils.retrieveNeighborsBabelSynset(synset.getId());
			if(listSynsets.size()>10 & nivel>0){
				listSynsets = listSynsets.subList(0, 9);
			}
			
			if(nivel!=2){
				logger.debug("NODO:"+this.synsetSearched+" NIVEL:"+nivel+" Vecinos:"+listSynsets.size()+ " de "+synset.getId());
			}
			maxPath--;
			
			logger.debug("-----"+synset+"-------");
			for(BabelSynsetID bsID : listSynsets){

				if(!this.synsetSearched.equals(bsID.getID())){
				
				
		
					Node nodeRelationship = null;
					
					int index = BabelnetPreprocess.originalSynsets.indexOf(bsID.getID().toString());
					if(index != -1){				
						nodeRelationship = searchNodeById(bsID);
					}

					
				
					if(nodeRelationship != null){
						if(!checkSameToken(synset,nodeRelationship)){

							boolean nodeCreated = checkRelation(synset,nodeRelationship);
							
							
							
						}else{
							logger.debug("De la misma raiz");
						}
						
					}else if(maxPath>0){
						
						try {
							searchFromOneNode(bsID.toBabelSynset(), maxPath, nivel);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				
				}else{
					logger.debug("El Mismo de origen");
				}
				
			}
		}
		this.pathInside.remove(synset);
		
	}
	
	private Node searchNodeById(BabelSynsetID bsID){
		
		boolean found = false;
		Node n = null;

		
		try {
			n = graph.getNode(bsID.getID());

			if(n!=null){
				
				found = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return n;
	}
	
	private boolean searchNode(String nameNode){
		
		boolean found = false;


		
		try {
			Node n = graph.getNode(nameNode);
		

			if(n!=null){
				
				found = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
		
		
		return found;
	}
	
	
	
	
	private boolean checkSameToken(BabelSynset by, Node node){
		
		boolean found = false;
		if(node.hasAttribute("listSynsets")){
			List<BabelSynset> listSynsets = node.getAttribute("listSynsets");
			
			for(BabelSynset graphSynset: listSynsets){
				//System.out.println("CheckSameToken:"+by.getId().toString()+ "|| "+graphSynset.getId());
				//System.out.println("OriginToken:"+this.synsetSearched+ "|| "+graphSynset.getId().toString());
				
				
				
				if(by.getId().toString().equals(graphSynset.getId().toString()) ||
						this.synsetSearched.equals(graphSynset.getId().toString())
						){
					
					found = true;
					break;
				}
			}
		}
		return found;

	}
	

	private boolean checkRelation(BabelSynset by, Node node2){
		
		for(BabelSynset synsetPath: this.pathInside){
			Node nodePath = searchNodeById(synsetPath.getId());
			if(nodePath==null){
				Node n = graph.addNode((synsetPath.getId().toString()));
				n.setAttribute("name", synsetPath.toString());
				n.setAttribute("synId", synsetPath.getId());
				n.setAttribute("synset", synsetPath);
				
			}
		}
	
		Node node1 = searchNodeById(by.getId());
		
		if(node1!= null && node2!=null){
		
			Edge edge = node1.getEdgeBetween(node2);
			if(edge == null){
				graph.addEdge(node1.getId()+"_"+node2.getId(), node1, node2);
				logger.debug("EDGE: " +node1.getId()+" A: "+ node2.getId());

			}
		}else if(node2!=null){
			Node n = graph.addNode((by.getId().toString()));
			n.setAttribute("name", by.toString());
			n.setAttribute("synId", by.getId());
			n.setAttribute("synset", by);
			graph.addEdge(n.getId()+"_"+node2.getId(), n, node2);
			logger.debug("EDGE: " +n.getId()+" A: "+ node2.getId());
			
		}
		if(this.pathInside.size()>1){
			for(int i=1;i<this.pathInside.size();i++){
				Node nodePath1 = searchNodeById(this.pathInside.get(i-1).getId());
				Node nodePath2 = searchNodeById(this.pathInside.get(i).getId());
				if(nodePath1!=null && nodePath2!=null){
					Edge edge = nodePath1.getEdgeBetween(nodePath2);
					if(edge == null){
						graph.addEdge(nodePath1.getId()+"_"+nodePath2.getId(), nodePath1, nodePath2);
						logger.debug("EDGE: " +nodePath1.getId()+" A: "+ nodePath2.getId());
					}
				}
			}
		}
		
		
		
		return false;
	}
	
	
	private int numberNodes(){
		
		int nodes = 0;
		for (Node node : this.graph) {
	        nodes++;
	    }
		return nodes;
		
	}
	

}
