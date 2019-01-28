package com.github.vkolencik.foosball.player;

import org.hibernate.annotations.Where;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Where(clause = "active = true")
    Player findByNickname(String nickname);

    @Override
    @Query("select p from Player p where p.active = true")
    List<Player> findAll(Sort sort);

    @Query("select count(p) > 0 from Player p where p.nickname = :nickname")
    boolean playerExistsIncludingInactive(@Param("nickname") String nickname);
}
