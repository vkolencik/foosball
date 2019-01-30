package com.github.vkolencik.foosball.game;

import com.github.vkolencik.foosball.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    private GameService gameService;
    private PlayerService playerService;

    @Autowired
    public GameController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
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
    public ResponseEntity saveGame(@RequestBody GameDto game) {
        validateGame(game);

        var id = gameService.saveGame(game);

        var link = ControllerLinkBuilder.linkTo(
            ControllerLinkBuilder.methodOn(GameController.class).getGame(id));

        return ResponseEntity.created(link.toUri()).build();
    }

    private void validateGame(@RequestBody GameDto game) {
        validateTeam(game.getTeamA());
        validateTeam(game.getTeamB());

        var allNicknames = new HashSet<String>();
        allNicknames.addAll(Arrays.asList(game.getTeamA()));
        allNicknames.addAll(Arrays.asList(game.getTeamB()));
        if (allNicknames.size() != 4) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "All players must be different from each other");
        }
    }

    private void validateTeam(String[] team) {
        if (team == null || team.length != 2) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Each team must have exactly two players");
        }

        Arrays.stream(team).forEach(this::validatePlayer);
    }

    private void validatePlayer(String nickname) {
        if (!playerService.playerExists(nickname)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Player with nickname " + nickname + " not found");
        }
    }
}
