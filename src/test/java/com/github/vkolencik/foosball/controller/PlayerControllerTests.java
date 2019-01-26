package com.github.vkolencik.foosball.controller;

import com.github.vkolencik.foosball.dto.PlayerDto;
import com.github.vkolencik.foosball.dto.PlayerOrder;
import com.github.vkolencik.foosball.service.PlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PlayerController.class)
public class PlayerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    private static PlayerDto john = new PlayerDto("john", 1, 2);
    private static PlayerDto victor = new PlayerDto("victor", 10, 0);


    @Test
    public void testGetPlayerByNickname() throws Exception {

        given(playerService.getPlayer("john")).willReturn(john);

        mockMvc.perform(get("/players/john").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nickname", is(john.getNickname())))
            .andExpect(jsonPath("$.wins", is(john.getWins())))
            .andExpect(jsonPath("$.losses", is(john.getLosses())));
    }

    @Test
    public void testGetPlayers() throws Exception {

        given(playerService.getPlayers(PlayerOrder.WINS, false))
            .willReturn(Arrays.asList(victor, john));

        mockMvc.perform(get("/players").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].nickname", is(victor.getNickname())));
    }

    @Test
    public void testGetPlayersAsc() throws Exception {

        given(playerService.getPlayers(PlayerOrder.WINS, true))
            .willReturn(Arrays.asList(john, victor));

        mockMvc.perform(get("/players?asc").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].nickname", is(john.getNickname())));
    }

    @Test
    public void testGetPlayersByLosses() throws Exception {

        given(playerService.getPlayers(PlayerOrder.LOSSES, false))
            .willReturn(Arrays.asList(john, victor));

        mockMvc.perform(get("/players?order-by=losses").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].nickname", is(john.getNickname())));
    }

    @Test
    public void testDeletePlayerReturnsNoContent() throws Exception {
        var nickname = "john";
        given(playerService.playerExists(nickname)).willReturn(true);

        mockMvc.perform(delete("/players/" + nickname).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(playerService, times(1)).deletePlayerByNickname(nickname);
    }

    @Test
    public void testDeleteNonexistentPlayerReturnsNotFound() throws Exception {
        var nickname = "john";
        given(playerService.playerExists(nickname)).willReturn(false);

        mockMvc.perform(delete("/players/" + nickname).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(playerService, never()).deletePlayerByNickname(nickname);
    }
}
