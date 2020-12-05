package com.learn.microservices.gamification.game.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreCard {

    private final static int DEFAULT_SCORE = 10;


    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Long attemptId;
    private Long timestamp;
    private int score;


}
