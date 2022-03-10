package com.tekion.repository;

import com.tekion.CricketGame;
import com.tekion.configs.Config;
import com.tekion.models.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcMatchRepository implements  MatchRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Config config;

    @Override
    public Match findById(int id) {
        try {
            Match match = this.jdbcTemplate.queryForObject("SELECT a.name as team_a_name, b.name as team_b_name, " +
                            "match_stats.* from teams a, teams b, match_stats " +
                            "where match_stats.id = ? and match_stats.team_a_id = a.id and\n" +
                            "match_stats.team_b_id = b.id;",
                    BeanPropertyRowMapper.newInstance(Match.class), id);

            return match;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }


    @Override
    public ArrayList<Match> getLastPlayedNMatches(int n) throws SQLException {
        System.out.println(n);
        ArrayList<Integer> lastPlayedNMatchesIds = new ArrayList<>();
        lastPlayedNMatchesIds = DbUpdates.getLastPlayedNMatchesIds(n);
        ArrayList<Match> matches = new ArrayList<>();
        for(int id:lastPlayedNMatchesIds){
            System.out.println(id);
            matches.add(findById(id));
        }
        return matches;
    }

    @Override
    public List<Match> findMatchesBySeriesId(int id) throws SQLException {
        int startMatchId, endMatchId;
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "select * from series where id = " + id;
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        startMatchId = rs.getInt("start_match_id");
        endMatchId = rs.getInt("end_match_id");
        stmt.close();
        conn.close();
        List<Match> matches = new ArrayList<>();
        for (int match = startMatchId; match<=endMatchId; match++){
            System.out.println(match);
            matches.add(findById(match));
        }
        return matches;
    }

}
