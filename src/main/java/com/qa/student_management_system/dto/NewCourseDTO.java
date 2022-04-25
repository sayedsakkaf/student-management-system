package com.qa.student_management_system.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewCourseDTO {
	@NotNull
	@NotBlank
	@Size(min = 3, max = 32, message = "Title must have atleast 3 characters, but less than 32 characters")
	private String courseTitle;
	
	@NotNull
	@NotBlank
	private String courseDescription;
	
	public NewCourseDTO() {
		super();
	}
	
	public NewCourseDTO (String courseTitle, String courseDescription) {
		super();
		this.courseTitle = courseTitle;
		this.courseDescription = courseDescription;
	}
	

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	@Override
	public int hashCode() {
		return Objects.hash(courseDescription, courseTitle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewCourseDTO other = (NewCourseDTO) obj;
		return Objects.equals(courseDescription, other.courseDescription)
				&& Objects.equals(courseTitle, other.courseTitle);
	}

	
	
}
