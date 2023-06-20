package tech.study.global;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/api")
    public String healthCheck() {
        return "Server is up!";
    }
}
