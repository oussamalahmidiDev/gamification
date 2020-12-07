package com.learn.microservices.gamification.game;

import com.learn.microservices.gamification.challenge.ChallengeSolvedEvent;
import com.learn.microservices.gamification.game.badgeprocessors.BadgeProcessor;
import com.learn.microservices.gamification.game.domain.BadgeCard;
import com.learn.microservices.gamification.game.domain.BadgeType;
import com.learn.microservices.gamification.game.domain.ScoreCard;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Value
public class GameServiceImpl implements GameService {

    ScoreRepository scoreRepository;
    BadgeRepository badgeRepository;
    List<BadgeProcessor> badgeProcessors;

    int DEFAULT_SCORE = 10;

    @Override
    public GameResult newAttemptForUser(final ChallengeSolvedEvent result) {
        if (result.isCorrect()) {
            ScoreCard scoreCard = ScoreCard.builder()
                .userId(result.getUserId())
                .score(DEFAULT_SCORE)
                .attemptId(result.getAttemptId())
                .build();
            scoreRepository.save(scoreCard);
            log.info("User {} scored {} for attempt {}",
                result.getUserAlias(),
                scoreCard.getScore(),
                result.getAttemptId());
            List<BadgeCard> badgeCards = processBadges(result);

            return new GameResult(
                scoreCard.getScore(),
                badgeCards.stream().map(BadgeCard::getBadgeType).collect(Collectors.toList())
            );
        } else {
            log.info("Attempt id {} is not correct. " +
                    "User {} does not get score.", result.getAttemptId(),
                result.getUserAlias());
            return new GameResult(0, Collections.emptyList());
        }
    }

    private List<BadgeCard> processBadges(ChallengeSolvedEvent result) {
        Optional<Integer> optionalTotalScore = scoreRepository.getTotalScoreForUser(result.getUserId());
        if (optionalTotalScore.isEmpty())
            return Collections.emptyList();
        int totalScore = optionalTotalScore.get();

        List<ScoreCard> scoreCards = scoreRepository
            .findByUserIdOrderByTimestampDesc(result.getUserId());

        Set<BadgeType> badges = badgeRepository
            .findByUserIdOrderByBadgeTimestampDesc(result.getUserId())
            .stream()
            .map(BadgeCard::getBadgeType).collect(Collectors.toSet());

        List<BadgeCard> newBadgeCards = badgeProcessors.stream()
            .filter(badgeProcessor -> !badges.contains(badgeProcessor.badgeType()))
            .map(badgeProcessor -> badgeProcessor.processForOptionalBadge(totalScore, scoreCards, result))
            .flatMap(Optional::stream)
            .map(badgeType ->  BadgeCard.builder().userId(result.getUserId()).badgeType(badgeType).build())
            .collect(Collectors.toList());

        badgeRepository.saveAll(newBadgeCards);

        return newBadgeCards;
    }
}
