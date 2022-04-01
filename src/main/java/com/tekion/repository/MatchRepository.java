package com.tekion.repository;

import com.tekion.models.Match;
import com.tekion.models.SeriesStats;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface MatchRepository {
    Match findMatchById(int id);
    ArrayList<Match> getLastPlayedNMatches(int n) throws SQLException;
    List<Match> findSeriesById(int id) throws SQLException;
}
