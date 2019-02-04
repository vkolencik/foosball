package com.github.vkolencik.foosball.game;

import com.github.vkolencik.foosball.player.PlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
@WithMockUser
public class GameControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private PlayerService playerService;

    private GameDto game = new GameDto(
        new String[]{"homer", "moe"},
        new String[]{"lenny", "carl"},
        Team.A
    );

    {
        game.setId(1L);
    }

    @Test
    public void testListGames() throws Exception {
        var games = Collections.singletonList(game);

        given(gameService.listAllGames()).willReturn(games);

        mockMvc.perform(get("/games"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].winningTeam", is("A")))
            .andExpect(jsonPath("$[0].teamA[0]", is("homer")))
            .andExpect(jsonPath("$[0].id").doesNotExist()); // check that ID is not returned
    }

    @Test
    public void testGetGameById() throws Exception {
        given(gameService.findGame(game.getId())).willReturn(game);

        mockMvc.perform(get("/games/" + game.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.winningTeam", is("A")));
    }

    @Test
    public void testNonexistentGameReturnsNotFound() throws Exception {
        var id = 1L;
        given(gameService.findGame(id)).willReturn(null);

        mockMvc.perform(get("/games/" + id))
            .andExpect(status().isNotFound());
    }

}
