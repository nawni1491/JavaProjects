package com.example.student.models;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class StudentDto {
    
	
	private Long id;
	
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@NotEmpty(message = "Name is required")
    private String name;
    
    @NotEmpty(message = "Email is required")
    private String email;
    
    @NotEmpty(message = "Phone is required")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "Phone must be digits (7–15 characters)")
    private String phone;
    
    @NotEmpty(message = "Gender is required")
    private String gender;
    
    @NotEmpty(message = "Address is required")
    private String address;
    
    
    private MultipartFile imageFile; 
    
    // --- Getters and Setters ---
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
    
    public MultipartFile getImageFile() {
        return imageFile;
    }
    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
