package com.github.vkolencik.foosball.game;

import com.github.vkolencik.foosball.player.Player;
import com.github.vkolencik.foosball.player.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;

    private PlayerRepository playerRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
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

    @Override
    @Transactional
    public long saveGame(GameDto gameDto) {
        Game game = new Game();
        game.setWinningTeam(gameDto.getWinningTeam());
        game.setPlayerA1(findPlayerAndUpdateScore(gameDto.getTeamA()[0], gameDto.getWinningTeam() == Team.A));
        game.setPlayerA2(findPlayerAndUpdateScore(gameDto.getTeamA()[1], gameDto.getWinningTeam() == Team.A));
        game.setPlayerB1(findPlayerAndUpdateScore(gameDto.getTeamB()[0], gameDto.getWinningTeam() == Team.B));
        game.setPlayerB2(findPlayerAndUpdateScore(gameDto.getTeamB()[1], gameDto.getWinningTeam() == Team.B));

        game = gameRepository.saveAndFlush(game);

        return game.getId();
    }

    private Player findPlayerAndUpdateScore(String nickname, boolean isWinner) {
        var player = playerRepository.findByNickname(nickname);
        if (isWinner) {
            player.setWins(player.getWins() + 1);
        } else {
            player.setLosses(player.getLosses() + 1);
        }

        return player;
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
