package com.tejas.studentapi.services;



import java.util.List;

import com.tejas.studentapi.entity.Student;

public interface StdService {

    Student addStudent(Student student);

    List<Student> getAllStudents();

    Student getStudentById(Integer id);

    Student updateStudent(Integer id, Student student);

    void deleteStudent(Integer id);
}

