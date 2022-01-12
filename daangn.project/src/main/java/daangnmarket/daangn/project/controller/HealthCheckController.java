package daangnmarket.daangn.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @PostMapping("/test")
    public ResponseEntity<String> ip (HttpServletRequest request) {
        return ResponseEntity.ok(request.getRemoteAddr());
    }

}