package com.github.vkolencik.foosball.service.impl;

import com.github.vkolencik.foosball.dto.PlayerDto;
import com.github.vkolencik.foosball.dto.PlayerOrder;
import com.github.vkolencik.foosball.entity.Player;
import com.github.vkolencik.foosball.repository.PlayerRepository;
import com.github.vkolencik.foosball.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public PlayerDto getPlayer(String nickname) {
        var player = playerRepository.findByNickname(nickname);
        return player != null ? mapToDto(player) : null;
    }

    @Override
    public List<PlayerDto> getPlayers(PlayerOrder sortProperty, Boolean ascending) {

        Sort sort = Sort.by(
            ascending
                ? Sort.Order.asc(sortProperty.getPropertyName())
                : Sort.Order.desc(sortProperty.getPropertyName()));
        List<Player> players = playerRepository.findAll(sort);

        return players.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public boolean playerExists(String nickname) {
        return playerRepository.findByNickname(nickname) != null;
    }

    @Override
    public void deletePlayerByNickname(String nickname) {
        var player = playerRepository.findByNickname(nickname);
        player.setActive(false);
    }

    private PlayerDto mapToDto(Player player) {
        return new PlayerDto(player.getNickname(), player.getWins(), player.getLosses());
    }
}
