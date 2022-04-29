package com.qa.student_management_system.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.student_management_system.domain.Course;
import com.qa.student_management_system.dto.CourseDTO;
import com.qa.student_management_system.dto.NewCourseDTO;
import com.qa.student_management_system.repository.CourseRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"}, 
executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class CourseControllerSystemIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CourseRepository courseRepository;
	
	private List<Course> savedCourses;
	private List<CourseDTO> savedCourseDTOs = new ArrayList<>();
	private int nextId;
	private String uri;
	
	@BeforeEach
	public void init() {
		savedCourses = courseRepository.findAll();
		savedCourses.forEach(course -> savedCourseDTOs.add(modelMapper.map(course, CourseDTO.class)));
		nextId = savedCourses.get(savedCourses.size() - 1).getId() + 1;
		uri = "/course";
		
	}
	
	@Test
	public void getAllCoursesTest() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.request(HttpMethod.GET, uri);
		
		request.accept(MediaType.APPLICATION_JSON);
		String courses = objectMapper.writeValueAsString(savedCourseDTOs);
		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(courses);
		mockMvc.perform(request)
			.andExpectAll(statusMatcher, contentMatcher);
	}
	
	@Test
	public void createCourseTest() throws Exception {
		NewCourseDTO newCourse = new NewCourseDTO();
		newCourse.setCourseTitle("Test Subject");
		newCourse.setCourseDescription("Test Subject Description");
		CourseDTO expectedCourse = new CourseDTO(nextId, newCourse.getCourseTitle(), newCourse.getCourseDescription());
		
		var request =  MockMvcRequestBuilders.request(HttpMethod.POST, uri);
		
		request.accept(MediaType.APPLICATION_JSON);
		
		request.content(objectMapper.writeValueAsString(newCourse));
		request.contentType(MediaType.APPLICATION_JSON);
		
		String expectedBody = objectMapper.writeValueAsString(newCourse);
		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isCreated();
		ResultMatcher locationMatcher = MockMvcResultMatchers.header().string("Location", "http://localhost:8080/course/" + expectedCourse.getId());
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(expectedBody);
		
		mockMvc.perform(request)
			.andExpectAll(statusMatcher, locationMatcher, contentMatcher);
	}
	
	@Test
	public void deleteCourseTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders
				.delete("/course/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
