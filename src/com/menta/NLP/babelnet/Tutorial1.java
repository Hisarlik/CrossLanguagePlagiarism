package com.menta.NLP.babelnet;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.Toolkit;

public class Tutorial1 {
	public static void main(String args[]) {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Graph graph = new SingleGraph("Tutorial 1");
		graph.addAttribute("ui.stylesheet", "node { size-mode: fit; text-size:40;text-background-mode:plain;text-background-color:yellow;}");


		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addEdge("AB", "A", "B");
		graph.addEdge("BC", "B", "C");
		graph.addEdge("CA", "C", "A");
		
		for(Node n:graph) {
			System.out.println(n.getId());
			n.setAttribute("name", n.getId());
		}

		int [] degree = Toolkit.degreeDistribution(graph);
		for(int i : degree )
		System.out.println(i);
		for (Node node : graph) {
	        node.addAttribute("ui.label", node.getAttribute("name").toString());
	    }

		graph.display();

	}

}
