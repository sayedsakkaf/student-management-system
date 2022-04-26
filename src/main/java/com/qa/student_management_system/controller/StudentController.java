package com.qa.student_management_system.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.qa.student_management_system.dto.NewStudentDTO;
import com.qa.student_management_system.dto.StudentDTO;
import com.qa.student_management_system.service.StudentService;




@RestController
@RequestMapping(path = "/student") 
@CrossOrigin("*")
public class StudentController {
	
	private StudentService studentService;
	
	@Autowired 
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	@GetMapping
	public ResponseEntity<List<StudentDTO>> getStudents() {
		return ResponseEntity.ok(studentService.getStudents());
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<StudentDTO> getStudent(@PathVariable(name = "id") int id) {
		StudentDTO student = studentService.getStudent(id);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}
		
	@PostMapping
	public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody NewStudentDTO student) {
		StudentDTO newStudent = studentService.createStudent(student);		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "http://localhost:8080/user/" + newStudent.getId());
		return new ResponseEntity<>(newStudent, headers, HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/{id}") 
	public ResponseEntity<StudentDTO> updateStudent(@RequestBody NewStudentDTO newStudentDTO,
			@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(studentService.updateStudent(newStudentDTO, id));
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable(name = "id") int id) {
		StudentDTO deletedStudent = studentService.getStudent(id);
		studentService.deleteStudent(id);
		return ResponseEntity.ok(deletedStudent);
	}
}