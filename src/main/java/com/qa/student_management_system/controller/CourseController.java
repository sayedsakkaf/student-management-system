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

import com.qa.student_management_system.dto.CourseDTO;
import com.qa.student_management_system.dto.NewCourseDTO;
import com.qa.student_management_system.service.CourseService;

	@RestController
	@RequestMapping(path = "/course") 
	@CrossOrigin("*")
	public class CourseController {
		
		private CourseService courseService;
		
		@Autowired 
		public CourseController(CourseService courseService) {
			this.courseService = courseService;
		}
		
		@GetMapping
		public ResponseEntity<List<CourseDTO>> getCourses() {
			return ResponseEntity.ok(courseService.getCourses());
		}
		
		@GetMapping(path = "/{id}")
		public ResponseEntity<CourseDTO> getCourse(@PathVariable(name = "id") int id) {
			CourseDTO course = courseService.getCourse(id);
			return new ResponseEntity<>(course, HttpStatus.OK);
		}
			
		@PostMapping
		public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody NewCourseDTO course) {
			CourseDTO newCourse = courseService.createCourse(course);		
			HttpHeaders headers = new HttpHeaders();
			headers.add("Location", "http://localhost:8080/user/" + newCourse.getId());
			return new ResponseEntity<>(newCourse, headers, HttpStatus.CREATED);
		}
		
		@PutMapping(path = "/{id}") 
		public ResponseEntity<CourseDTO> updateCourse(@RequestBody NewCourseDTO newCourseDTO,
				@PathVariable(name = "id") int id) {
			return ResponseEntity.ok(courseService.updateCourse(newCourseDTO, id));
		}
		
		@DeleteMapping(path = "/{id}")
		public ResponseEntity<?> deleteCourse(@PathVariable(name = "id") int id) {
			CourseDTO deletedCourse = courseService.getCourse(id);
			courseService.deleteCourse(id);
			return ResponseEntity.ok(deletedCourse);
		}
	}

	


