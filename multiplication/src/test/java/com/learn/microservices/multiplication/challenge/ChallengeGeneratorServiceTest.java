package com.learn.microservices.multiplication.challenge;

import com.learn.microservices.multiplication.serviceclients.ChallengeSolvedEventPublisher;
import com.learn.microservices.multiplication.serviceclients.GamificationServiceClient;
import com.learn.microservices.multiplication.user.User;
import com.learn.microservices.multiplication.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ChallengeGeneratorServiceTest {

    private ChallengeService service;

    @Spy
    private Random random;


    @Mock
    private UserRepository userRepository;

    @Mock
    private GamificationServiceClient gamificationServiceClient;

    @Mock
    private ChallengeSolvedEventPublisher challengeSolvedEventPublisher;

    @Mock
    private ChallengeAttemptRepository challengeAttemptRepository;


    @BeforeEach
    public void setUp() {
        service = new ChallengeServiceImpl(userRepository, challengeAttemptRepository, challengeSolvedEventPublisher, gamificationServiceClient);
        given(challengeAttemptRepository.save(any()))
            .will(returnsFirstArg());
    }

    @Test
    public void generatedRandomFactoriesBetweenExpectedLimits() {
        given(random.nextInt(89)).willReturn(20, 30);

        Challenge challenge = service.generateRandomChallenge();

        then(challenge.getFactorA()).isBetween(11, 100);
        then(challenge.getFactorB()).isBetween(11, 100);

    }

    @Test
    void verifyCorrectAttemptTest() {
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "oussama", 3000);

        ChallengeAttempt result = service.verifyAttempt(attemptDTO);

        then(result.isCorrect()).isTrue();

        verify(userRepository).save(new User(null, "oussama"));
        verify(challengeAttemptRepository).save(result);
    }

    @Test
    void verifyWrongAttemptTest() {
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "oussama", 300);

        ChallengeAttempt result = service.verifyAttempt(attemptDTO);

        then(result.isCorrect()).isFalse();
    }

    @Test
    void verifyExistingAttemptsTest() {

        User existingUser = new User(1L, "oussama");
        given(userRepository.findByAlias("oussama"))
            .willReturn(Optional.of(existingUser));

        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "oussama", 3000);
        ChallengeAttempt result = service.verifyAttempt(attemptDTO);

        then(result.isCorrect()).isTrue();
        then(result.getUser()).isEqualTo(existingUser);

        verify(userRepository, never()).save(any());
        verify(challengeAttemptRepository).save(result);

    }
}
