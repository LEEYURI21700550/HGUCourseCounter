package edu.handong.analysis.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;





public class Utils {

	public static CSVParser getLines(String file,boolean removeHeader) throws IOException
	{


		try {
			if(removeHeader) {
			Reader reader = new BufferedReader(new FileReader(file));
			CSVParser csvParser = new CSVParser(reader,
					CSVFormat.DEFAULT
					.withFirstRecordAsHeader()
					.withIgnoreHeaderCase()
					.withTrim());
			return csvParser;
			}
		} catch(FileNotFoundException e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		}




		return null;


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
