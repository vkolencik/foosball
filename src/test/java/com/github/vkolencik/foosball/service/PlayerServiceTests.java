package com.github.vkolencik.foosball.service;

import com.github.vkolencik.foosball.dto.PlayerDto;
import com.github.vkolencik.foosball.entity.Player;
import com.github.vkolencik.foosball.repository.PlayerRepository;
import com.github.vkolencik.foosball.service.impl.PlayerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class PlayerServiceTests {
    @MockBean
    private PlayerRepository playerRepository;

    private PlayerService playerService;

    private Player john;

    @Before
    public void setUp() {
        this.playerService = new PlayerServiceImpl(playerRepository);
        john = new Player();
        john.setNickname("john");
        john.setWins(1);
        john.setLosses(2);
        given(playerRepository.findByNickname(john.getNickname())).willReturn(john);
    }

    @Test
    public void testFindByNickname() {
        PlayerDto foundPlayer = playerService.getPlayer(john.getNickname());
        assertThat(foundPlayer.getNickname()).isEqualTo(john.getNickname());
        assertThat(foundPlayer.getWins()).isEqualTo(john.getWins());
        assertThat(foundPlayer.getLosses()).isEqualTo(john.getLosses());
    }

    @Test
    public void testByNonexistentNickname() {
        assertThat(playerService.getPlayer("asdf")).isNull();
    }



}
