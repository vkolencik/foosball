package com.github.vkolencik.foosball.game;

import com.github.vkolencik.foosball.player.PlayerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)

public class GameValidatorTests {

    @MockBean
    private PlayerService playerService;

    private GameDtoValidator validator;

    private GameDto validGame = new GameDto(
        new String[]{"homer", "moe"},
        new String[]{"lenny", "carl"},
        Team.A
    );

    private Errors errors = new BeanPropertyBindingResult(validGame, "validAddress");

    @Before
    public void setUp() {
        validator = new GameDtoValidator(playerService);
        given(playerService.playerExists(anyString())).willReturn(true);
    }

    @Test
    public void testValidGame() {

        validator.validate(validGame, errors);

        assertThat(errors.hasErrors()).isFalse();
    }

    @Test
    public void testDuplicateNickname() {
        validGame.getTeamA()[0] = "x";
        validGame.getTeamA()[1] = "x";

        validator.validate(validGame, errors);

        assertThat(errors.hasErrors()).isTrue();
    }

    @Test
    public void testDuplicateNicknameAcrossTeams() {
        validGame.getTeamA()[0] = "x";
        validGame.getTeamB()[0] = "x";

        validator.validate(validGame, errors);

        assertThat(errors.hasErrors()).isTrue();
    }

    @Test
    public void testInvalidNickname() {
        given(playerService.playerExists(anyString())).will(nickname -> !nickname.getArgument(0).equals("x"));
        validGame.getTeamA()[0] = "x";

        validator.validate(validGame, errors);

        assertThat(errors.hasErrors()).isTrue();
    }

    @Test
    public void testInvalidTeamSize() {
        validGame.setTeamA(new String[] { "1", "2", "3"});

        validator.validate(validGame, errors);

        assertThat(errors.hasErrors()).isTrue();
    }
}
