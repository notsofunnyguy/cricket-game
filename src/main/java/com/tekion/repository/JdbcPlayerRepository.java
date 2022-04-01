package com.tekion.repository;

import com.tekion.configs.Config;
import com.tekion.controllers.GameController;
import com.tekion.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class JdbcPlayerRepository implements PlayerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Config config;

    /*

    this method here is used for fetching the
    stats of a player by its id.
     */
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

    /*

    this method here is used for fetching the
    stats of a player in a match by match id
    and player id.
     */
    @Override
    public Player PlayerInfoByMatchId(int matchId, int id) {
        Player player = this.jdbcTemplate.queryForObject("SELECT player_name as name, scoreboard.* FROM scoreboard WHERE match_id = ? and player_id = ? ",
                BeanPropertyRowMapper.newInstance(Player.class), matchId, id);
        return player;
    }

    @Override
    public List<Player> getAllPlayersOfTeam(int matchId, int teamId){
        List<Player> players = this.jdbcTemplate.query("SELECT player_name as name, scoreboard.* FROM scoreboard WHERE match_id = ? and team_id=?",
                BeanPropertyRowMapper.newInstance(Player.class), matchId, teamId);
//        System.out.println("recahed");
        return players;
    }

}




