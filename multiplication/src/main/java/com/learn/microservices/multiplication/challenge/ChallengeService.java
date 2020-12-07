package com.learn.microservices.multiplication.challenge;

import java.util.List;

public interface ChallengeService {
    Challenge generateRandomChallenge();
    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attempt);
    List<ChallengeAttempt> getStatsForUser(String userAlias);
}
