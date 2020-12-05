package com.learn.microservices.gamification.game;

import com.learn.microservices.gamification.challenge.ChallengeSolvedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attempts")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    void postResult(@RequestBody ChallengeSolvedEvent dto) {
        gameService.newAttemptForUser(dto);
    }

}
