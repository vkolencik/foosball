package com.github.vkolencik.foosball.game;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Objects;

@SuppressWarnings("WeakerAccess")
public class GameDto {

    public GameDto() {
    }

    public GameDto(String[] teamA, String[] teamB, Team winningTeam) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.winningTeam = winningTeam;
    }

    @JsonIgnore
    private Long id;

    @Size(min = 2, max = 2, message = "Each team must have exactly two players")
    private String[] teamA;

    @Size(min = 2, max = 2, message = "Each team must have exactly two players")
    private String[] teamB;

    private Team winningTeam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[] getTeamA() {
        return teamA;
    }

    public void setTeamA(String[] teamA) {
        this.teamA = teamA;
    }

    public String[] getTeamB() {
        return teamB;
    }

    public void setTeamB(String[] teamB) {
        this.teamB = teamB;
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
        GameDto gameDto = (GameDto) o;
        return Objects.equals(getId(), gameDto.getId())
            && Arrays.equals(getTeamA(), gameDto.getTeamA())
            && Arrays.equals(getTeamB(), gameDto.getTeamB())
            && getWinningTeam() == gameDto.getWinningTeam();
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getWinningTeam());
        result = 31 * result + Arrays.hashCode(getTeamA());
        result = 31 * result + Arrays.hashCode(getTeamB());
        return result;
    }
}
