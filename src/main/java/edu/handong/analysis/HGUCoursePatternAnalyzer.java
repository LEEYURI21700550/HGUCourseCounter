package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import java.io.File;
import java.io.IOException;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

public class HGUCoursePatternAnalyzer{
	private String inputPath, outputPath, courseCode;
	int analysis, startYear, endYear;
	boolean help;
	private HashMap<String,Student> students;

	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 * @throws IOException 
	 */
	public void run(String[] args) throws IOException {
		Options options = createOptions();

		if(parseOptions(options, args)) {
			if (help){
				printHelp(options);

				return;
			}


			ArrayList<String[]> lines = Utils.getLines(inputPath, true);
			students = loadStudentCourseRecords(lines);

			Map<String, Student> sortedStudents = new TreeMap<String,Student>(students);

			if(analysis == 1) {				
				ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);		
				Utils.writeAFile(linesToBeSaved, outputPath);
			}

			else {
				if(courseCode == null)
				{
					printHelp(options);
					return ;
				}


				ArrayList<String> linesToBeSaved = courseSearch(sortedStudents);
				Utils.writeAFile(linesToBeSaved, outputPath);
			}
		}
	}



	private ArrayList<String> courseSearch(Map<String, Student> sortedStudents) {
		// TODO Auto-generated method stub
		ArrayList<String> result = new ArrayList<String>();
		int totalStudents = 0;
		int studentsTaken = 0;
		String courseName = null;
		String line ;
		double rate= 0;

		result.add("Year,Semester,CouseCode, CourseName,TotalStudents,StudentsTaken,Rate");

		for(int year=startYear;year<=endYear;year++) {
			for(int semester=1;semester<=4;semester++) {
				String yearAndSemester = year+"-"+semester;

				for(String key : sortedStudents.keySet()){
					Student student = sortedStudents.get(key);

					if(student.studentsTakenCheck(courseCode, yearAndSemester)) {
						studentsTaken++; 

						courseName = student.getCourseName(courseCode);

					}

					if(student.totalStudentsCheck(yearAndSemester)) totalStudents++;


				}

				rate = (float)studentsTaken / (float)totalStudents * 100.0;;

				if(studentsTaken != 0)	{
					line = year + ","+ semester + "," + courseCode + "," + courseName+ "," +  totalStudents+ "," + studentsTaken + "," + String.format("%.1f", rate) + "%";
					result.add(line);
					studentsTaken=0;
					totalStudents = 0;
				}
			}
		}
		return result;
	}



	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);
			inputPath = cmd.getOptionValue("i");
			outputPath = cmd.getOptionValue("o");
			analysis = Integer.parseInt(cmd.getOptionValue("a"));
			courseCode = cmd.getOptionValue("c");
			startYear = Integer.parseInt(cmd.getOptionValue("s"));
			endYear = Integer.parseInt(cmd.getOptionValue("e"));
			help = cmd.hasOption("h");
		} catch (Exception e) {
			printHelp(options);
			return false;
		}
		return true;

	}
	
	private Options createOptions() {

		Options options = new Options();
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("Input path")
				.required()
				.build());

		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()
				.argName("Output path")
				.required()
				.build());

		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year")
				.hasArg() 
				.argName("Analysis option")
				.required()
				.build());

		options.addOption(Option.builder("c").longOpt("coursecode")
				.desc("Course code for '-a 2' option")
				.hasArg() 
				.argName("course code")
				.build());

		options.addOption(Option.builder("s").longOpt("startyear")
				.desc("Set the start year for analysis e.g., -s 2002")
				.hasArg() 
				.argName("Start year for analysis")
				.required()
				.build());

		options.addOption(Option.builder("e").longOpt("endyear")
				.desc("End year for analysis")
				.hasArg() 
				.argName("Set the end year for analysis e.g., -e 2005")
				.required()
				.build());

		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a Help page")
				.argName("Help")
				.build());

		return options;
	}


	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String[]> lines) {		
		// TODO: Implement this method
		HashMap<String,Student> studentData = new HashMap<String,Student>();

		for(String[] line : lines)
		{
			Course course = new Course(line);
			Student student = new Student(course.getStudentId());

			if(studentData.containsKey(student.getStudentId()))
				studentData.get(student.getStudentId()).addCourse(course);
			else
			{
				studentData.put(student.getStudentId(),student);
				studentData.get(student.getStudentId()).addCourse(course);
			}

		}
		return studentData; // do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
	 * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {

		//TODO: Implement this method
		ArrayList<String> lines = new ArrayList<String>();

		lines.add("StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester");

		for (String key : sortedStudents.keySet())
		{
			Student student = sortedStudents.get(key);

			for(int i=1;i<= student.getSemestersByYearAndSemester(startYear, endYear).size();i++) {
				String line = key + "," + student.getSemestersByYearAndSemester(startYear, endYear).size()
						+ "," + i + "," + student.getNumCourseInNthSementer(i, startYear, endYear); 

				lines.add(line);
			}
		}

		return lines; // do not forget to return a proper variable.
	}

	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer = "" ; // Leave this empty.;
		formatter.printHelp("HGUCourseCounter", header, options, footer, true);
	}

}
