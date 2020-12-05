package com.learn.microservices.gamification.game.domain;

import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

//@Value
@AllArgsConstructor
//@Builder
@Getter
public class LeaderBoardRow {

    Long userId;
    Long totalScore;

    public LeaderBoardRow(Long userId, Long totalScore) {
        this.userId = userId;
        this.totalScore = totalScore;
        badges = Collections.emptyList();
    }


    @With
    List<String> badges;


}
