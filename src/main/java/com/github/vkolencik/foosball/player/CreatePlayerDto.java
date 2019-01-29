package com.github.vkolencik.foosball.player;

public class CreatePlayerDto {

    public CreatePlayerDto() {
    }

    public CreatePlayerDto(String nickname) {
        this.nickname = nickname;
    }

    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
