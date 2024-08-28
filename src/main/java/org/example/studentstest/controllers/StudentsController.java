package org.example.studentstest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentsController {

    @GetMapping("/students-db")
    public String getStudents() {
        return "students";
    }

}
