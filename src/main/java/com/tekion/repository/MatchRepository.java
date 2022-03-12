package com.tekion.repository;

import com.tekion.models.Match;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface MatchRepository {
    Match findById(int id);
    ArrayList<Match> getLastPlayedNMatches(int n) throws SQLException;

    List<Match> findMatchesBySeriesId(int id) throws SQLException;
}
