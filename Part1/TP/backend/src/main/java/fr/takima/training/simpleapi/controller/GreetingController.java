package fr.takima.training.simpleapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@CrossOrigin
public class GreetingController {

    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Value("${greeting.default-name:World}")
    private String defaultName;

    @GetMapping
    public Greeting greeting(@RequestParam(value = "name", required = false) String name) {
        if (name == null) name = defaultName;
        return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
    }

    record Greeting(long id, String content) {}
}
