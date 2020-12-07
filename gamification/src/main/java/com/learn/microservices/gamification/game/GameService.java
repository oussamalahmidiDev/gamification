package com.learn.microservices.gamification.game;

import com.learn.microservices.gamification.challenge.ChallengeSolvedEvent;
import com.learn.microservices.gamification.game.domain.BadgeType;
import lombok.Value;

import java.util.List;

public interface GameService {

    GameResult newAttemptForUser(ChallengeSolvedEvent result);

    @Value
    class GameResult {
        int score;
        List<BadgeType> badges;
    }
}
