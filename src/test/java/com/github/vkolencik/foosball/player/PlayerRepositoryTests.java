package com.github.vkolencik.foosball.player;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/data.sql")
public class PlayerRepositoryTests {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void testOnlyActivePlayersAreReturned() {
        var activePlayers = playerRepository.findAll(Sort.by("wins").descending());
        assertThat(activePlayers)
            .extracting("active")
            .containsOnly(true);
    }

    @Test
    public void testFindByNickname() {
        var john = playerRepository.findByNickname("john");
        assertThat(john).isNotNull();
        assertThat(john.getNickname()).isEqualTo("john");
    }

    @Test
    public void testFindByNicknameReturnsNullForInactivePlayer() {
        var nonExistentPlayer = playerRepository.findByNickname("asdf");
        assertThat(nonExistentPlayer).isNull();
    }

    @Test
    public void testNicknameUnique() {
        var duplicatePlayer = new Player();
        duplicatePlayer.setNickname("john");
        assertThatThrownBy(() -> playerRepository.save(duplicatePlayer));
    }

    @Test
    public void testPlayerExistsIncludingInactive() {
        assertThat(playerRepository.playerExistsIncludingInactive("sebastian")).isEqualTo(true);
    }

    @Test
    public void testPlayerDoesNotExistIncludingInactive() {
        assertThat(playerRepository.playerExistsIncludingInactive("asdf")).isEqualTo(false);
    }
}
