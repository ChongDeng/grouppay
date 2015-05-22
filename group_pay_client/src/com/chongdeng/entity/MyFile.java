package com.chongdeng.entity;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;



public class MyFile {

	private String fileName = "test.txt";
	List list = new ArrayList();
	
	public void setFileName(String fileName)
	{		
		this.fileName = fileName;	
	}
	
	public String getFileName()
	{		
		return this.fileName;	
	}
		
	
	public List getContentList()
	{
		 list.clear();
		 		 
	     
	     try {
	    	 	File  f = new File(fileName);
	    	 	BufferedReader Reader = null;
	    	 	Reader = new BufferedReader(new FileReader(f));
	    	 	String TempStr= null;
	    	 	TempStr= Reader.readLine();	
	    	 	while (null!=TempStr)
	    	 	{
	    	 		list.add(TempStr);	
	    	 		TempStr= Reader.readLine();
	    	 	}
		     } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }	 	   
	     
	     return list;
     }	
	
	public void writeFileData(String message){
		
		try {
			
			 File outputfile = new File(fileName);
		     if(outputfile.exists()){
		      //System.out.print("文件存在");
		    	 ;
		     }else{
		     // System.out.print("文件不存在");
		      outputfile.createNewFile();//不存在则创建
		     }
		     BufferedWriter output = new BufferedWriter(new FileWriter(outputfile));		     
		     output.write(message);
		     output.close();			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }	 	


    }
}
