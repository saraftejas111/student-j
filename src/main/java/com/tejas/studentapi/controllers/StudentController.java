
package com.tejas.studentapi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tejas.studentapi.entity.Student;
import com.tejas.studentapi.services.StdService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StdService studentService;

    public StudentController(StdService studentService) {
        this.studentService = studentService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {

        Student savedStudent = studentService.addStudent(student);

        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {

        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id) {

        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Integer id,
            @RequestBody Student student) {

        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {

        studentService.deleteStudent(id);

        return ResponseEntity.ok("Student deleted successfully");
    }
}
