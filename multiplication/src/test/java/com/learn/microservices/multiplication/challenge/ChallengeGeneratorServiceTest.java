package com.learn.microservices.multiplication.challenge;

//import com.learn.microservices.multiplication.serviceclients.GamificationServiceClient;
import com.learn.microservices.multiplication.user.User;
import com.learn.microservices.multiplication.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChallengeGeneratorServiceTest {

    private IChallengeService service;

//    @Spy
    private Random random;


//    @Mock
    private UserRepository userRepository;

//    @Mock
//    private GamificationServiceClient gamificationServiceClient;

//    @Mock
    private ChallengeAttemptRepository challengeAttemptRepository;


//    @BeforeEach
    public void setUp() {
//        service = new ChallengeService(userRepository, challengeAttemptRepository, gamificationServiceClient);
//        given(challengeAttemptRepository.save(any()))
//            .will(returnsFirstArg());
    }

//    @Test
    public void generatedRandomFactoriesBetweenExpectedLimits() {
        given(random.nextInt(89)).willReturn(20, 30);

        Challenge challenge = service.randomChallenge();

        then(challenge).isEqualTo(new Challenge(31, 41));
    }

//    @Test
    void verifyCorrectAttemptTest() {
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "oussama", 3000);

        ChallengeAttempt result = service.verifyAttempt(attemptDTO);

        then(result.isCorrect()).isTrue();

        verify(userRepository).save(new User(null, "oussama"));
        verify(challengeAttemptRepository).save(result);
    }

//    @Test
    void verifyWrongAttemptTest() {
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "oussama", 300);

        ChallengeAttempt result = service.verifyAttempt(attemptDTO);

        then(result.isCorrect()).isFalse();
    }

//    @Test
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
