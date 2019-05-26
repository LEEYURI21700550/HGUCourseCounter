package edu.handong.analysis.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;



public class Utils {

	public static ArrayList<String> getLines(String file,boolean removeHeader)
	{
		ArrayList<String> lines = new ArrayList<String>();
		try {
				Scanner inputStream = new Scanner(new File(file));
				if(removeHeader) inputStream.nextLine();
				
				while(inputStream.hasNextLine())
				{
					lines.add(inputStream.nextLine());
				}
				inputStream.close();
										
		} catch(FileNotFoundException e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		}		
		return lines;
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
