package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.PortService;


import java.util.stream.Stream;

@RestController
@RequestMapping("port")

public class infoController {
    private final Logger logger = LoggerFactory.getLogger(infoController.class);
    @Value("${server.port}")
    private String port;

    @Autowired
    private final PortService portService;
    private long startTime;
    private long finishTime;

    public infoController(PortService portService) {
        this.portService = portService;
    }

    @GetMapping
    public String infoPort() {
        // return portService.portInfo();
        return port;
    }

    @GetMapping("calculation")
    public void calculate() {

        startTime = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        finishTime = System.currentTimeMillis();
        logger.info("время выполнения базы равно " + (finishTime - startTime) + " мс");



        startTime = System.currentTimeMillis();
        sum = 0;
        for (int i = 0; i < 1000000; i++) {
            sum += i;
        }
        finishTime = System.currentTimeMillis();
        logger.info("время выполнения цикла равно " + (finishTime - startTime) + " мс");
    }
}
