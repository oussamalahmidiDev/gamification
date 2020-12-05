package com.learn.microservices.gamification.game.badgeprocessors;

import com.learn.microservices.gamification.challenge.ChallengeSolvedEvent;
import com.learn.microservices.gamification.game.domain.BadgeType;
import com.learn.microservices.gamification.game.domain.ScoreCard;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FirstWonBadgeProcessor implements BadgeProcessor {
    @Override
    public List<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCards, ChallengeSolvedEvent challenge) {
        return scoreCards.size() == 1 ?
            Collections.singletonList(BadgeType.FIRST_WON) : Collections.emptyList();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.FIRST_WON;
    }
}
