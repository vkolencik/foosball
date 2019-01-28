package com.github.vkolencik.foosball.service;

import com.github.vkolencik.foosball.dto.PlayerDto;
import com.github.vkolencik.foosball.dto.PlayerOrder;

import java.util.List;

public interface PlayerService {

    PlayerDto getPlayer(String nickname);

    List<PlayerDto> getPlayers(PlayerOrder sort, Boolean ascending);

    void deletePlayerByNickname(String nickname);

    boolean playerExists(String nickname);

    boolean playerExistsIncludingInactive(String nickname);

    void createPlayer(String nickname);
}
