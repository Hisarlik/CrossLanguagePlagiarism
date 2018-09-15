package com.menta.NLP.utils;


import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class ReadFiles {
	
	public static void main(String[] args) {
	      File folder = new File("data/corpus");
	      ReadFiles listFiles = new ReadFiles();
	      System.out.println("reading files before Java8 - Using listFiles() method");
	      listFiles.listAllFiles(folder);
	      System.out.println("-------------------------------------------------");
	      System.out.println(listFiles.listAllFiles(folder));
	      //listFiles.listAllFiles("G:\\Test");

	     }
	         
	     public List<File> listAllFiles(File folder){
	    	 List<File> filenames = new ArrayList<File>();
	         //System.out.println("In listAllfiles(File) method");
	         File[] fileNames = folder.listFiles();
	         for(File file : fileNames){
	             // if directory call the same method again
	             if(file.isDirectory()){
	                 listAllFiles(file);
	             }else{
	            	 String name = getFileExtension(file);
	                 if(name !=""){
	                	 filenames.add(file);
	                 }
	                 
	        
	             }
	         }
	         return filenames;
	     }
	     
	     
	     
	     public List<File> listAllFilesSerialized(File folder){
	    	 List<File> filenames = new ArrayList<File>();
	         //System.out.println("In listAllfiles(File) method");
	         File[] fileNames = folder.listFiles();
	         for(File file : fileNames){
	             // if directory call the same method again
	             if(file.isDirectory()){
	                 listAllFiles(file);
	             }else{
	            	 String name = getFilesExtensionSerialized(file);
	                 if(name !=""){
	                	 filenames.add(file);
	                 }
	                 
	        
	             }
	         }
	         return filenames;
	     }

	     
	     
	     private static String getFileExtension(File file) {
	         String fileName = file.getName();
	         if(fileName.lastIndexOf(".txt") != -1 && fileName.lastIndexOf(".txt") != 0)
	         return fileName.substring(fileName.lastIndexOf(".")+1);
	         else return "";
	     }
	     
	     private static String getFilesExtensionSerialized(File file) {
	         String fileName = file.getName();
	         if(fileName.lastIndexOf(".ser") != -1 && fileName.lastIndexOf(".ser") != 0)
	         return fileName.substring(fileName.lastIndexOf(".")+1);
	         else return "";
	     }
	     
	     
	     

}
