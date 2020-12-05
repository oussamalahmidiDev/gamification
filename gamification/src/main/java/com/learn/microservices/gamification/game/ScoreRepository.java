package com.learn.microservices.gamification.game;

import com.learn.microservices.gamification.game.domain.LeaderBoardRow;
import com.learn.microservices.gamification.game.domain.ScoreCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends CrudRepository<ScoreCard, Long> {
    List<ScoreCard> findByUserIdOrderByTimestampDesc(long userId);

    @Query("SELECT SUM(s.score) FROM ScoreCard s WHERE s.userId = :userId GROUP BY s.userId")
    Optional<Integer> getTotalScoreForUser(@Param("userId") long userId);

    @Query("SELECT NEW com.learn.microservices.gamification.game.domain.LeaderBoardRow(s.userId, SUM(s.score)) " +
        "FROM ScoreCard s " +
        "GROUP BY s.userId ORDER BY SUM(s.score) DESC")
    List<LeaderBoardRow> findFirst10();
}
