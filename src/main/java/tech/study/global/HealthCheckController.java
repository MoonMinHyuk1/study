package tech.study.global;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/api")
    public String healthCheck1() {
        return "Server is up!";
    }

    @GetMapping("/api/health")
    public String healthCheck2() {
        return "Health check!";
    }
}
