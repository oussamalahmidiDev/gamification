package com.learn.microservices.multiplication.challenge;

import com.learn.microservices.multiplication.serviceclients.ChallengeSolvedEventPublisher;
//import com.learn.microservices.multiplication.serviceclients.GamificationServiceClient;
import com.learn.microservices.multiplication.user.User;
import com.learn.microservices.multiplication.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChallengeService implements IChallengeService {

    private final UserRepository userRepository;
    private final ChallengeAttemptRepository challengeAttemptRepository;

//    private final GamificationServiceClient gameClient;
    private final ChallengeSolvedEventPublisher challengeSolvedEventPublisher;

    private final int MIN_FACTOR = 11;
    private final int MAX_FACTOR = 100;


    @Override
    public Challenge randomChallenge() {
        return new Challenge(next(), next());
    }

    @Override
    @Transactional
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attempt) {
        boolean isCorrect = attempt.getGuess() == attempt.getFactorA() * attempt.getFactorB();
        User user = userRepository.findByAlias(attempt.getUserAlias())
            .orElseGet(() -> {
                log.info("Creating a new user with a new alias {}", attempt.getUserAlias());
                return userRepository.save(new User(null, attempt.getUserAlias()));
            });

        ChallengeAttempt challengeAttempt = challengeAttemptRepository.save(new ChallengeAttempt(
            null,
            user,
            attempt.getFactorA(),
            attempt.getFactorB(),
            attempt.getGuess(),
            isCorrect
        ));

//        HttpStatus status = gameClient.sendAttempt(challengeAttempt);
        challengeSolvedEventPublisher.publish(challengeAttempt);
//        log.info("Gamification service response : {}", status);
        return challengeAttempt;
    }

    @Override
    public List<ChallengeAttempt> getStatsForUser(String userAlias) {
        return challengeAttemptRepository.findTop10ByUserAliasOrderByIdDesc(userAlias);
    }

    private int next() {
        return new Random().nextInt(MAX_FACTOR - MIN_FACTOR) + MIN_FACTOR;
    }
}
