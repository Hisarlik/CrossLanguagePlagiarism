package com.menta.main;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.menta.NLP.utils.CSVUtils;
import com.menta.NLP.utils.ReadXml;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CorpusRealPlagiarismCases {
	
	static String CSVFILE = "data/realCasesPruebaSuspect.csv";


	public static void main(String[] args) {
		
		
		
		try{
			File folder = new File("data/corpus/pruebaSuspect");
			
			listAllFiles(folder);
			
			

			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
	public static void listAllFiles(File folder){

		// Creación de fichero para guardar los casos de plagio

		try {
			FileWriter writer = new FileWriter(CorpusRealPlagiarismCases.CSVFILE);
		
		
		
        File[] fileNames = folder.listFiles();
        for(File file : fileNames){

            if(file.isDirectory()){
                listAllFiles(file);
            }else{
           
           	String name = getFileExtension(file);
           	
              if(name !=""){

               	 	String fileName = file.getName();
               	 	int iend = fileName.indexOf(".");
	               	String subName;

	               	subName= fileName.substring(0 , iend); //this will give abc

               	 	List<String> listPlag = ReadXml.listPlagiarismDocuments(file.getAbsolutePath());
               	 	listPlag.add(0,  subName+".txt");
               	
               	 	System.out.println(listPlag);
               	 	CSVUtils.writeLine(writer, listPlag, '\t');
               	 	
               	
               	
                }
                
       
            }
           
        }
		// se guardan todos los casos y se cierra el archivo
	    writer.flush();
	    writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        
    }
	  private static String getFileExtension(File file) {
	         String fileName = file.getName();
	         if(fileName.lastIndexOf(".xml") != -1 && fileName.lastIndexOf(".xml") != 0)
	         return fileName.substring(fileName.lastIndexOf(".")+1);
	         else return "";
	     }

}
