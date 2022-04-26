package com.qa.student_management_system.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.qa.student_management_system.domain.Course;
import com.qa.student_management_system.domain.Student;
import com.qa.student_management_system.repository.CourseRepository;
import com.qa.student_management_system.repository.StudentRepository;

@Profile("dev")
@Configuration
public class DevStartupListener implements ApplicationListener<ApplicationReadyEvent> {
	
	private StudentRepository studentRepository;
	private CourseRepository courseRepository;
	
	@Autowired
	public DevStartupListener (StudentRepository studentRepository, CourseRepository courseRepository) {
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		List<Student> students = studentRepository.saveAll(List.of(
				new Student("John", "Doe"), new Student ("Jane", "Smith")));
		
		Student student = students.stream().filter(m -> m.getFirstName().equals("John")).findFirst().orElse(null);
		
		
		List<Course> courses = courseRepository.saveAll(List.of(
				new Course("Information Technology", "Course designed to give an understanding of the computer technology needs of people"),
				new Course("History", "The study of the past - the lasts 6000 years, approximately, from the fist evidence of human writing "
						)));
		
	}
}
