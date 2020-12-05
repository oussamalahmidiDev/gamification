package com.learn.microservices.gamification.game;

import com.learn.microservices.gamification.challenge.ChallengeSolvedEvent;
import com.learn.microservices.gamification.game.badgeprocessors.BadgeProcessor;
import com.learn.microservices.gamification.game.domain.BadgeCard;
import com.learn.microservices.gamification.game.domain.BadgeType;
import com.learn.microservices.gamification.game.domain.ScoreCard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameService implements IGameService {

    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;
    private final List<BadgeProcessor> badgeProcessors;

    @Override
    public GameResult newAttemptForUser(final ChallengeSolvedEvent challenge) {
        if (challenge.isCorrect()) {
            ScoreCard scoreCard = ScoreCard.builder()
                .userId(challenge.getUserId())
                .score(10)
                .attemptId(challenge.getAttemptId())
                .build();
            scoreRepository.save(scoreCard);
            log.info("User {} scored {} for attempt {}",
                challenge.getUserAlias(),
                scoreCard.getScore(),
                challenge.getAttemptId());
            List<BadgeCard> badgeCards = processBadges(challenge);

            return new GameResult(
                scoreCard.getScore(),
                badgeCards.stream().map(BadgeCard::getBadgeType).collect(Collectors.toList())
            );
        } else {
            log.info("Attempt id {} is not correct. " +
                    "User {} does not get score.", challenge.getAttemptId(),
                challenge.getUserAlias());
            return new GameResult(0, Collections.emptyList());
        }
    }

    private List<BadgeCard> processBadges(ChallengeSolvedEvent challenge) {
        Optional<Integer> totalScoreOptional = scoreRepository.getTotalScoreForUser(challenge.getUserId());
        if (totalScoreOptional.isPresent())
            return Collections.emptyList();
        int totalScore = totalScoreOptional.get();

        List<ScoreCard> scoreCards = scoreRepository
            .findByUserIdOrderByTimestampDesc(challenge.getUserId());

        Set<BadgeType> badges = badgeRepository
            .findByUserIdOrderByBadgeTimestampDesc(challenge.getUserId())
            .stream()
            .map(BadgeCard::getBadgeType).collect(Collectors.toSet());

        List<BadgeCard> newBadgeCards = badgeProcessors.stream()
            .filter(bp -> !badges.contains(bp.badgeType()))
            .map(bp -> bp.processForOptionalBadge(totalScore, scoreCards, challenge))
            .flatMap(List::stream)
            .map((badgeType) ->  BadgeCard.builder().userId(challenge.getUserId()).badgeType(badgeType).build())
            .collect(Collectors.toList());

        badgeRepository.saveAll(newBadgeCards);

        return newBadgeCards;
    }
}
