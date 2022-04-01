package com.tekion.repository;

import com.tekion.constants.StringUtils;
import com.tekion.configs.Config;
import com.tekion.models.Match;
import com.tekion.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*

This class works as a repository,
it has methods that fetch details
about the matches.
 */
@Repository
public class JdbcMatchRepository implements  MatchRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Config config;
    
    /*

    It is fetching stats of a match by its id
    from match_stats table.
     */
    @Override
    public Match findMatchById(int id) {
        Match match = this.jdbcTemplate.queryForObject("SELECT a.name as team_a_name, b.name as team_b_name, " +
                        "match_stats.* from teams a, teams b, match_stats " +
                        "where match_stats.id = ? and match_stats.team_a_id = a.id and " +
                        "match_stats.team_b_id = b.id;",
                BeanPropertyRowMapper.newInstance(Match.class), id);
        return match;
    }

    /*

    It is fetching stats of a match by its id
    from match_stats table.
     */
    @Override
    public ArrayList<Match> getLastPlayedNMatches(int n) throws SQLException {
        ArrayList<Integer> lastPlayedNMatchesIds = new ArrayList<>();
        lastPlayedNMatchesIds = DbUpdates.getLastPlayedNMatchesIds(n);
        ArrayList<Match> matches = new ArrayList<>();
        for(int id:lastPlayedNMatchesIds){
            matches.add(findMatchById(id));
        }
        return matches;
    }

    /*

    It is fetching stats of a matches played under a series
    by its series id from match_stats table.
     */
    @Override
    public List<Match> findSeriesById(int id) throws SQLException {
        int startMatchId, endMatchId;
        Connection conn = DriverManager.getConnection(StringUtils.DB_URL, StringUtils.USER, StringUtils.PASS);
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
            matches.add(findMatchById(match));
        }
        return matches;
    }

}
