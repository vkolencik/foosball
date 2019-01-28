package com.github.vkolencik.foosball.player;

@SuppressWarnings("WeakerAccess")
public class PlayerDto {

    private String nickname;

    private int wins;

    private int losses;

    public PlayerDto(String nickname, int wins, int losses) {
        this.nickname = nickname;
        this.wins = wins;
        this.losses = losses;
    }

    public String getNickname() {
        return nickname;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }
}
