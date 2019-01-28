package com.github.vkolencik.foosball.game;

import com.github.vkolencik.foosball.player.Player;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@SuppressWarnings("ALL")
@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_a_1")
    private Player playerA1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_a_2")
    private Player playerA2;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_b_1")
    private Player playerB1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_b_2")
    private Player playerB2;

    @Enumerated(EnumType.STRING)
    private Team winningTeam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayerA1() {
        return playerA1;
    }

    public void setPlayerA1(Player playerA1) {
        this.playerA1 = playerA1;
    }

    public Player getPlayerA2() {
        return playerA2;
    }

    public void setPlayerA2(Player playerA2) {
        this.playerA2 = playerA2;
    }

    public Player getPlayerB1() {
        return playerB1;
    }

    public void setPlayerB1(Player playerB1) {
        this.playerB1 = playerB1;
    }

    public Player getPlayerB2() {
        return playerB2;
    }

    public void setPlayerB2(Player playerB2) {
        this.playerB2 = playerB2;
    }

    public Team getWinningTeam() {
        return winningTeam;
    }

    public void setWinningTeam(Team winningTeam) {
        this.winningTeam = winningTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(getId(), game.getId())
            && Objects.equals(getPlayerA1(), game.getPlayerA1())
            && Objects.equals(getPlayerA2(), game.getPlayerA2())
            && Objects.equals(getPlayerB1(), game.getPlayerB1())
            && Objects.equals(getPlayerB2(), game.getPlayerB2())
            && getWinningTeam() == game.getWinningTeam();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPlayerA1(), getPlayerA2(), getPlayerB1(), getPlayerB2(), getWinningTeam());
    }
}
