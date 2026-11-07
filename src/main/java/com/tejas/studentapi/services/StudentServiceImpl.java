
package com.tejas.studentapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tejas.studentapi.entity.Student;
import com.tejas.studentapi.repo.StdRepository;

@Service
public class StudentServiceImpl implements StdService {

    private final StdRepository studentRepository;

    public StudentServiceImpl(StdRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {

        return studentRepository.save(student);
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
