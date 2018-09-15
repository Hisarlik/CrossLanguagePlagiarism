package com.menta.NLP.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXml {
	
	
	public static Double returnTotalSize(String path){
			
			String xmlpath = path.substring(0, path.lastIndexOf(".")+1)+"xml";
			List<String> files = ReadXml.read(xmlpath);
			return ReadXml.fileXmlSize(files);

	}
	
	
	
	public static Double fileXmlSize(List<String> listFiles){
		
		Double totalsize=0.0;
		
		
		for(String path: listFiles){
			  String finalpath = path.replace("suspicious-documents", "source-documents");
		      File file = new File(finalpath);
		      //System.out.println("Tamaño de "+file.getName()+":"+file.length());
		      totalsize += (double) (file.length());

		}
		
		//System.out.println("Tamaño:"+totalsize);
	
		
		return totalsize;
		
	}
	
	public static List<String> listPlagiarismDocuments(String path){
		List<String> listFiles = new ArrayList<String>();
		try{
			
			String new_path = path.substring(0, path.lastIndexOf("/")+1);
			File xmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(xmlFile);
			
			doc.getDocumentElement().normalize();
			
			
			NodeList nList = doc.getElementsByTagName("feature");
			
			for( int temp=0; temp < nList.getLength(); temp++){
				Node nNode = nList.item(temp);
				
				
				if(nNode.getNodeType() == Node.ELEMENT_NODE){
					 Element eElement  = (Element) nNode;
					 if(eElement.getAttribute("source_reference") != ""){

						 String namefile_copied = eElement.getAttribute("source_reference");
						 if(!listFiles.contains(namefile_copied)){
							 listFiles.add(namefile_copied);
						 }
						 
					 }
				}
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return listFiles;
	}
	
	
	public static List<String> read(String path){
		
		List<String> listFilesCopied = new ArrayList<String>();
		
		try{
			String new_path = path.substring(0, path.lastIndexOf("/")+1);
			//System.out.println(new_path);
			File xmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(xmlFile);
			
			doc.getDocumentElement().normalize();
			
			//System.out.println("Root element :"+doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("feature");
			
			for( int temp=0; temp < nList.getLength(); temp++){
				Node nNode = nList.item(temp);
				
				//System.out.println("\n Current Element :"+ nNode.getNodeName());
				
				if(nNode.getNodeType() == Node.ELEMENT_NODE){
					 Element eElement  = (Element) nNode;
					 if(eElement.getAttribute("source_reference") != ""){
						 //System.out.println("source:" + eElement.getAttribute("source_reference"));
						 String namefile_copied = eElement.getAttribute("source_reference");
						 if (listFilesCopied.contains(new_path+namefile_copied)) {
							    //System.out.println("Account found");
							} else {
							    //System.out.println("Account not found");
							    listFilesCopied.add(new_path+namefile_copied);
							}
						 
					 }
				}
			}
			
			//System.out.println("Numero ficheros copiados: "+listFilesCopied.size());
			//System.out.println("Path: "+listFilesCopied);
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return listFilesCopied;
		
	}
	
	public static List<String> returnFilesSource(String path){
		
		List<String> listFileCopied = new ArrayList<String>();
		
		listFileCopied = read(path);
		
		return listFileCopied;
	}
	
	   private static String getFileExtension(File file) {
	         String fileName = file.getName();
	         if(fileName.lastIndexOf(".xml") != -1 && fileName.lastIndexOf(".xml") != 0)
	         return fileName.substring(fileName.lastIndexOf(".")+1);
	         else return "";
	     }

}
