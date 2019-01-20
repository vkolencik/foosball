package com.github.vkolencik.foosball.repository;

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
public class PlayerRepositoryTests {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void testOnlyActivePlayersAreReturned() {
        var activePlayers = playerRepository.findAll();
        assertThat(activePlayers)
            .extracting("nickname")
            .containsOnly("john", "peter");
    }

    @Test
    public void testFindByNickname() {
        var john = playerRepository.findByNickname("john");
        assertThat(john).isNotNull();
        assertThat(john.getNickname()).isEqualTo("john");
    }
}
