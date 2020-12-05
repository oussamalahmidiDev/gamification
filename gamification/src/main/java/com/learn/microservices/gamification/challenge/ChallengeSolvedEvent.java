package com.learn.microservices.gamification.challenge;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;

@Data
@ToString
@NoArgsConstructor
public class ChallengeSolvedEvent {
    private long attemptId;
    private boolean correct;
    private int factorA;
    private int factorB;
    private long userId;
    private String userAlias;
}
