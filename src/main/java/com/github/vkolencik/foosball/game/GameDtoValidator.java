package com.github.vkolencik.foosball.game;


import com.github.vkolencik.foosball.player.PlayerService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class GameDtoValidator implements Validator {

    private PlayerService playerService;

    public GameDtoValidator(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return GameDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var game = (GameDto) target;

        var nicknames = new HashSet<String>();
        nicknames.addAll(Arrays.asList(game.getTeamA()));
        nicknames.addAll(Arrays.asList(game.getTeamB()));

        if (nicknames.size() != 4) {
            errors.reject("All players must be different from each other");
        }

        var badNicknames = nicknames.stream()
            .filter(nickname -> !playerService.playerExists(nickname))
            .collect(Collectors.toList());
        if (badNicknames.size() > 0) {
            errors.reject("Some players do not exist: " + String.join(", ", badNicknames));
        }
    }
}
