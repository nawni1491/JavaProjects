package com.example.student.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.student.models.Student;
import com.example.student.models.StudentDto;
import com.example.student.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentRepository studentRepository;
	
	private final String uploadDir = "C:\\Users\\User\\eclipse-workspace\\StudentInfo\\public\\images";
	
	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository =studentRepository;
	}
	
	@Override
    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }


	@Override
	public Student getStudentById(long id) {
		return studentRepository.findById(id).orElseThrow(() ->new RuntimeException("Student not found"));
	}

	@Override
	public Student saveStudent(StudentDto studentDto, MultipartFile imageFile) throws Exception {
		String storageFileName = saveImage(imageFile);
		
		Student student = new Student();
		student.setName(studentDto.getName());
		student.setEmail(studentDto.getEmail());
		student.setPhone(studentDto.getPhone());
		student.setGender(studentDto.getGender());
		student.setAddress(studentDto.getAddress());
		
		
		return studentRepository.save(student);
	}

	@Override
	public Student updateStudent(long id, StudentDto studentDto, MultipartFile imageFile) throws Exception {
		Student existingStudent = getStudentById(id);
		
		if(imageFile != null && !imageFile.isEmpty()) {
			deleteImage(existingStudent.getImageFileName());
			String newFileName = saveImage(imageFile);
			existingStudent.setImageFileName(newFileName);
	}
		existingStudent.setName(studentDto.getName());
		existingStudent.setEmail(studentDto.getEmail());
		existingStudent.setPhone(studentDto.getPhone());
		existingStudent.setGender(studentDto.getGender());
		existingStudent.setAddress(studentDto.getAddress());
		
		return studentRepository.save(existingStudent);
	}
	private String saveImage(MultipartFile imageFile) throws Exception{
		String storageFileName= new Date().getTime() + "_" + imageFile.getOriginalFilename();
		Path uploadPath = Paths.get(uploadDir);
	
	if (!Files.exists(uploadPath)) {
		Files.createDirectories(uploadPath);	
	}
	try(InputStream inputStream = imageFile.getInputStream()) {
		Files.copy(inputStream, uploadPath.resolve(storageFileName), StandardCopyOption.REPLACE_EXISTING);
	}
	return storageFileName;
}
	private void deleteImage(String fileName) throws Exception {
		Path filePath =Paths.get(uploadDir + fileName);
		Files.deleteIfExists(filePath);
	}

	@Override
	public void deleteStudent(long id) throws Exception {
		// TODO Auto-generated method stub
		Student student = getStudentById(id);
		deleteImage(student.getImageFileName());
		studentRepository.delete(student);	
		
	}

	@Override
	public void deleteProduct(long id) throws Exception {
		// TODO Auto-generated method stub
		
	}
} 
