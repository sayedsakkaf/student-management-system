package com.qa.student_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.student_management_system.domain.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

}
