package com.tejas.studentapi.services;

import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.tejas.studentapi.entity.Student;
import com.tejas.studentapi.repo.StdRepository;

@Service
public class StudentServiceImpl implements StdService {

    private final StdRepository studentRepository;

    private final ObjectProvider<JdbcTemplate> localJdbcTemplateProvider;

    public StudentServiceImpl(
            StdRepository studentRepository,
            @Qualifier("localJdbcTemplate")
            ObjectProvider<JdbcTemplate> localJdbcTemplateProvider) {

        this.studentRepository = studentRepository;
        this.localJdbcTemplateProvider = localJdbcTemplateProvider;
    }

    @Override
    public Student addStudent(Student student) {

        // 1. Save student in AWS RDS
        Student savedStudent = studentRepository.save(student);

        // 2. Get local MySQL JdbcTemplate if available
        JdbcTemplate localJdbcTemplate =
                localJdbcTemplateProvider.getIfAvailable();

        // 3. Save student in local MySQL
        if (localJdbcTemplate != null) {

            localJdbcTemplate.update(
                "INSERT INTO students (name, email, course, fees) VALUES (?, ?, ?, ?)",
                savedStudent.getName(),
                savedStudent.getEmail(),
                savedStudent.getCourse(),
                savedStudent.getFees()
            );
        }

        return savedStudent;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Integer id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Student not found with ID: " + id));
    }

    @Override
    public Student updateStudent(Integer id, Student student) {

        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Student not found with ID: " + id));

        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setCourse(student.getCourse());
        existingStudent.setFees(student.getFees());

        return studentRepository.save(existingStudent);
    }

    @Override
    public void deleteStudent(Integer id) {

        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with ID: " + id);
        }

        studentRepository.deleteById(id);
    }
}