package com.learn.microservices.gamification.game;

import com.learn.microservices.gamification.challenge.ChallengeSolvedEvent;
import com.learn.microservices.gamification.game.domain.BadgeType;
import lombok.Value;

import java.util.List;

public interface IGameService {

    GameResult newAttemptForUser(ChallengeSolvedEvent challengeSolvedDTO);

    @Value
    class GameResult {
        int score;
        List<BadgeType> badges;
    }
}
