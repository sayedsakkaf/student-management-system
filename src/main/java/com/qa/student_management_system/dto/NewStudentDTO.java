package com.qa.student_management_system.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewStudentDTO {

	
	@NotBlank (message = "Value cannot be null or whitespace")
	@Size(min = 2, max = 64, message = "Manufacturer character limit exceeded")
	private String firstName;
	
	@NotBlank (message = "Value cannot be null or whitespace")
	@Size(min = 2, max = 64, message = "Model character limit exceeded")
	private String surname;
	
	public NewStudentDTO() {
		super();
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
		return Objects.hash(firstName, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewStudentDTO other = (NewStudentDTO) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(surname, other.surname);
	}
}
	