package com.github.vkolencik.foosball.controller;

import com.github.vkolencik.foosball.dto.PlayerDto;
import com.github.vkolencik.foosball.dto.PlayerOrder;
import com.github.vkolencik.foosball.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Player with nickname \"" + nickname + "\" not found.");
        }

        return player;
    }
}
