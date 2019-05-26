package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	private String studentId;
	private ArrayList<Course> coursesTaken;
	private HashMap<String,Integer> semestersByYearAndSemester;
	
	public Student(String studentId)
	{
		this.studentId = studentId;
		coursesTaken = new ArrayList<Course>();
		semestersByYearAndSemester = new HashMap<String,Integer>();
	}
	
	public void addCourse(Course newRecord)
	{
		coursesTaken.add(newRecord);
	}
	
	public HashMap<String,Integer> getSemestersByYearAndSemester()
	{
		int i=1;
		for(Course course : coursesTaken)
		{
			String yearAndSemester = course.getYearTaken() + "-" + course.getSemesterCourseTaken();
			
			if(!semestersByYearAndSemester.containsKey(yearAndSemester))
			{
				semestersByYearAndSemester.put(yearAndSemester, i);
				i++;
			}
		}
		
		return semestersByYearAndSemester;
	}
	
	public int getNumCourseInNthSementer(int semester)
	{
		int count = 0;
		for(Course course : coursesTaken)
		{
			String yearAndsemester = course.getYearTaken() + "-" + course.getSemesterCourseTaken();
			if(semestersByYearAndSemester.get(yearAndsemester) == semester)
				count++;
		}

		return count;
	}
	
	public String getStudentId()
	{
		return studentId;
	}
	
	
}
