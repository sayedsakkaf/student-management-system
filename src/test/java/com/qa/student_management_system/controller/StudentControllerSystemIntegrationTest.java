package com.qa.student_management_system.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.student_management_system.domain.Student;
import com.qa.student_management_system.dto.NewStudentDTO;
import com.qa.student_management_system.dto.StudentDTO;
import com.qa.student_management_system.repository.StudentRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"}, 
executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class StudentControllerSystemIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private StudentRepository studentRepository;
	
	private List<Student> savedStudents;
	private List<StudentDTO> savedStudentDTOs = new ArrayList<>();
	private int nextId;
	private String uri;
	
	@BeforeEach
	public void init() {
		savedStudents = studentRepository.findAll();
		savedStudents.forEach(student -> savedStudentDTOs.add(modelMapper.map(student, StudentDTO.class)));
		nextId = savedStudents.get(savedStudents.size() - 1).getId() + 1;
		uri = "/student";
	}
	
	@Test
	public void getAllStudentsTest() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.request(HttpMethod.GET, uri);
		
		request.accept(MediaType.APPLICATION_JSON);
		String students = objectMapper.writeValueAsString(savedStudentDTOs);
		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(students);
		mockMvc.perform(request)
			.andExpectAll(statusMatcher, contentMatcher);
	}
	
	
	@Test
	public void createStudentTest() throws Exception {
		NewStudentDTO newStudent = new NewStudentDTO();
		newStudent.setFirstName("Matthew");
		newStudent.setSurname("Stevens");
		StudentDTO expectedStudent = new StudentDTO(nextId, newStudent.getFirstName(), newStudent.getSurname());
		
		var request =  MockMvcRequestBuilders.request(HttpMethod.POST, uri);
		
		request.accept(MediaType.APPLICATION_JSON);
		
		request.content(objectMapper.writeValueAsString(newStudent));
		request.contentType(MediaType.APPLICATION_JSON);
		
		String expectedBody = objectMapper.writeValueAsString(expectedStudent);
		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isCreated();
		ResultMatcher locationMatcher = MockMvcResultMatchers.header().string("Location", "http://localhost:8080/user/" + expectedStudent.getId());
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(expectedBody);
		
		mockMvc.perform(request)
			.andExpectAll(statusMatcher, locationMatcher, contentMatcher);
	}

}
