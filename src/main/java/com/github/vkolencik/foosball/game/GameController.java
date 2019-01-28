package com.github.vkolencik.foosball.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<GameDto> listAllGames() {
        return gameService.listAllGames();
    }

    @GetMapping("/{gameId}")
    public GameDto getGame(@PathVariable("gameId") long gameId) {
        var game =  gameService.findGame(gameId);

        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game with ID " + gameId + " not found");
        }

        return game;
    }
}
