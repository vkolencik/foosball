package com.github.vkolencik.foosball.player;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class PlayerServiceTests {
    @MockBean
    private PlayerRepository playerRepository;

    @Captor
    private ArgumentCaptor<Player> playerCaptor;

    private PlayerService playerService;

    private Player john;

    @Before
    public void setUp() {
        this.playerService = new PlayerServiceImpl(playerRepository);
        john = new Player();
        john.setNickname("john");
        john.setWins(1);
        john.setLosses(2);
        john.setActive(true);
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
    public void testFindByNonexistentNickname() {
        assertThat(playerService.getPlayer("asdf")).isNull();
    }

    @Test
    public void testPlayerExists() {
        assertThat(playerService.playerExists(john.getNickname())).isEqualTo(true);
    }

    @Test
    public void testPlayerDoesNotExist() {
        assertThat(playerService.playerExists("asdf")).isEqualTo(false);
    }

    @Test
    public void testDelete() {
        playerService.deletePlayerByNickname(john.getNickname());
        assertThat(john.isActive()).isEqualTo(false);
    }

    @Test
    public void testCreate() {
        playerService.createPlayer(john.getNickname());
        verify(playerRepository).save(playerCaptor.capture());
        assertThat(playerCaptor.getValue().getNickname()).isEqualTo(john.getNickname());
    }
}
