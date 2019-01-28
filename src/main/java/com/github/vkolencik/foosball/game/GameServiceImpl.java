package com.github.vkolencik.foosball.game;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Long createGame(GameDto gameDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<GameDto> listAllGames() {
        return gameRepository.findAll(Sort.by("id")).stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    @Override
    public GameDto findGame(long gameId) {
        return gameRepository.findById(gameId).map(this::mapToDto).orElse(null);
    }

    private GameDto mapToDto(Game game) {
        var dto = new GameDto();
        dto.setId(game.getId());
        dto.setTeamA(new String[] { game.getPlayerA1().getNickname(), game.getPlayerA2().getNickname() });
        dto.setTeamB(new String[] { game.getPlayerB1().getNickname(), game.getPlayerB2().getNickname() });
        dto.setWinningTeam(game.getWinningTeam());

        return dto;
    }
}
