package com.qa.student_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.student_management_system.domain.Student;
import com.qa.student_management_system.repository.StudentRepository;




@RestController
@RequestMapping("/student")
public class StudentController {
	
	private StudentRepository studentRepository;
	
	@Autowired
	public StudentController(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	@GetMapping
	public ResponseEntity<?> getStudents() {
		List<Student> students = List.of(new Student("John", "Doe"));
		ResponseEntity<?> res = new ResponseEntity<>(students, HttpStatus.OK);
		return res;
	}
}