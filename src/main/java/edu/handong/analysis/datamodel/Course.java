package edu.handong.analysis.datamodel;

public class Course {
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;
	
	public Course(String[] line){
		studentId = (line[0].trim());
		yearMonthGraduated = line[1].trim();
		firstMajor = line[2].trim();
		secondMajor = line[3].trim();
		courseCode = line[4].trim();
		courseName = line[5].trim();
		courseCredit = line[6].trim();
		yearTaken = Integer.parseInt(line[7].trim());
		semesterCourseTaken = Integer.parseInt(line[8].trim());
	}
	
	public String getCourseCode()
	{
		return courseCode;
	}
	
	public String getcourseName()
	{
		return courseName;
	}
	
	public String getYearAndSemester()
	{
		return yearTaken + "-" + semesterCourseTaken;
	}
	
	public int getSemesterCourseTaken()
	{
		return semesterCourseTaken;
	}
	
	public int getYearTaken()
	{
		return yearTaken;
	}
	
	public String getStudentId()
	{
		return studentId;
	}
	
}
