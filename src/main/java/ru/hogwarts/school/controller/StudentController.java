package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping()
    public List<Student> getAll() {
        return studentService.findAll();
    }

    @GetMapping("byAgeBetween")
    public List<Student> findAllByAgeBetween(int minAge, int maxAge) {
        return studentService.findAllByAgeBetween(minAge, maxAge);
    }


    @PutMapping()
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updateStudent = studentService.updateStudent(student);
        if (updateStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/faculty")
    public Faculty getFaculty(@PathVariable Long id) {
        return studentService.getFaculty(id);
    }

    @GetMapping("number")
    public Integer getNumberAllStudent() {
        return studentService.getNumberAllStudent();
    }

    @GetMapping("middle_age")
    public Integer getAvgAgeStudent() {
        return studentService.getMiddleAgeStudent();
    }

    @GetMapping("lust_student")
    public List<Student> getLustStudent() {
        return studentService.getLustStudent();
    }

    @GetMapping("name_start_with_a")
    public List<String> getStudentStartWithA() {
        return studentService.getStudentStartWithA();
    }

    @GetMapping("average_age_of_students")
    public double averageAgeOfStudents() {
        return studentService.averageAgeOfStudents();
    }
}
