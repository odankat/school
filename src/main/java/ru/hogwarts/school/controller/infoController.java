package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.PortService;

@RestController
@RequestMapping("port")

public class infoController {
    @Value("${server.port}")
    private String port;

    @Autowired
    private final PortService portService;

    public infoController(PortService portService) {
        this.portService = portService;
    }

    @GetMapping
    public String infoPort() {
        // return portService.portInfo();
        return port;
    }
}
