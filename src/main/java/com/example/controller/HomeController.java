package com.example.smartclassroom.controller;

import com.example.smartclassroom.model.Assignment;
import com.example.smartclassroom.model.Attendance;
import com.example.smartclassroom.model.Notice;
import com.example.smartclassroom.model.Student;
import com.example.smartclassroom.model.User;
import com.example.smartclassroom.repository.AssignmentRepository;
import com.example.smartclassroom.repository.AttendanceRepository;
import com.example.smartclassroom.repository.NoticeRepository;
import com.example.smartclassroom.repository.StudentRepository;
import com.example.smartclassroom.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String home(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }
        return "home";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", username);
            return "redirect:/home";
        }

        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        User existingUser = userRepository.findByUsername(username);

        if (existingUser != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        userRepository.save(user);

        model.addAttribute("success", "Registration successful. Please login.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/students")
    public String students(@RequestParam(required = false) String date, Model model) {
        if (date != null && !date.isEmpty()) {
            model.addAttribute("students", studentRepository.findByDate(date));
            model.addAttribute("selectedDate", date);
        } else {
            model.addAttribute("students", studentRepository.findAll());
        }
        return "students";
    }

    @GetMapping("/attendance")
    public String attendance(@RequestParam(required = false) String date, Model model) {
        if (date != null && !date.isEmpty()) {
            model.addAttribute("attendanceList", attendanceRepository.findByDate(date));
            model.addAttribute("selectedDate", date);
        } else {
            model.addAttribute("attendanceList", attendanceRepository.findAll());
        }
        return "attendance";
    }

    @GetMapping("/assignments")
    public String assignments(Model model) {
        model.addAttribute("assignments", assignmentRepository.findAll());
        return "assignments";
    }

    @GetMapping("/notices")
    public String notices(Model model) {
        model.addAttribute("notices", noticeRepository.findAll());
        return "notices";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(
            @RequestParam String name,
            @RequestParam String rollNumber,
            @RequestParam String className,
            @RequestParam String date) {

        Student student = new Student();
        student.setName(name);
        student.setRollNumber(rollNumber);
        student.setClassName(className);
        student.setDate(date);

        studentRepository.save(student);

        return "redirect:/students";
    }

    @PostMapping("/saveAttendance")
    public String saveAttendance(
            @RequestParam String studentName,
            @RequestParam String status,
            @RequestParam String date) {

        Attendance attendance = new Attendance();
        attendance.setStudentName(studentName);
        attendance.setStatus(status);
        attendance.setDate(date);

        attendanceRepository.save(attendance);

        return "redirect:/attendance";
    }

    @PostMapping("/saveAssignment")
    public String saveAssignment(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String dueDate) {

        Assignment assignment = new Assignment();
        assignment.setTitle(title);
        assignment.setDescription(description);
        assignment.setDueDate(dueDate);

        assignmentRepository.save(assignment);

        return "redirect:/assignments";
    }

    @PostMapping("/saveNotice")
    public String saveNotice(
            @RequestParam String title,
            @RequestParam String message) {

        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setMessage(message);

        noticeRepository.save(notice);

        return "redirect:/notices";
    }
}