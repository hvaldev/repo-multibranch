package com.example.springbootcrud.service;

import com.example.springbootcrud.clients.Redis;
import com.example.springbootcrud.model.Course;
import com.example.springbootcrud.model.Student;
import com.example.springbootcrud.repository.CourseRepository;
import com.example.springbootcrud.repository.StudentRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private CourseRepository courseRepository;

	@Override
	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}
	
	@Override
	public Student add(Student newStudent) {

		Student dbResponse = studentRepository.save(newStudent);

		String studentkey = "student-" + dbResponse.getId();
		this.updateStudentFromRedis(studentkey, newStudent);

		//fetch all courses and add them in cache

		return dbResponse;
	}
	
	@Override
	public String delete(Long id) {
		try {
			studentRepository.deleteById(id);

			String studentkey = "student-" + id;
			this.deleteStudentFromRedis(studentkey);

		}catch(Exception err) {
			return "Failed to delete Student with id" + id;
		}
		return "Deleted Student with id " + id;
	}
	
	
	
}
