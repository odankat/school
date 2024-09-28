package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findById(Long id) {
        return facultyRepository.findById(id)
                .orElse(null);
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> findAllByColorBetween(String name, String color) {
        return facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Set<Student> getStudents(Long facultyId) {
        return facultyRepository.findById(facultyId)
                .map(Faculty::getStudents)
                .orElse(null);
    }

    public String getLongName() {
        return facultyRepository.findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElse(":(");
    }

}
