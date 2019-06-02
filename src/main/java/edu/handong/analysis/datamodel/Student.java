package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
	
	public String getCourseName(String courseCode) 
	{
		String courseName = null;
		
		for(Course course : coursesTaken)
		{
			if(course.getCourseCode().equals(courseCode))
				courseName = course.getcourseName();
		}
		
		return courseName;
	}
	
	
	public Map<String,Integer> getSemestersByYearAndSemester(int start, int end)
	{
		int i=1;
		for(Course course : coursesTaken)
		{	
			
			String yearAndSemester = course.getYearTaken() + "-" + course.getSemesterCourseTaken();
			if(course.getYearTaken() >= start && course.getYearTaken() <= end) {
			
				
				if(!semestersByYearAndSemester.containsKey(yearAndSemester))
				{
				
					semestersByYearAndSemester.put(yearAndSemester, i);
					i++;
				}
				
			}
			
		}
		
		Map<String, Integer> sortedHash = new TreeMap<String,Integer>(semestersByYearAndSemester);
		return sortedHash;
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
	
	
	public int getNumCourseInNthSementer( )
	{
		int count = 0;
	
		for(String keys :semestersByYearAndSemester.keySet())
		{	

			for(Course course : coursesTaken) {
				String yearAndsemester = course.getYearTaken() + "-" + course.getSemesterCourseTaken();
				if(keys.equals(yearAndsemester)) {
					//System.out.println(key + " " + yearAndsemester +" " +count);
					count++;
					
				}
			
			
			}
			return count;
		}
		return 0;
	}
	
	
	public int getNumCourseInNthSementer(int semester, int start, int end)
	{
		int count = 0;
		for(Course course : coursesTaken)
		{
			if(course.getYearTaken() >= start && course.getYearTaken()<= end)
			{
				String yearAndsemester = course.getYearTaken() + "-" + course.getSemesterCourseTaken();
				if(semestersByYearAndSemester.get(yearAndsemester) == semester)
					count++;
			}

			
		}

		return count;
	}
	
	
	public String getStudentId()
	{
		return studentId;
	}

	
	
	public boolean studentsTakenCheck(String courseCode, String yearAndSemester) {
		
		for(Course course : coursesTaken) {
			if(course.getCourseCode().equals(courseCode)) {
				if(course.getYearAndSemester().equals(yearAndSemester)) return true;
			}
			
		}
		
		return false;
	}

	public boolean totalStudentsCheck(String yearAndSemester) {
		
		for(Course course : coursesTaken) {
			if(course.getYearAndSemester().equals(yearAndSemester) ) return true;
		}
		
		return false;
	}


	
	
	//Student Class.
}
