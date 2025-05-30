package ru.hogwarts.school.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StudentController.class)
public class StudentControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    @MockBean
    private AvatarService avatarService;

    @Test
    void shouldGetStudent() throws Exception {
        // given
        Long studentId = 1L;
        Student student = new Student(studentId, "test", 12);
        when(studentService.getStudentById(studentId)).thenReturn(student);
        // when
        ResultActions perform = mockMvc.perform(get("/student/{id}", studentId));
        // then
        perform
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }

    @Test
    void shouldCreateStudent() throws Exception {
        // given
        Long studentId = 1L;
        Student student = new Student(studentId, "test", 12);
        Student student1 = new Student(studentId, "test", 12);
        student1.setId(studentId);
        when(studentService.createStudent(student)).thenReturn(student1);
        // when
        ResultActions perform = mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        // then
        perform
                .andExpect(jsonPath("$.id").value(student1.getId()))
                .andExpect(jsonPath("$.name").value(student1.getName()))
                .andExpect(jsonPath("$.age").value(student1.getAge()))
                .andDo(print());
    }

    @Test
    void shouldUpdateStudent() throws Exception {
        // given
        Long studentId = 1L;
        Student student = new Student(studentId, "test", 10);
        when(studentService.updateStudent(student)).thenReturn(student);

        // when
        ResultActions perform = mockMvc.perform(put("/student", student)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));
        // then
        perform.
                 andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }
}