package edu.mum.eregistrar.controller;

import edu.mum.eregistrar.model.Student;
import edu.mum.eregistrar.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(value ="/eregistar/Student" )
public class StudentController {
@Autowired
private StudentService studentService;
    /*@Autowired
    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }*/
    @GetMapping(value = {"/list","/home/Student/list"})
    public ModelAndView listBooks(@RequestParam(defaultValue = "0") int pageno) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("students", studentService.getAllStudentPaged(pageno));
        modelAndView.addObject("currentPageNo", pageno);
        modelAndView.setViewName("home/Student/list");
        return modelAndView;
    }
    @GetMapping(value = {"/search"})
    public ModelAndView searchStudents(@RequestParam String studentNumber) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("student", studentService.findByStudentNumber(studentNumber));
        modelAndView.addObject("currentPageNo", 0);
        modelAndView.setViewName("home/Student/list");
        return modelAndView;
    }
    @GetMapping(value = {"/new"})
    public String displayNewStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "home/Student/new";
    }
    @PostMapping(value = {"/new"})
    public String addNewStudent(@Valid @ModelAttribute("student") Student student,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "home/Student/new";
        }
        student = studentService.saveStudent(student);
        return "redirect:home/Student/list";
    }
    @GetMapping(value = {"/edit/{studentId}"})
    public String editStudent(@PathVariable Long studentId, Model model) {
        Student student = studentService.getStudentById(studentId);
        if (student != null) {
            model.addAttribute("student", student);
            return "home/Student/edit";
        }
        return "Student/list";
    }
    @PostMapping(value = {"/edit"})
    public String updateStudent(@Valid @ModelAttribute("student") Student student,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "home/Student/edit";
        }
        student = studentService.saveStudent(student);
        return "redirect:home/Student/list";
    }
    @GetMapping(value = {"/delete/{studentId}"})
    public String deleteStudent(@PathVariable Long studentId, Model model) {
        studentService.deleteStudentById(studentId);
        return "redirect:home/Student/list";
    }


}
