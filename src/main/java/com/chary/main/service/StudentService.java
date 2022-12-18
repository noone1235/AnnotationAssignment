package com.chary.main.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.chary.main.model.Student;
import com.chary.main.repository.StudentRepo;

public class StudentService {
	
	StudentRepo studentRepo=new StudentRepo();
	
	public Connection getConnection() throws SQLException{
		return studentRepo.establishConnection();
	}
	
	public List<Student> getAllStudents() throws SQLException, IllegalArgumentException, IllegalAccessException {
		return studentRepo.getJsonFromDb();
	}
	
	public List<Student> getStudentByName(String name) throws SQLException, IllegalArgumentException, IllegalAccessException{
		return studentRepo.getJsonFromDbByName(name);
	} 
	
	public List<Integer> postObjectsToDb(List<Student> _students) throws IllegalArgumentException, IllegalAccessException, SQLException{
		return studentRepo.sendObjectToDb(_students);
	}
	
	
}
