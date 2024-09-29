package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {
    private Logger logger = LoggerFactory.getLogger(AvatarService.class);

    @Value("%{path.to.avatars.folder}")
    private String avatarsDir;
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    public AvatarService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public Avatar getAvatarByStudent(Student student) {
        return avatarRepository.findByStudent(student).orElseGet(() -> {
            Avatar avatar = new Avatar();
            avatar.setStudent(student);
            return avatar;
        });
    }

    public Avatar uploadAvatar(Long studentId, MultipartFile avatarFile)
            throws IOException {
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
                new IllegalArgumentException("student with id" + studentId + " is not found"));
        Path avatarPath = saveToLocalDirectory(student, avatarFile);
        Avatar avatar = saveToBd(student, avatarPath, avatarFile);
        return avatar;
    }

    private Path saveToLocalDirectory(Student student, MultipartFile avatarFile) throws IOException {

        Path avatarPath = Path.of(avatarsDir, student + "."
                + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(avatarPath.getParent());
        Files.deleteIfExists(avatarPath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(avatarPath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        return avatarPath;

    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private Avatar saveToBd(Student student, Path avatarPath, MultipartFile avatarFile) throws IOException {
        Avatar avatar = getAvatarByStudent(student);
        avatar.setFilePath(avatarPath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        return avatarRepository.save(avatar);
    }

    public Avatar findByAvatar(Long avatarId) {
        return avatarRepository.findById(avatarId).orElseThrow(() ->
                new IllegalArgumentException("Avatar with id " + avatarId + " is not found in database"));

    }

    public Page<Avatar> findAll(int page, int size) {
        var pageRequest = PageRequest.of(page, size);
        return avatarRepository.findAll(pageRequest);
    }
}
