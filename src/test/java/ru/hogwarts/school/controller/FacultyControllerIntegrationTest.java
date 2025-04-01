package ru.hogwarts.school.controller;
/*
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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FacultyControllerIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
     void clearDatabase() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
        studentRepository.deleteAll();
    }

    @Test
    void shouldCreateFaculty() {
        //given
        Faculty faculty = new Faculty(1L, "name", "color");
        //when
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                faculty,
                Faculty.class
        );
        //then
        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatus.valueOf(200));
        Faculty actualFaculty = facultyResponseEntity.getBody();
        assertEquals(actualFaculty.getName(), faculty.getName());
        assertEquals(actualFaculty.getColor(), faculty.getColor());
    }



    @Test
    void shouldFindById() {
        //given
        Faculty faculty = new Faculty(1L, "name", "color");
        facultyRepository.save(faculty);

        //when
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + faculty.getId(),
                Faculty.class
        );
        //then
        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatus.valueOf(200));
        Faculty actualFaculty = facultyResponseEntity.getBody();
        assertEquals(actualFaculty.getName(), faculty.getName());
        assertEquals(actualFaculty.getColor(), faculty.getColor());
    }

    @Test
    void shouldFindAllByColor() {
        //given
        Faculty faculty1 = new Faculty(1L, "name1", "color1");
        Faculty faculty2 = new Faculty(1L, "name2", "color2");
        Faculty faculty3 = new Faculty(1L, "name3", "color3");
        facultyRepository.save(faculty2);
        facultyRepository.save(faculty3);
        Faculty expected = facultyRepository.save(faculty1);
        List<Faculty> expectedFacultyList = new ArrayList<>();
        expectedFacultyList.add(expected);

        //when
        ResponseEntity<List<Faculty>> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/findAllByColorOrName" + "?name=" + faculty1.getName(), //+  "&color=color3" ,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        //then
        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatus.valueOf(200));
        assertEquals(facultyResponseEntity.getBody(), expectedFacultyList);
    }


    @Test
    void shouldGetStudents() {
        //given
        Faculty faculty = new Faculty(1L, "name1", "color1");
        Faculty faculty1 = facultyRepository.save(faculty);
        Student student = new Student(1L, "name", 10);
        Student student2 = new Student(2L, "nameQ", 10);
        student.setFaculty(faculty1);
        student2.setFaculty(faculty1);
        studentRepository.save(student);
        studentRepository.save(student2);
        Collection<Student> studentsExpected = new ArrayList<>();
        studentsExpected.add(student);
        studentsExpected.add(student2);
        //when
        ResponseEntity<Collection<Student>> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + faculty1.getId() + "/students",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        //then
        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatus.valueOf(200));
        assertEquals(facultyResponseEntity.getBody(), studentsExpected);

    }

    @Test
    void shouldUpdateFaculty() {
        //given
        Faculty facultyAbs = new Faculty(1L, "name1", "color1");
        Faculty faculty = facultyRepository.save(facultyAbs);
        Faculty facultyExpected = new Faculty(faculty.getId(), "test", "tectColor");

        //when
         restTemplate.put(
                "http://localhost:" + port + "/faculty?id=" + faculty.getId(),
                facultyExpected
        );
        //then
        Faculty result = restTemplate.getForObject(
                "http://localhost:" + port + "/faculty/" + faculty.getId(),
                Faculty.class);

        assertEquals(result.getColor(), facultyExpected.getColor());
        assertEquals(result.getName(), facultyExpected.getName());
    }
    @Test
    void shouldDeleteFaculty() {
        //given
        Faculty faculty = new Faculty(1L, "name1", "color1");
        Faculty faculty1 = facultyRepository.save(faculty);
        Long idTest = faculty1.getId();


        //when
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + idTest,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<Faculty>() {
                }
        );
        ResponseEntity<Faculty> result1 = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + idTest,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Faculty>() {
                }
        );
        //then
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatus.valueOf(200));
        assertEquals(result1.getStatusCode(),HttpStatus.valueOf(404));
    }


}
*/
