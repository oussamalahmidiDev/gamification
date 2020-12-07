package com.learn.microservices.multiplication.serviceclients;

import com.learn.microservices.multiplication.challenge.ChallengeAttempt;
import com.learn.microservices.multiplication.challenge.ChallengeSolvedEvent;
import lombok.experimental.NonFinal;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@lombok.Value
@NonFinal
public class ChallengeSolvedEventPublisher {

    AmqpTemplate amqpTemplate;
    String attemptsTopicExchange;

    public ChallengeSolvedEventPublisher(
        AmqpTemplate amqpTemplate,
        @Value("${amqp.exchange.attempts}") String attemptsTopicExchange
    ) {
        this.amqpTemplate = amqpTemplate;
        this.attemptsTopicExchange = attemptsTopicExchange;
    }


    public void publishChallengeSolvedEvent(final ChallengeAttempt attempt) {
        ChallengeSolvedEvent challengeSolvedEvent = buildEventFromAttempt(attempt);
        amqpTemplate.convertAndSend(
            attemptsTopicExchange,
            "attempt." + (attempt.isCorrect()? "correct" : "wrong"),
            challengeSolvedEvent
        );
    }

    private ChallengeSolvedEvent buildEventFromAttempt(ChallengeAttempt attempt) {
        return new ChallengeSolvedEvent(
            attempt.getId(),
            attempt.isCorrect(),
            attempt.getFactorA(),
            attempt.getFactorB(),
            attempt.getUser().getId(),
            attempt.getUser().getAlias()
        );
    }
}
