package com.qa.student_management_system.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class StudentDTO {
	
	private int id;
		
	@NotBlank (message = "Value cannot be null or whitespace")
	@Size(min = 2, max = 64, message = "Manufacturer character limit exceeded")
	private String firstName;
	
	@NotBlank (message = "Value cannot be null or whitespace")
	@Size(min = 2, max = 64, message = "Model character limit exceeded")
	private String surname;
	
	public StudentDTO() {
		super();
	}
	
	public StudentDTO(int id, String firstName, String surname) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.surname = surname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentDTO other = (StudentDTO) obj;
		return Objects.equals(firstName, other.firstName) && id == other.id && Objects.equals(surname, other.surname);
	}

	@Override
	public String toString() {
		return "StudentDTO [id=" + id + ", firstName=" + firstName + ", surname=" + surname + "]";
	}
	
	

}
