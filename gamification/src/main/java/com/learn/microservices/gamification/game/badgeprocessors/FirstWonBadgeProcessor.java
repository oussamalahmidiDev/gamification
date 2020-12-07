package com.learn.microservices.gamification.game.badgeprocessors;

import com.learn.microservices.gamification.challenge.ChallengeSolvedEvent;
import com.learn.microservices.gamification.game.domain.BadgeType;
import com.learn.microservices.gamification.game.domain.ScoreCard;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FirstWonBadgeProcessor implements BadgeProcessor {
    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCards, ChallengeSolvedEvent result) {
        return scoreCards.size() == 1 ?
            Optional.of(BadgeType.FIRST_WON) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.FIRST_WON;
    }
}
