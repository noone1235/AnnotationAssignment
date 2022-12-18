package com.chary.main.controller;

import java.sql.SQLException;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chary.main.model.Student;
import com.chary.main.service.StudentService;

@RestController
public class Controller {

	private final StudentService studentService = new StudentService();

	@GetMapping("/getStudents")
	public List<Student> getStudents() throws SQLException, IllegalArgumentException, IllegalAccessException {
		return studentService.getAllStudents();
	}

	@GetMapping("/getStudents/")
	public List<Student> getStudentByName(@RequestBody String name)
			throws SQLException, IllegalArgumentException, IllegalAccessException {
		return studentService.getStudentByName(name);
	}

	@PostMapping("/postStudents")
	public List<Integer> postStudents(@RequestBody List<Student> _students)
			throws IllegalArgumentException, IllegalAccessException, SQLException {
		return studentService.postObjectsToDb(_students);

	}
	
}
