package com.learn.microservices.gamification.game.badgeprocessors;

import com.learn.microservices.gamification.challenge.ChallengeSolvedEvent;
import com.learn.microservices.gamification.game.domain.BadgeType;
import com.learn.microservices.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class BronzeBadgeProcessor implements BadgeProcessor {

    @Override
    public List<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCards, ChallengeSolvedEvent challenge) {
        return currentScore > 50 ? Collections.singletonList(BadgeType.BRONZE) : Collections.emptyList();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.BRONZE;
    }
}
