
package com.tejas.studentapi.localrepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tejas.studentapi.entity.LocalStudent;

public interface LocalStdRepository
        extends JpaRepository<LocalStudent, Integer> {
}
