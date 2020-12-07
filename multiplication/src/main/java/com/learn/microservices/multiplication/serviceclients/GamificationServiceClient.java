package com.learn.microservices.multiplication.serviceclients;

import com.learn.microservices.multiplication.challenge.ChallengeAttempt;
import com.learn.microservices.multiplication.challenge.ChallengeSolvedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GamificationServiceClient {
    private final RestTemplate restTemplate;
    private final String gamificationHostUrl;

    public GamificationServiceClient(
        final RestTemplateBuilder builder,
        @Value("${service.gamification.host}") final String gamificationHostUrl
    ) {
        restTemplate = builder.build();
        this.gamificationHostUrl = gamificationHostUrl;
    }

    public HttpStatus sendAttempt(final ChallengeAttempt attempt) {
        try {
            ChallengeSolvedEvent dto = new ChallengeSolvedEvent(attempt.getId(), attempt.isCorrect(), attempt.getFactorA(), attempt.getFactorB(), attempt.getUser().getId(), attempt.getUser().getAlias());
            ResponseEntity<String> r = restTemplate
                .postForEntity(gamificationHostUrl + "/attempts", dto, String.class);

            log.info("Gamification service response: {}", r.getStatusCode());
            return r.getStatusCode();
        } catch (Exception e) {
            log.error("There was a problem sending the attempt.", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
