package com.github.vkolencik.foosball.player;

import java.util.List;

public interface PlayerService {

    PlayerDto getPlayer(String nickname);

    List<PlayerDto> getPlayers(PlayerOrder sort, Boolean ascending);

    void deletePlayerByNickname(String nickname);

    boolean playerExists(String nickname);

    boolean playerExistsIncludingInactive(String nickname);

    void createPlayer(String nickname);
}
