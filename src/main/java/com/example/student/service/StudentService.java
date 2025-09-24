package com.example.student.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.student.models.Student;
import com.example.student.models.StudentDto;
public interface StudentService {
	
	
	Student getStudentById(long id);
	Student saveStudent(StudentDto studentDto, MultipartFile imageFile ) throws Exception;
	Student updateStudent(long id, StudentDto studentDto,MultipartFile imageFile) throws Exception;
	void deleteStudent(long id)throws Exception;
	
	Page<Student> getAllStudents(Pageable pageable);
	void deleteProduct(long id) throws Exception;
}
