package com.qa.student_management_system.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "student")
public class Student {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank (message = "Value cannot be null or whitespace")
	@Size(min = 2, max = 64, message = "Manufacturer character limit exceeded")
	private String firstName;
	
	@NotBlank (message = "Value cannot be null or whitespace")
	@Size(min = 2, max = 64, message = "Model character limit exceeded")
	private String surname;
	
	
	
	protected Student () {		
	}


	public Student(String firstName, String surname) {
		
		super();
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

		
	
}
