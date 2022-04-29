package com.qa.student_management_system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.qa.student_management_system.domain.Course;
import com.qa.student_management_system.dto.CourseDTO;
import com.qa.student_management_system.dto.NewCourseDTO;
import com.qa.student_management_system.repository.CourseRepository;

@ExtendWith(MockitoExtension.class)
public class CourseServiceUnitTest {
	
	@Mock
	private CourseRepository courseRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@InjectMocks
	private CourseService courseService;
	
	private List<Course> courses;
	private List<CourseDTO> courseDTOs;
	
	@BeforeEach
	public void init() {
				
		courses = List.of(new Course(1, "Test Subject 1", "Test Subject Description"), 
				new Course(2, "Test Subject 2", "Test Subject 2 Description"));
		courseDTOs = List.of(new CourseDTO(1,"Test Subject 1", "Test Subject Description"),
				new CourseDTO(2,"Test Subject 2", "Test Subject 2 Description"));
		
	
	}
	
	@Test
	public void getAllCoursesTest() {
		when(courseRepository.findAll()).thenReturn(courses);
		when(modelMapper.map(courses.get(0), CourseDTO.class)).thenReturn(courseDTOs.get(0));
		when(modelMapper.map(courses.get(1), CourseDTO.class)).thenReturn(courseDTOs.get(1));
		List<CourseDTO> actual = courseService.getCourses();
		assertEquals(courseDTOs, actual);
		verify(courseRepository).findAll();
		verify(modelMapper).map(courses.get(0), CourseDTO.class);
		verify(modelMapper).map(courses.get(1), CourseDTO.class);
		
	}
	
	@Test
	public void getCoursesByIdTest() {
		Course course = courses.get(0);
		CourseDTO courseDTO = courseDTOs.get(0);
		int id = course.getId();
		when(courseRepository.findById(id)).thenReturn(Optional.of(course));
		when(modelMapper.map(course, CourseDTO.class)).thenReturn(courseDTO);
		CourseDTO actual = courseService.getCourse(id);
		assertEquals(courseDTO, actual);
		verify(courseRepository).findById(id);
		verify(modelMapper).map(course, CourseDTO.class);
	}
	
	@Test
	public void getByInvalidIdTest() {
		int id = 100;
		when(courseRepository.findById(id)).thenReturn(Optional.empty());
		EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
			courseService.getCourse(id);
		});
		String expectedMessage = "Course not found with id " + id;
		assertEquals(expectedMessage, exception.getMessage());
		verify(courseRepository).findById(id);
		
	}
	
	@Test
	public void createCourseTest() {
		Course course = courses.get(0);
		Course newCourse = new Course(course.getCourseTitle(), course.getCourseDescription());
		CourseDTO expected = courseDTOs.get(0);
		
		NewCourseDTO courseDTO = new NewCourseDTO(course.getCourseTitle(), course.getCourseDescription());
		CourseDTO createdCourseDTO = new CourseDTO(course.getId(), course.getCourseTitle(), course.getCourseDescription());
		
		when(modelMapper.map(courseDTO, Course.class)).thenReturn(newCourse);
		when(courseRepository.save(newCourse)).thenReturn(course);
		when(modelMapper.map(course, CourseDTO.class)).thenReturn(createdCourseDTO);
		
		CourseDTO actual = courseService.createCourse(courseDTO);
		
		assertEquals(expected, actual);
		verify(modelMapper).map(courseDTO, Course.class);
		verify(courseRepository).save(newCourse);
		verify(modelMapper).map(course, CourseDTO.class);
	
	}
	

}
