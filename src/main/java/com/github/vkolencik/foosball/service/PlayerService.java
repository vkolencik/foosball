package com.github.vkolencik.foosball.service;

import com.github.vkolencik.foosball.dto.PlayerSort;
import com.github.vkolencik.foosball.dto.PlayerDto;

import java.util.List;

public interface PlayerService {

    PlayerDto getPlayer(String nickname);

    List<PlayerDto> getPlayers(PlayerSort sort, Boolean ascending);
}