package com.learn.microservices.gamification.game;

import com.learn.microservices.gamification.challenge.ChallengeSolvedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameEventHandler {

    private final GameServiceImpl service;

    @RabbitListener(queues = "${amqp.queue.gamification}")
    void handleChallengeSolvedEvent(final ChallengeSolvedEvent event) {
        log.info("Challenge Solved event received: {}", event);
        try {
            service.newAttemptForUser(event);
        } catch (Exception e) {
            log.error("Error processing event : {}", e.getMessage());
        }
    }
}
