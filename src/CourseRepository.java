package com.example.springbootcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootcrud.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
