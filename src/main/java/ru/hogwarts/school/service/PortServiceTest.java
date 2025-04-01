package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("production")

public class PortServiceTest implements PortService {
    @Value("${server.port}")
    private String port;

    @Override
    public String portInfo() {
        port = "0000";
        return port;
    }

}
