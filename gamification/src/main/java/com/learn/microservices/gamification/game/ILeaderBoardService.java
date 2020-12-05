package com.learn.microservices.gamification.game;

import com.learn.microservices.gamification.game.domain.LeaderBoardRow;

import java.util.List;

public interface ILeaderBoardService {
    List<LeaderBoardRow> getCurrentLeaderBoard();
}
