package com.company.interfaces;


import java.sql.SQLException;

public interface Observer {
    void updateStriker(int runs) throws SQLException;
    void updateTeam(int runs) throws SQLException;
    void updateBowler(int runs) throws SQLException;
}
