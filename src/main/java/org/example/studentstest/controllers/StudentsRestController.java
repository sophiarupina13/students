package org.example.studentstest.controllers;

import org.example.studentstest.repository.NamedJdbcStudentDao;
import org.example.studentstest.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsRestController {

    private final NamedJdbcStudentDao namedJdbcStudentDao;

    @Autowired
    public StudentsRestController(NamedJdbcStudentDao namedJdbcStudentDao) {
        this.namedJdbcStudentDao = namedJdbcStudentDao;
    }

    @PostMapping("")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        try {
            namedJdbcStudentDao.addStudent(student);
            return ResponseEntity.ok().build();
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("\nСтудент с таким уникальным номером уже существует: " + student.getUniqueNumber());
        }
    }

    @DeleteMapping("/{uniqueNumber}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String uniqueNumber) {
        namedJdbcStudentDao.deleteByUniqueNumber(uniqueNumber);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(namedJdbcStudentDao.findAll());
    }

}
