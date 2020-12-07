package com.learn.microservices.gamification.game.badgeprocessors;

import com.learn.microservices.gamification.challenge.ChallengeSolvedEvent;
import com.learn.microservices.gamification.game.domain.BadgeType;
import com.learn.microservices.gamification.game.domain.ScoreCard;

import java.util.List;
import java.util.Optional;

public interface BadgeProcessor {
    Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCards, ChallengeSolvedEvent result);

    BadgeType badgeType();
}
