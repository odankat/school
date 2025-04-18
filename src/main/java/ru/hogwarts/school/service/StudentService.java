package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Value("@{avatars.dir.path}")
    private String avatarsDir;
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public Student createStudent(Student student) {
        logger.info("method for create student");
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        logger.info("method for get student");
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Student student) {
        logger.info("method for update student");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        logger.info("method for delete student");
        studentRepository.deleteById(id);
    }

    public List<Student> findAllByAgeBetween(int minAge, int maxAge) {
        logger.info("method for find all student by age");
        return studentRepository.findAllByAgeBetween(minAge, maxAge);
    }

    public List<Student> findAll() {
        logger.info("method for find all student");
        return studentRepository.findAll();
    }

    public Faculty getFaculty(Long studentId) {
        logger.info("method for get faculty student");
        return studentRepository.findById(studentId)
                .map(Student::getFaculty)
                .orElse(null);
    }

    public Integer getNumberAllStudent() {
        logger.info("method for get number all student");
        return studentRepository.getNumbersAllStudent();
    }

    public Integer getMiddleAgeStudent() {
        logger.info("method for get middle age student");
        return studentRepository.getMiddleAgeStudent();
    }

    public List<Student> getLustStudent() {
        logger.info("method for get lust student");
        return studentRepository.getLustStudent();
    }

}
