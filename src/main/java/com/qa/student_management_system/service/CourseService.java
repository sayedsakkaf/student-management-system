package com.qa.student_management_system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.student_management_system.domain.Course;
import com.qa.student_management_system.dto.CourseDTO;
import com.qa.student_management_system.dto.NewCourseDTO;
import com.qa.student_management_system.repository.CourseRepository;

@Service
public class CourseService {
	
	private CourseRepository courseRepository;
	private ModelMapper modelMapper;
	
	@Autowired
	public CourseService(CourseRepository courseRepository, ModelMapper modelMapper) {
		super();
		this.courseRepository = courseRepository;
		this.modelMapper = modelMapper;

	}
	
	public List<CourseDTO> getCourses() {
		List<Course> courses = courseRepository.findAll();
		List<CourseDTO> DTOs = new ArrayList<>();
		
		for (Course course : courses) {
			DTOs.add(this.toDTO(course));
		}
		return DTOs;
	}
	
	
	public CourseDTO getCourse(int id) {
		Optional<Course> course = courseRepository.findById(id);
		
		if (course.isPresent()) {
			return this.toDTO(course.get());
		}
		throw new EntityNotFoundException("Course not found with id "+ id);
		
		
	}
	
	
	public CourseDTO createCourse(NewCourseDTO course) {
		Course toSave = this.modelMapper.map(course, Course.class);
		Course newCourse = courseRepository.save(toSave);
		return this.toDTO(newCourse);
	}
	
	@Transactional
	public CourseDTO updateCourse(NewCourseDTO course, int id) {
		if (courseRepository.existsById(id)) {
			Course savedCourse = courseRepository.getById(id);
			savedCourse.setCourseTitle(course.getCourseTitle());
			savedCourse.setCourseDescription(course.getCourseDescription());
			return this.toDTO(savedCourse);
		}
		throw new EntityNotFoundException("Course not found with id " + id);
	}
	
	
	public void deleteCourse(int id) {
		if (courseRepository.existsById(id)) {
			courseRepository.deleteById(id);
			return;
		}
		throw new EntityNotFoundException("Course not found with id " + id);
	}
	
	private CourseDTO toDTO (Course course) {
		return this.modelMapper.map(course, CourseDTO.class);
	}

}