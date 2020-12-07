package com.learn.microservices.gamification.game.badgeprocessors;

import com.learn.microservices.gamification.challenge.ChallengeSolvedEvent;
import com.learn.microservices.gamification.game.domain.BadgeType;
import com.learn.microservices.gamification.game.domain.ScoreCard;

import java.util.List;
import java.util.Optional;

public class BronzeBadgeProcessor implements BadgeProcessor {

    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCards, ChallengeSolvedEvent result) {
        return currentScore >= 50 ? Optional.of(BadgeType.BRONZE) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.BRONZE;
    }
}
