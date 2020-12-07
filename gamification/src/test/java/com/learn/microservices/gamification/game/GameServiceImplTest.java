package com.learn.microservices.gamification.game;

import com.learn.microservices.gamification.challenge.ChallengeSolvedEvent;
import com.learn.microservices.gamification.game.badgeprocessors.BadgeProcessor;
import com.learn.microservices.gamification.game.badgeprocessors.BronzeBadgeProcessor;
import com.learn.microservices.gamification.game.badgeprocessors.FirstWonBadgeProcessor;
import com.learn.microservices.gamification.game.domain.BadgeCard;
import com.learn.microservices.gamification.game.domain.BadgeType;
import com.learn.microservices.gamification.game.domain.ScoreCard;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
//import org.so
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL_INT;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL_LONG;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GameServiceImplTest {

    @InjectMocks
    private GameServiceImpl gameService;

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private BadgeRepository badgeRepository;

    EasyRandom generator = new EasyRandom();



    @BeforeEach
    public void setup() {
        List<BadgeProcessor> badgeProcessors = List.of(
            generator.nextObject(BronzeBadgeProcessor.class),
            generator.nextObject(FirstWonBadgeProcessor.class)
        );

        gameService = new GameServiceImpl(scoreRepository, badgeRepository, badgeProcessors);

        given(scoreRepository.save(any()))
            .willReturn(generator.nextObject(ScoreCard.class));

        given(scoreRepository.getTotalScoreForUser(anyLong()))
            .willReturn(Optional.of(generator.nextInt()));

        given(scoreRepository.findByUserIdOrderByTimestampDesc(anyLong()))
            .willReturn(generator.objects(ScoreCard.class, 5).collect(Collectors.toList()));

    }

    @Test
    void userGets10PointsWhenAnswerIsCorrectTest() {
        ChallengeSolvedEvent result = new ChallengeSolvedEvent(
            generator.nextLong(),
            true,
            generator.nextInt(),
            generator.nextInt(),
            generator.nextLong(),
            generator.nextObject(String.class)
        );

        then(gameService.newAttemptForUser(result).getScore())
            .isNotZero();

    }

    @Test
    void userDoesntGets10PointsWhenAnswerIsCorrectTest() {
        ChallengeSolvedEvent result = new ChallengeSolvedEvent(
            generator.nextLong(),
            false,
            generator.nextInt(),
            generator.nextInt(),
            generator.nextLong(),
            generator.nextObject(String.class)
        );

        then(gameService.newAttemptForUser(result).getScore())
            .isZero();

    }

    @Test
    void userGetsBronzeBadgeWhenScoreIs50Test() {
        ChallengeSolvedEvent result = generator.nextObject(ChallengeSolvedEvent.class);

        given(scoreRepository.getTotalScoreForUser(result.getUserId()))
            .willReturn(Optional.of(50));

        given(badgeRepository.findByUserIdOrderByBadgeTimestampDesc(result.getUserId()))
            .willReturn(Collections.emptyList());

        then(gameService.newAttemptForUser(result).getBadges())
            .containsOnly(BadgeType.BRONZE);
    }
}
