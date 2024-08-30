package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)

public class StudentControllerIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private FacultyRepository facultyRepository;

    @BeforeEach
    void clearDatabase() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
        studentRepository.deleteAll();
    }

    @Test
    void shouldCreateStudent() {
        //given
        Student student = new Student(1L, "name", 10);
        //when
        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );
        //then
        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatus.valueOf(200));
        Student actualStudent = studentResponseEntity.getBody();
        assertEquals(actualStudent.getName(), student.getName());
        assertEquals(actualStudent.getAge(), student.getAge());
    }

    @Test
    void shouldFindById() {
        //given
        Student student = new Student(1L, "name", 10);
        studentRepository.save(student);

        //when
        ResponseEntity<Student> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + student.getId(),
                Student.class
        );
        //then
        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatus.valueOf(200));
        Student actualStudent = studentResponseEntity.getBody();
        assertEquals(actualStudent.getName(), student.getName());
        assertEquals(actualStudent.getAge(), student.getAge());
    }

    @Test
    void shouldFindAll() {
        //given
        Student student = new Student(1L, "name", 10);
        Student student1 = new Student(0L, "name1", 11);
        Student studentExpected = studentRepository.save(student);
        Student studentExpected1 = studentRepository.save(student1);
        List<Student> students = new ArrayList<>();
        students.add(studentExpected);
        students.add(studentExpected1);

        //when
        ResponseEntity<List<Student>> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                }
        );
        //then
        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatus.valueOf(200));
        assertEquals(studentResponseEntity.getBody(), students);
    }

    @Test
    void shouldFindAllByAgeBetween() {
        //given
        Student studentAbs = new Student(1L, "name", 10);
        Student studentAbs1 = new Student(2L, "name1", 15);
        Student studentAbs2 = new Student(3L, "name2", 20);
        Student student = studentRepository.save(studentAbs);
        Student student1 = studentRepository.save(studentAbs1);
        Student student2 = studentRepository.save(studentAbs2);
        Student s = student;
        Student s1 = student1;
        List<Student> students = new ArrayList<>();
        students.add(s);
        students.add(s1);
        //when
        ResponseEntity<List<Student>> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/byAgeBetween?minAge=" + s.getAge() + "&maxAge=" + s1.getAge(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        //then
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatus.valueOf(200));
        assertNotNull(studentResponseEntity.getBody());
        assertEquals(studentResponseEntity.getBody(), students);
    }

    @Test
    void shouldUpdateStudent() {
        //given
        Student studentAbs = new Student(1L, "name", 10);
        Student student = studentRepository.save(studentAbs);
        Student studentActual = new Student(student.getId(), "test", 15);
        //when
        restTemplate.put(
                "http://localhost:" + port + "/student?id=" + student.getId(),
                studentActual
        );
        //then
        Student studentExpected = restTemplate.getForObject(
                "http://localhost:" + port + "/student/" + student.getId(),
                Student.class
        );
        assertEquals(studentExpected.getAge(), studentActual.getAge());
        assertEquals(studentExpected.getName(), studentActual.getName());
    }

    @Test
    void shouldDeleteStudent() {
        //given
        Student student = new Student(1L, "name", 10);
        studentRepository.save(student);
        Long idTest = student.getId();
        //when
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + idTest,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        ResponseEntity<Student> result = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + idTest,
                Student.class
        );
        //then
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatus.valueOf(200));
        assertEquals(result.getStatusCode(), HttpStatus.valueOf(404));
    }

    @Test
    void shouldGetNumberAllStudent() {
        //given
        Student student = new Student(1L, "name", 10);
        Student student1 = new Student(2L, "name1", 10);
        Student student2 = new Student(3L, "name2", 10);
        studentRepository.save(student);
        studentRepository.save(student1);
        studentRepository.save(student2);
        int a = 3;
        //when
        ResponseEntity<Integer> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/number",
                Integer.class
        );
        //then
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatus.valueOf(200));
        assertEquals(a, studentResponseEntity.getBody());
    }

    @Test
    void shouldGetMiddleAgeStudent() {
        //given
        Student student = new Student(1L, "name", 10);
        Student student1 = new Student(2L, "name1", 15);
        Student student2 = new Student(3L, "name2", 20);
        studentRepository.save(student);
        studentRepository.save(student1);
        studentRepository.save(student2);
        int middleAge = (student.getAge() + student1.getAge() + student2.getAge()) / studentRepository.findAll().size();
        //when
        ResponseEntity<Integer> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/middle_age",
                Integer.class
        );
        //then
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatus.valueOf(200));
        assertEquals(middleAge, studentResponseEntity.getBody());
    }

    @Test
    void shouldGetLustStudent() {
        //given
        Student student = new Student(1L, "name", 10);
        Student student1 = new Student(2L, "name1", 15);
        Student student2 = new Student(3L, "name2", 20);
        Student student3 = new Student(4L, "name4", 10);
        Student student4 = new Student(5L, "name5", 15);
        Student student5 = new Student(6L, "name6", 20);
        studentRepository.save(student);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);
        studentRepository.save(student5);
        List<Student> students = new ArrayList<>();
        students.add(student5);
        students.add(student4);
        students.add(student3);
        students.add(student2);
        students.add(student1);
        //when
        ResponseEntity<List<Student>> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/lust_student",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        //then
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatus.valueOf(200));
        assertEquals(students,studentResponseEntity.getBody());
    }


}
