package com.github.vkolencik.foosball.game;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/data.sql")
public class GameRepositoryTests {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void testInactivePlayersAppearInGames() {
        var allGames = gameRepository.findAll();
        assertThat(allGames).extracting(game -> game.getPlayerB2().getNickname()).contains("sebastian");
    }
}
