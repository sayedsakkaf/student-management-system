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

import com.qa.student_management_system.dto.CourseDTO;
import com.qa.student_management_system.dto.NewCourseDTO;

import com.qa.student_management_system.service.CourseService;

@WebMvcTest(CourseController.class)
@ActiveProfiles({"test"})
public class CourseControllerWebLayerIntegrationTest {
	
	@Autowired
	private CourseController courseController;
	
	@MockBean
	private CourseService courseService;
	
	private List<CourseDTO> courseDTOs;
	
	@BeforeEach
	public void init() {
		courseDTOs = List.of(new CourseDTO(1,"Test Subject 1", "Test Subject Description"),
				new CourseDTO(2,"Test Subject 2", "Test Subject 2 Description"));
		
	
	}
	@Test
	public void getAllCoursesTest() {
		ResponseEntity<?> expected = ResponseEntity.ok(courseDTOs);
		when(courseService.getCourses()).thenReturn(courseDTOs);		
		ResponseEntity<?> actual = courseController.getCourses();
		assertEquals(expected, actual);
		verify(courseService).getCourses();
	}
	

	@Test
	public void createCourseTest() {
		NewCourseDTO newCourse = new NewCourseDTO();
		newCourse.setCourseTitle("Test Subject");
		newCourse.setCourseDescription("Test Subject Description");
		
		CourseDTO expectedCourse = new CourseDTO(1, newCourse.getCourseTitle(), newCourse.getCourseDescription());
		ResponseEntity<?> expected = ResponseEntity.created(URI.create("http://localhost:8080/course/" + expectedCourse.getId()))
								.body(expectedCourse);
		
		when(courseService.createCourse(newCourse)).thenReturn(expectedCourse);
		
		ResponseEntity<?> actual = courseController.createCourse(newCourse);
		assertEquals(expected, actual);
		verify(courseService).createCourse(newCourse);
	}
	

}

