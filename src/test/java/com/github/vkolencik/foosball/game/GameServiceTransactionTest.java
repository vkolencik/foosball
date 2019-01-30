package com.github.vkolencik.foosball.game;


import com.github.vkolencik.foosball.FoosballApplication;
import com.github.vkolencik.foosball.player.PlayerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(
    classes = FoosballApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@RunWith(SpringRunner.class)
@Sql("/data.sql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GameServiceTransactionTest {

    @Autowired
    private PlayerRepository playerRepository;

    @MockBean // can't use @SpyBean due to https://github.com/spring-projects/spring-boot/issues/7033
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;

    @Before
    public void setUp() {
        Game savedGame = new Game();
        savedGame.setId(1L);
        given(gameRepository.saveAndFlush(any(Game.class))).willReturn(savedGame);
    }

    @Test
    public void testGameSaveFailureDoesNotChangePlayerScores() {
        given(gameRepository.saveAndFlush(any(Game.class))).willThrow(new RuntimeException("Test exception"));

        var winsBefore = playerRepository.findByNickname("john").getWins();
        var lossesBefore = playerRepository.findByNickname("john").getLosses();

        try {
            gameService.saveGame(new GameDto(
                new String[] { "john", "peter" },
                new String[] { "homer", "moe" },
                Team.A));
        } catch (RuntimeException e) {
            // expected
        }

        var winsAfter = playerRepository.findByNickname("john").getWins();
        var lossesAfter = playerRepository.findByNickname("john").getLosses();

        assertThat(winsBefore).isEqualTo(winsAfter);
        assertThat(lossesBefore).isEqualTo(lossesAfter);
    }

    @Test
    public void testGameSaveUpdatesScores() {

        var johnWinsBefore = playerRepository.findByNickname("john").getWins();
        var johnLossesBefore = playerRepository.findByNickname("john").getLosses();
        var homerWinsBefore = playerRepository.findByNickname("homer").getWins();
        var homerLossesBefore = playerRepository.findByNickname("homer").getLosses();

        gameService.saveGame(new GameDto(
            new String[] { "john", "peter" },
            new String[] { "homer", "moe" },
            Team.A));

        var johnWinsAfter = playerRepository.findByNickname("john").getWins();
        var johnLossesAfter = playerRepository.findByNickname("john").getLosses();
        var homerWinsAfter = playerRepository.findByNickname("homer").getWins();
        var homerLossesAfter = playerRepository.findByNickname("homer").getLosses();

        assertThat(johnWinsAfter).isEqualTo(johnWinsBefore + 1);
        assertThat(johnLossesAfter).isEqualTo(johnLossesBefore);
        assertThat(homerWinsAfter).isEqualTo(homerWinsBefore);
        assertThat(homerLossesAfter).isEqualTo(homerLossesBefore + 1);
    }
}
