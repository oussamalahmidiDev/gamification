package com.learn.microservices.multiplication.serviceclients;

import com.learn.microservices.multiplication.challenge.ChallengeAttempt;
import com.learn.microservices.multiplication.challenge.ChallengeSolvedEvent;
import com.learn.microservices.multiplication.user.User;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChallengeSolvedEventPublisherTest {

    @Mock
    private AmqpTemplate template;

    private ChallengeSolvedEventPublisher publisher;

    @BeforeEach
    void setUp() {
        publisher = new ChallengeSolvedEventPublisher(template, "test.topic");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void sendsAttempt(boolean isCorrect) {
        ChallengeAttempt attempt = createTestAttempt(isCorrect);

        publisher.publish(attempt);

        ArgumentCaptor<String> exchange = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> key = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ChallengeSolvedEvent> event = ArgumentCaptor.forClass(ChallengeSolvedEvent.class);

        verify(template)
            .convertAndSend(
                exchange.capture(), // Exchange
                key.capture(), // Route key
                event.capture()
            );
        then(exchange.getValue()).isEqualTo("test.topic");
        then(key.getValue()).isEqualTo(isCorrect? "attempt.correct" : "attempt.wrong");
        then(event.getValue()).isEqualTo(solvedEvent(isCorrect));
    }

    private ChallengeSolvedEvent solvedEvent(boolean isCorrect) {
        return new ChallengeSolvedEvent(1L, isCorrect, 30, 40, 10L, "john");
    }

    private ChallengeAttempt createTestAttempt(boolean isCorrect) {
        return new ChallengeAttempt(1L, new User(10L, "john"), 30, 40,
            isCorrect ? 1200 : 1300, isCorrect);
    }
}
