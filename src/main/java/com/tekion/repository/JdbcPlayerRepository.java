package com.tekion.repository;

import com.tekion.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.tekion.configs.Config;


@Repository
public class JdbcPlayerRepository implements PlayerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Config config;

    @Override
    public Player findById(int id) {
        try {
            Player player = this.jdbcTemplate.queryForObject("SELECT * FROM players WHERE id=?",
                    BeanPropertyRowMapper.newInstance(Player.class), id);

            return player;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public Player findByName(String name) {
        return null;
    }

    @Override
    public Player PlayerInfoByMatchId(int matchId, int id) {
        try {
            System.out.println(matchId + " " + id);
            Player player = this.jdbcTemplate.queryForObject("SELECT player_name as name, scoreboard.* FROM scoreboard WHERE match_id = ? and player_id = ? ",
                    BeanPropertyRowMapper.newInstance(Player.class), matchId, id);
            System.out.println(player.getId());
            return player;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }


}




