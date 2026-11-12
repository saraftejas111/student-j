
package com.tejas.studentapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tejas.studentapi.entity.LocalStudent;
import com.tejas.studentapi.entity.Student;
import com.tejas.studentapi.localrepo.LocalStdRepository;
import com.tejas.studentapi.repo.StdRepository;

@Service
public class StudentServiceImpl implements StdService {

    private final StdRepository studentRepository;
    private final LocalStdRepository localRepository;

    public StudentServiceImpl(
            StdRepository studentRepository,
            LocalStdRepository localRepository) {

        this.studentRepository = studentRepository;
        this.localRepository = localRepository;
    }

    // INSERT STUDENT IN BOTH DATABASES
    @Override
    public Student addStudent(Student student) {

        // Save in AWS RDS
        Student savedStudent = studentRepository.save(student);

        // Create LocalStudent with AWS generated ID
        LocalStudent localStudent = new LocalStudent(
                savedStudent.getId(),
                savedStudent.getName(),
                savedStudent.getEmail(),
                savedStudent.getCourse(),
                savedStudent.getFees()
        );

        // Save in Local MySQL
        localRepository.save(localStudent);

        return savedStudent;
    }

    // GET ALL STUDENTS FROM BOTH DATABASES
    @Override
    public List<Student> getAllStudents() {

        List<Student> awsStudents = studentRepository.findAll();

        List<LocalStudent> localStudents = localRepository.findAll();

        return awsStudents;
    }

    // GET STUDENT BY ID FROM BOTH DATABASES
    @Override
    public Student getStudentById(Integer id) {

        Student awsStudent = studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found in AWS: " + id));

        localRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found in Local MySQL: " + id));

        return awsStudent;
    }

    // UPDATE STUDENT IN BOTH DATABASES
    @Override
    public Student updateStudent(Integer id, Student student) {

        Student awsStudent = studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found in AWS: " + id));

        LocalStudent localStudent = localRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found in Local MySQL: " + id));

        // Update AWS entity
        awsStudent.setName(student.getName());
        awsStudent.setEmail(student.getEmail());
        awsStudent.setCourse(student.getCourse());
        awsStudent.setFees(student.getFees());

        // Update Local entity
        localStudent.setName(student.getName());
        localStudent.setEmail(student.getEmail());
        localStudent.setCourse(student.getCourse());
        localStudent.setFees(student.getFees());

        studentRepository.save(awsStudent);
        localRepository.save(localStudent);

        return awsStudent;
    }

    // DELETE STUDENT FROM BOTH DATABASES
    @Override
    public void deleteStudent(Integer id) {

        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found in AWS: " + id);
        }

        if (!localRepository.existsById(id)) {
            throw new RuntimeException("Student not found in Local MySQL: " + id);
        }

        studentRepository.deleteById(id);
        localRepository.deleteById(id);
    }
}
