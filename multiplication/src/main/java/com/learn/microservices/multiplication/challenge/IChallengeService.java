package com.learn.microservices.multiplication.challenge;

import java.util.List;

public interface IChallengeService {
    Challenge randomChallenge();
    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attempt);
    List<ChallengeAttempt> getStatsForUser(String userAlias);
}
