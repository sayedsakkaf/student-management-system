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

import com.qa.student_management_system.domain.Course;
import com.qa.student_management_system.dto.CourseDTO;
import com.qa.student_management_system.dto.NewCourseDTO;
import com.qa.student_management_system.repository.CourseRepository;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles({"test"})
public class CourseServiceIntegrationTest {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private List<Course> savedCourses;
	private List<CourseDTO> savedCourseDTOs = new ArrayList<>();
	private int nextId;
	
	@BeforeEach
	public void init() {
		savedCourses = courseRepository.findAll();
		savedCourses.forEach(course -> savedCourseDTOs.add(modelMapper.map(course, CourseDTO.class)));
		nextId = savedCourses.get(savedCourses.size() - 1).getId() + 1;
	}
	
	@Test
	public void getAllCoursesTest() {
		assertEquals(savedCourseDTOs, courseService.getCourses());
	}
	
	@Test
	public void getCourseByIdTest() {
		CourseDTO expected = savedCourseDTOs.get(0);
		CourseDTO actual = courseService.getCourse(expected.getId());
		assertEquals(expected, actual);
	}
	
	@Test
	public void createCourseTest() {
		NewCourseDTO course = new NewCourseDTO();
		course.setCourseTitle("Maths");
		course.setCourseDescription("Study of numbers, shapes and patterns");
		CourseDTO expected = new CourseDTO(nextId, course.getCourseTitle(), course.getCourseDescription());
		CourseDTO actual = courseService.createCourse(course);
		assertEquals(expected, actual);
	}
	
	@Test
	public void updateCourseTest() {
		int id = savedCourses.get(0).getId();
		NewCourseDTO course = new NewCourseDTO();
		course.setCourseTitle("Maths");
		course.setCourseDescription("Study of numbers, shapes and patterns");
		CourseDTO expected = new CourseDTO(id, course.getCourseTitle(), course.getCourseDescription());
		CourseDTO actual = courseService.updateCourse(course, id);
		assertEquals(expected, actual);
	}
	
	@Test
	public void deleteUserTest() {
		int id = savedCourses.get(0).getId();
		courseService.deleteCourse(id);
		assertEquals(Optional.empty(), courseRepository.findById(id));
	}
	
	

}
