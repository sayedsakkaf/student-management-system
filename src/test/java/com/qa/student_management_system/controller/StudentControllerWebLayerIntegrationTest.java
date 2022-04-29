package com.qa.student_management_system.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.qa.student_management_system.dto.NewStudentDTO;
import com.qa.student_management_system.dto.StudentDTO;
import com.qa.student_management_system.service.StudentService;

@WebMvcTest(StudentController.class)
@ActiveProfiles({"test"})
public class StudentControllerWebLayerIntegrationTest {
	
	@Autowired
	private StudentController studentController;
	
	@MockBean
	private StudentService studentService;
	
	private List<StudentDTO> studentDTOs;
	
	@BeforeEach
	public void init() {
		studentDTOs = List.of(new StudentDTO(1, "James", "Brown"),
				new StudentDTO(2, "John", "Tanner"));
				
	}
	
	@Test
	public void getAllStudentsTest() {
		ResponseEntity<?> expected = ResponseEntity.ok(studentDTOs);
		when(studentService.getStudents()).thenReturn(studentDTOs);		
		ResponseEntity<?> actual = studentController.getStudents();
		assertEquals(expected, actual);
		verify(studentService).getStudents();
	}
	

	@Test
	public void createStudentTest() {
		NewStudentDTO newStudent = new NewStudentDTO();
		newStudent.setFirstName("Matthew");
		newStudent.setSurname("Stevens");
		
		StudentDTO expectedStudent = new StudentDTO(1, newStudent.getFirstName(), newStudent.getSurname());
		ResponseEntity<?> expected = ResponseEntity.created(URI.create("http://localhost:8080/user/" + expectedStudent.getId()))
								.body(expectedStudent);
		
		when(studentService.createStudent(newStudent)).thenReturn(expectedStudent);
		
		ResponseEntity<?> actual = studentController.createStudent(newStudent);
		assertEquals(expected, actual);
		verify(studentService).createStudent(newStudent);
	}

}
