package com.company;

import com.company.controllers.GameController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CricketGame {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/cricket";
    public static final String USER = "root";
    public static final String PASS = "@Saurabh1";

    /*
    24-02-2022

    This method main is the entry point
    for our game.
     */
    public static void main(String[] args) throws SQLException {

        GameController.preGameSetUp();

    }

}
