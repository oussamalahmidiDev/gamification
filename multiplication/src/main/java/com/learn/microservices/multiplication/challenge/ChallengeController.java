package com.learn.microservices.multiplication.challenge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/challenges")
public class ChallengeController {
    private final ChallengeService service;

    @GetMapping("/random")
    ResponseEntity<Challenge> getRandomChallenge() {
        Challenge challenge = service.generateRandomChallenge();
        log.info("Generated random challenge : {}", challenge);
        return ResponseEntity.ok(challenge);
    }
}
