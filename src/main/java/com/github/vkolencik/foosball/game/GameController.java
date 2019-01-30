package com.github.vkolencik.foosball.game;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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

    @PutMapping
    public ResponseEntity saveGame(@RequestBody @Valid GameDto game) {
        var id = gameService.saveGame(game);

        var link = ControllerLinkBuilder.linkTo(
            ControllerLinkBuilder.methodOn(GameController.class).getGame(id));

        return ResponseEntity.created(link.toUri()).build();
    }
}
