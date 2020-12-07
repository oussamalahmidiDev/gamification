package com.learn.microservices.multiplication.challenge;

import com.learn.microservices.multiplication.serviceclients.ChallengeSolvedEventPublisher;
//import com.learn.microservices.multiplication.serviceclients.GamificationServiceClient;
import com.learn.microservices.multiplication.serviceclients.GamificationServiceClient;
import com.learn.microservices.multiplication.user.User;
import com.learn.microservices.multiplication.user.UserRepository;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@Value
@NonFinal
@Slf4j
public class ChallengeServiceImpl implements ChallengeService {

    UserRepository userRepository;
    ChallengeAttemptRepository challengeAttemptRepository;

    ChallengeSolvedEventPublisher challengeSolvedEventPublisher;
    GamificationServiceClient gamificationServiceClient;

    int MIN_FACTOR = 11;
    int MAX_FACTOR = 100;


    @Override
    public Challenge generateRandomChallenge() {
        return new Challenge(nextRandomInt(), nextRandomInt());
    }

    @Override
    @Transactional
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attempt) {
        var isCorrect = attempt.getGuess() == attempt.getFactorA() * attempt.getFactorB();
        var user = userRepository.findByAlias(attempt.getUserAlias())
            .orElseGet(() -> {
                log.info("Creating a new user with a new alias {}", attempt.getUserAlias());
                return userRepository.save(new User(null, attempt.getUserAlias()));
            });

        ChallengeAttempt savedAttempt = challengeAttemptRepository.save(new ChallengeAttempt(
            null,
            user,
            attempt.getFactorA(),
            attempt.getFactorB(),
            attempt.getGuess(),
            isCorrect
        ));

//        HttpStatus status = gameClient.sendAttempt(challengeAttempt);
        challengeSolvedEventPublisher.publishChallengeSolvedEvent(savedAttempt);
//        log.info("Gamification service response : {}", status);
        return savedAttempt;
    }

    @Override
    public List<ChallengeAttempt> getStatsForUser(String userAlias) {
        return challengeAttemptRepository.findTop10ByUserAliasOrderByIdDesc(userAlias);
    }

    private int nextRandomInt() {
        return new Random().nextInt(MAX_FACTOR - MIN_FACTOR) + MIN_FACTOR;
    }
}
