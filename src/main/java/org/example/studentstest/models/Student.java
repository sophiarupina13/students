package org.example.studentstest.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Student {

    private final long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate birthDate;
    private String group;
    private String uniqueNumber;

    public Student(long id, String firstName, String lastName, String patronymic, LocalDate birthDate, String group, String uniqueNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.group = group;
        this.uniqueNumber = uniqueNumber;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", group='" + group + '\'' +
                ", uniqueNumber='" + uniqueNumber + '\'' +
                '}';
    }

}

