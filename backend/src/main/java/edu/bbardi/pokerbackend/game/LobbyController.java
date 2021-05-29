package edu.bbardi.pokerbackend.game;

import edu.bbardi.pokerbackend.game.model.dto.LobbyDTO;
import edu.bbardi.pokerbackend.game.service.LobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.bbardi.pokerbackend.UrlMapping.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(LOBBY)
@CrossOrigin(originPatterns = "*",maxAge = 3600)
public class LobbyController {
    private final LobbyService lobbyService;

    @PostMapping
    public LobbyDTO createLobby(@RequestBody LobbyDTO lobby){
        return lobbyService.create(lobby);
    }

    @GetMapping
    public List<LobbyDTO> findAll(){
        return lobbyService.findAll();
    }

    @PostMapping(ENTITY + USER_ENTITY)
    public LobbyDTO joinLobby(@PathVariable Long id, @PathVariable Long userID){
        return lobbyService.join(id,userID);
    }
}
