package com.github.vkolencik.foosball.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findByNickname(String nickname);

    @Query(value =
        "SELECT CASE WHEN (SELECT COUNT(1) FROM players p WHERE p.nickname = :nickname) > 0 THEN TRUE ELSE FALSE END",
        nativeQuery = true)
    boolean playerExistsIncludingInactive(@Param("nickname") String nickname);

}
