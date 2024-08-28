package org.example.studentstest.repository;

import org.example.studentstest.dao.StudentDao;
import org.example.studentstest.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class NamedJdbcStudentDao implements StudentDao {

    private static final String CREATE_STUDENT_SQL = "" +
        "insert into students (first_name, last_name, patronymic, birth_date, group_name, unique_number) " +
        "values (:firstName, :lastName, :patronymic, :birthDate, :group, :uniqueNumber)";

    private static final String GET_STUDENT_SQL = "" +
            "select * " +
            "from students " +
            "where unique_number = :uniqueNumber";

    private static final String DELETE_STUDENT_SQL = "" +
            "delete " +
            "from students " +
            "where unique_number = :uniqueNumber";

    private static final String GET_ALL_STUDENTS_SQL = "" +
            "select * " +
            "from students";

    private static final RowMapper<Student> STUDENT_ROW_MAPPER =
            (rs, i) -> new Student(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("patronymic"), rs.getDate("birth_date").toLocalDate(), rs.getString("group_name"), rs.getString("unique_number"));

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public NamedJdbcStudentDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Student addStudent(Student student) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                CREATE_STUDENT_SQL,
                new MapSqlParameterSource()
                        .addValue("firstName", student.getFirstName())
                        .addValue("lastName", student.getLastName())
                        .addValue("patronymic", student.getPatronymic())
                        .addValue("birthDate", student.getBirthDate())
                        .addValue("group", student.getGroup())
                        .addValue("uniqueNumber", student.getUniqueNumber())
                ,
                keyHolder,
                new String[] { "id" }
        );

        var studentId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new Student(studentId, student.getFirstName(), student.getLastName(), student.getPatronymic(), student.getBirthDate(), student.getGroup(), student.getUniqueNumber());
    }

    @Override
    public Optional<Student> getByUniqueNumber(String uniqueNumber) {
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                GET_STUDENT_SQL,
                new MapSqlParameterSource("uniqueNumber", uniqueNumber),
                STUDENT_ROW_MAPPER
        ));
    }

    @Override
    public void deleteByUniqueNumber(String uniqueNumber) {
        if (getByUniqueNumber(uniqueNumber).isEmpty()) {
            throw new RuntimeException("Student with unique number " + uniqueNumber + " not found");
        }

        namedParameterJdbcTemplate.update(
                DELETE_STUDENT_SQL,
                new MapSqlParameterSource("uniqueNumber", uniqueNumber)
        );
    }

    @Override
    public List<Student> findAll() {
        return namedParameterJdbcTemplate.query(
                GET_ALL_STUDENTS_SQL,
                STUDENT_ROW_MAPPER
        );
    }

}
