package com.qa.student_management_system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.qa.student_management_system.domain.Student;
import com.qa.student_management_system.dto.NewStudentDTO;
import com.qa.student_management_system.dto.StudentDTO;
import com.qa.student_management_system.repository.StudentRepository;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:student-data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles({"test"})
public class StudentServiceIntegrationTest {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private List<Student> savedStudents;
	private List<StudentDTO> savedStudentDTOs = new ArrayList<>();
	private int nextId;
	
	@BeforeEach
	public void init() {
		savedStudents = studentRepository.findAll();
		savedStudents.forEach(student -> savedStudentDTOs.add(modelMapper.map(student, StudentDTO.class)));
		nextId = savedStudents.get(savedStudents.size() - 1).getId() + 1;
	}
	
	@Test
	public void getAllStudentsTest() {
		assertEquals(savedStudentDTOs, studentService.getStudents());
	}
	
	@Test
	public void getStudentByIdTest() {
		StudentDTO expected = savedStudentDTOs.get(0);
		StudentDTO actual = studentService.getStudent(expected.getId());
		assertEquals(expected, actual);
	}
	
	@Test
	public void createStudentTest() {
		NewStudentDTO student = new NewStudentDTO();
		student.setFirstName("Sayed");
		student.setSurname("Sakkaf");		
		StudentDTO expected = new StudentDTO(nextId, student.getFirstName(), student.getSurname());
		StudentDTO actual = studentService.createStudent(student);
		assertEquals(expected, actual);
	}
	
	@Test
	public void updateStudentTest() {
		int id = savedStudents.get(0).getId();
		NewStudentDTO student = new NewStudentDTO();
		student.setFirstName("Sayed");
		student.setSurname("Sakkaf");
		StudentDTO expected = new StudentDTO(id, student.getFirstName(), student.getSurname());
		StudentDTO actual = studentService.updateStudent(student, id);
		assertEquals(expected, actual);
	}
	
	@Test
	public void deleteUserTest() {
		int id = savedStudents.get(0).getId();
		studentService.deleteStudent(id);
		assertEquals(Optional.empty(), studentRepository.findById(id));
	}

}
