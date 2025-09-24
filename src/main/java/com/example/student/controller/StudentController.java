package com.example.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.student.StudentInfoApplication;
import com.example.student.models.Student;
import com.example.student.models.StudentDto;
import com.example.student.service.StudentService;
import com.example.student.service.StudentServiceImpl;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentInfoApplication studentInfoApplication;

    @Autowired
    private StudentService studentService;

    public StudentController(StudentServiceImpl studentService, StudentInfoApplication studentInfoApplication) {
        this.studentService = studentService;
        this.studentInfoApplication = studentInfoApplication;
    }

    @GetMapping({"", "/"})
    public String showStudentList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Student> studentPage = studentService.getAllStudents(pageable);

        model.addAttribute("studentPage", studentPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentPage.getTotalPages());

        return "students/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("studentDto", new StudentDto());
        return "students/CreateStudent";
    }

    @PostMapping("/create")
    public String createStudent(@Valid @ModelAttribute StudentDto studentDto, BindingResult result) {
        if (result.hasErrors()) {
            return "students/CreateStudent";
        }
        try {
            studentService.saveStudent(studentDto, studentDto.getImageFile());
        } catch (Exception e) {
            e.printStackTrace();
            result.rejectValue("imageFile", "error.imageFile", "Failed to save the profile.");
            return "students/CreateStudent";
        }
        return "redirect:/students";
    }

    @GetMapping("/edit")
    public String showEditPage(@RequestParam long id, Model model) {
        Student student = studentService.getStudentById(id);
        StudentDto studentDto = new StudentDto();

        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setEmail(student.getEmail());
        studentDto.setPhone(student.getPhone());
        studentDto.setGender(student.getGender());
        studentDto.setAddress(student.getAddress());

        model.addAttribute("student", student);
        model.addAttribute("studentDto", studentDto);
        return "students/EditStudent";
    }

    @PostMapping("/edit/{id}")
    public String editStudent(@PathVariable long id,
                              @Valid @ModelAttribute StudentDto studentDto,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "students/EditStudent";
            
        }
        try {
            studentService.updateStudent(id, studentDto, studentDto.getImageFile());
        } catch (Exception e) {
            e.printStackTrace();
            result.rejectValue("imageFile", "error.imageFile", "Failed to update the profile.");
            return "students/EditStudent";
        }
        return "redirect:/students";
    }


    @GetMapping("/delete")
    public String deleteStudent(@RequestParam long id) {
        try {
            studentService.deleteStudent(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/students";
    }
    
    @PostMapping("/save")
    public String studentSave(@ModelAttribute("studentDto") StudentDto studentDto) {
    	try {
			studentService.saveStudent(studentDto,studentDto.getImageFile());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return "redirect:/students"; // go back to index page
    }

}
