package com.qa.student_management_system.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CourseDTO {
	
	private int id;
	
	@NotNull
	@NotBlank
	@Size(min = 3, max = 32, message = "Title must have atleast 3 characters, but less than 32 characters")
	private String courseTitle;
	
	@NotNull
	@NotBlank
	private String courseDescription;
	
	public CourseDTO() {
		super();
	}
	
	public CourseDTO (String courseTitle, String courseDescription) {
		super();
		this.courseTitle = courseTitle;
		this.courseDescription = courseDescription;
	}
	
	public CourseDTO (int id, String courseTitle, String courseDescription) {
		super();
		this.id = id;
		this.courseTitle = courseTitle;
		this.courseDescription = courseDescription;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return Objects.hash(courseDescription, courseTitle, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseDTO other = (CourseDTO) obj;
		return Objects.equals(courseDescription, other.courseDescription)
				&& Objects.equals(courseTitle, other.courseTitle) && id == other.id;
	}

	@Override
	public String toString() {
		return "CourseDTO [id=" + id + ", courseTitle=" + courseTitle + ", courseDescription=" + courseDescription
				+ "]";
	}


}
