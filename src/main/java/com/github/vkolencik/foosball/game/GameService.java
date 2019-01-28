package com.github.vkolencik.foosball.game;

import java.util.List;

public interface GameService {

    /**
     * Create new game
     * @param gameDto Game data
     * @return ID of the newly created game
     */
    Long createGame(GameDto gameDto);

    /**
     * Returns the list of all games
     * @return list of all games
     */
    List<GameDto> listAllGames();

    GameDto findGame(long gameId);
}
