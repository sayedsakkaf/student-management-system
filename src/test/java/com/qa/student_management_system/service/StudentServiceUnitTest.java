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

import com.qa.student_management_system.domain.Student;
import com.qa.student_management_system.dto.NewStudentDTO;
import com.qa.student_management_system.dto.StudentDTO;
import com.qa.student_management_system.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
public class StudentServiceUnitTest {
	
	@Mock
	private StudentRepository studentRepository;	
	
	@Mock
	private ModelMapper modelMapper;
	
	@InjectMocks
	private StudentService studentService;
	
	private List<Student> students;
	private List<StudentDTO> studentDTOs;
	
	@BeforeEach
	public void init() {
		students = List.of(new Student(1, "James", "Brown"), 
				new Student(2, "John", "Tanner"));
		studentDTOs = List.of(new StudentDTO(1, "James", "Brown"),
				new StudentDTO(2, "John", "Tanner"));
				
	}
	
	@Test
	public void getAllTest() {
		when(studentRepository.findAll()).thenReturn(students);
		when(modelMapper.map(students.get(0), StudentDTO.class)).thenReturn(studentDTOs.get(0));
		when(modelMapper.map(students.get(1), StudentDTO.class)).thenReturn(studentDTOs.get(1));	
		List<StudentDTO> actual = studentService.getStudents();
		assertEquals(studentDTOs, actual);
		verify(studentRepository).findAll();
		verify(modelMapper).map(students.get(0), StudentDTO.class);
		verify(modelMapper).map(students.get(1), StudentDTO.class);
	}
	
	@Test
	public void getByIdTest() {
		Student student = students.get(0);
		StudentDTO studentDTO = studentDTOs.get(0);
		int id = student.getId();
		when(studentRepository.findById(id)).thenReturn(Optional.of(student));
		when(modelMapper.map(student, StudentDTO.class)).thenReturn(studentDTO);
		StudentDTO actual = studentService.getStudent(id);
		assertEquals(studentDTO, actual);
		verify(studentRepository).findById(id);
		verify(modelMapper).map(student, StudentDTO.class);
	}
		
	@Test
	public void getByInvalidIdTest() {
		int id = 999;
		when(studentRepository.findById(id)).thenReturn(Optional.empty());
		EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
			studentService.getStudent(id);
		});
		String expectedMessage = "Student not found with id "+ id;
		assertEquals(expectedMessage, exception.getMessage());
		verify(studentRepository). findById(id);
		
	}
	
	@Test
	public void createTest() {
		Student student = students.get(0);
		NewStudentDTO studentDTO = new NewStudentDTO();
		studentDTO.setFirstName(student.getFirstName());
		studentDTO.setSurname(student.getSurname());
		StudentDTO newStudent = new StudentDTO(student.getId(), student.getFirstName(), student.getSurname());
		when(modelMapper.map(studentDTO, Student.class)).thenReturn(student);
		when(studentRepository.save(student)).thenReturn(student);
		when(modelMapper.map(student, StudentDTO.class)).thenReturn(newStudent);
		
		StudentDTO actual = studentService.createStudent(studentDTO);
		assertEquals(newStudent, actual);
		verify(modelMapper).map(studentDTO, Student.class);
		verify(studentRepository).save(student);	
		verify(modelMapper).map(student, StudentDTO.class);
		
		}
	@Test
	public void updateTest() {
		Student student = students.get(1);
		int id = student.getId();
		NewStudentDTO newStudentDTO = new NewStudentDTO(student.getFirstName(), student.getSurname());
		StudentDTO expected = new StudentDTO(student.getId(), student.getFirstName(), student.getSurname());
		when(studentRepository.existsById(id)).thenReturn(true);
		when(studentRepository.getById(id)).thenReturn(student);
		when(modelMapper.map(student, StudentDTO.class)).thenReturn(expected);
		
		StudentDTO actual = studentService.updateStudent(newStudentDTO, id);
		
		assertEquals(expected, actual);
		verify(studentRepository).existsById(id);
		verify(studentRepository).getById(id);
		verify(modelMapper).map(student, StudentDTO.class);

	}
}
