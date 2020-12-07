package com.learn.microservices.gamification.game;

import com.learn.microservices.gamification.game.domain.LeaderBoardRow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaders")
@RequiredArgsConstructor
@Slf4j
class LeaderBoardController {
    private final LeaderBoardServiceImpl leaderBoardService;

    @GetMapping
    public List<LeaderBoardRow> getLeaderBoard() {
        log.info("Retrieving leaderboard from different new port");
        return leaderBoardService.getCurrentLeaderBoard();
    }
}
