package com.github.vkolencik.foosball.player;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<PlayerDto> getPlayers(
        @RequestParam(value = "order-by", defaultValue = "wins") String orderBy,
        @RequestParam Map<String, String> queryMap) {

        PlayerOrder order;
        try {
            order = PlayerOrder.valueOf(orderBy.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Unknown order: \"%s\"", orderBy));
        }

        return playerService.getPlayers(order, queryMap.containsKey("asc"));
    }

    @GetMapping("/{nickname}")
    public PlayerDto getPlayer(@PathVariable("nickname") String nickname) {
        var player = playerService.getPlayer(nickname);
        if (player == null) {
            return throwPlayerNotFound(nickname);
        }

        return player;
    }

    @PutMapping
    public ResponseEntity createPlayer(@RequestBody CreatePlayerDto playerDto) throws ResponseStatusException {
        var nickname = playerDto.getNickname();
        if (playerService.playerExistsIncludingInactive(nickname)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Player with nickname " + nickname + "already exists");
        }

        playerService.createPlayer(nickname);

        var link = ControllerLinkBuilder.linkTo(
            ControllerLinkBuilder.methodOn(PlayerController.class).getPlayer(nickname));

        return ResponseEntity.created(link.toUri()).build();
    }

    @DeleteMapping("/{nickname}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlayer(@PathVariable("nickname") String nickname) {
        if (!playerService.playerExists(nickname)) {
            throwPlayerNotFound(nickname);
        }

        playerService.deletePlayerByNickname(nickname);
    }

    private PlayerDto throwPlayerNotFound(@PathVariable("nickname") String nickname) {
        throw new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Player with nickname \"" + nickname + "\" not found.");
    }
}
