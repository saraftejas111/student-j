package com.tejas.studentapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tejas.studentapi.entity.Student;

public interface StdRepository extends JpaRepository<Student, Integer> {

}
