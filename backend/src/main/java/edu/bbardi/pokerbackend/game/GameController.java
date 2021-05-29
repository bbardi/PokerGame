package edu.bbardi.pokerbackend.game;

import edu.bbardi.pokerbackend.game.model.dto.GameDTO;
import edu.bbardi.pokerbackend.game.service.GameService;
import edu.bbardi.pokerbackend.game.service.RoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import static edu.bbardi.pokerbackend.UrlMapping.*;

@Controller
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final RoundService roundService;

    @MessageMapping(READY)
    public void markReady(GameDTO gameDTO){
        gameService.setReady(gameDTO);
    }
    @MessageMapping(FOLD)
    public void fold(GameDTO gameDTO){
        roundService.fold(gameDTO);
    }
    @MessageMapping(CALL)
    public void call(GameDTO gameDTO){
        roundService.call(gameDTO);
    }
    @MessageMapping(CHECK)
    public void check(GameDTO gameDTO){
        roundService.check(gameDTO);
    }
    @MessageMapping(RAISE)
    public void raise(GameDTO gameDTO){
        roundService.raise(gameDTO);
    }
}
