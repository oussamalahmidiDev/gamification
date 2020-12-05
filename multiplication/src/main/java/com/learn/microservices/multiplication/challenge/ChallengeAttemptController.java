package com.learn.microservices.multiplication.challenge;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/attempts")
@RequiredArgsConstructor
class ChallengeAttemptController {
    private final IChallengeService service;

    @PostMapping
    public ResponseEntity<ChallengeAttempt> postResults(@RequestBody @Valid ChallengeAttemptDTO attemptDTO) {
        return ResponseEntity.ok(service.verifyAttempt(attemptDTO));
    }

    @GetMapping
    public ResponseEntity<List<ChallengeAttempt>> recentAttempts(@RequestParam("alias") String alias) {
        return ResponseEntity.ok(service.getStatsForUser(alias));
    }

    @GetMapping("/hello")
    String getResults() {
        return "Hello from dockerok0";
    }
}
