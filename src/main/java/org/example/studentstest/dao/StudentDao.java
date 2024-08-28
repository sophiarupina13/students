package org.example.studentstest.dao;

import org.example.studentstest.models.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentDao {
    Student addStudent(Student student);
    Optional<Student> getByUniqueNumber(String uniqueNumber);
    void deleteByUniqueNumber(String uniqueNumber);
    List<Student> findAll();
}
