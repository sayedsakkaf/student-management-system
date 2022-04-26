package com.qa.student_management_system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.student_management_system.domain.Student;
import com.qa.student_management_system.dto.NewStudentDTO;
import com.qa.student_management_system.dto.StudentDTO;
import com.qa.student_management_system.repository.StudentRepository;

@Service
public class StudentService {
	
	private StudentRepository studentRepository;
	private ModelMapper modelMapper;
	
	@Autowired
	public StudentService(StudentRepository studentRepository, ModelMapper modelMapper) {
		super();
		this.studentRepository = studentRepository;
		this.modelMapper = modelMapper;

	}
	
	public List<StudentDTO> getStudents() {
		List<Student> students = studentRepository.findAll();
		List<StudentDTO> dtos = new ArrayList<>();
		
		for (Student student : students) {
			dtos.add(this.toDTO(student));
		}
		return dtos;
	}
	
	
	public StudentDTO getStudent(int id) {
		Optional<Student> student = studentRepository.findById(id);
		
		if (student.isPresent()) {
			return this.toDTO(student.get());
		}
		throw new EntityNotFoundException("Student not found with id "+ id);
		
		
	}
	
//	public List<Course> getStudentCourses (int studentId) {
//		Optional<Student> student = studentRepository.findById(id);
//		
//		if (student.isPresent()) {
//			return student.get().getCourses();
//		}
//		throw new EntityNotFoundException("Student not found with id "+ id);
//		
//	}
	
	public StudentDTO createStudent(NewStudentDTO student) {
		Student toSave = this.modelMapper.map(student, Student.class);
		Student newStudent = studentRepository.save(toSave);
		return this.toDTO(newStudent);
	}
	
	@Transactional
	public StudentDTO updateStudent(NewStudentDTO student, int id) {
		if (studentRepository.existsById(id)) {
			Student savedStudent = studentRepository.getById(id);
			savedStudent.setFirstName(student.getFirstName());
			savedStudent.setSurname(student.getSurname());
			return this.toDTO(savedStudent);
		}
		throw new EntityNotFoundException("Student not found with id " + id);
	}
	
	
	public void deleteStudent(int id) {
		if (studentRepository.existsById(id)) {
			studentRepository.deleteById(id);
			return;
		}
		throw new EntityNotFoundException("Student not found with id " + id);
	}
	
	private StudentDTO toDTO (Student student) {
		return this.modelMapper.map(student, StudentDTO.class);
	}

}
