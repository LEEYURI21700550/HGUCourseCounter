package edu.handong.analysis.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import au.com.bytecode.opencsv.CSVReader;



public class Utils {

	public static ArrayList<String[]> getLines(String file,boolean removeHeader) throws IOException
	{
		ArrayList<String[]> csv = new ArrayList<String[]>();

		try {
			CSVReader reader = new CSVReader(new FileReader(file));
			String [] line;
			
			if(removeHeader) reader.readNext();
			
			while ((line = reader.readNext()) != null) {
				csv.add(line);
			}

		} catch(FileNotFoundException e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		}
	
		

		return csv;


	}
	
	
	
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName)
	{
		
		PrintWriter outputStream= null;
		File file = new File(targetFileName);
		File AFile = file.getAbsoluteFile();
		try {
			if(!file.exists()) {
				AFile.getParentFile().mkdirs();
			}
			
		outputStream = new PrintWriter(targetFileName);	
			
		} catch (FileNotFoundException e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		}
		
		for(String line : lines)
		{
			outputStream.println(line);
		}
			
		
		outputStream.close();
		
		
		
	}
}
