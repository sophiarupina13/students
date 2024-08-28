$(document).ready(function () {
    loadStudents();

    $("#addStudentForm").on("submit", function (event) {
        event.preventDefault();
        $(".error").hide();

        const firstName = $("#firstName").val();
        const lastName = $("#lastName").val();
        const patronymic = $("#patronymic").val();
        const birthDate = $("#birthDate").val();
        const group = $("#group").val();
        const uniqueNumber = $("#uniqueNumber").val();

        if (
            !firstName ||
            !lastName ||
            !patronymic ||
            !birthDate ||
            !group ||
            !uniqueNumber
        ) {
            $(".error").show();
            return;
        }
        const student = {
            firstName: firstName,
            lastName: lastName,
            patronymic: patronymic,
            birthDate: birthDate,
            group: group,
            uniqueNumber: uniqueNumber,
        };
        $.ajax({
            url: "http://localhost:8080/students",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(student),
            success: function () {
                loadStudents();
                $("#addStudentForm")[0].reset();
            },
            error: function (jqXHR) {
                if (jqXHR.status === 409) {
                    alert("Student with this unique number already exists.");
                } else {
                    alert("An error occurred: " + jqXHR.responseText);
                }
            },
        });
    });

    function loadStudents() {
        $.ajax({
            url: "http://localhost:8080/students",
            type: "GET",
            success: function (students) {
                $("#studentsTable tbody").empty();
                students.forEach(function (student) {
                    $("#studentsTable tbody").append(
                        `<tr>
                          <td>${student.id}</td>
                          <td>${student.firstName}</td>
                          <td>${student.lastName}</td>
                          <td>${student.patronymic}</td>
                          <td>${student.birthDate}</td>
                          <td>${student.group}</td>
                          <td>${student.uniqueNumber}</td>
                          <td><button class="deleteButton" onclick="deleteStudent('${student.uniqueNumber}')">Delete</button></td>
                      </tr>`
                    );
                });
            },
            error: function () {
                alert("An error occurred while loading students.");
            },
        });
    }

    window.deleteStudent = function (uniqueNumber) {
        $.ajax({
            url: `http://localhost:8080/students/${uniqueNumber}`,
            type: "DELETE",
            success: function () {
                loadStudents();
            },
            error: function () {
                alert("An error occurred while deleting the student.");
            },
        });
    };
});
