package main.java.com.tekion;

import main.java.com.tekion.controllers.GameController;
import java.sql.SQLException;

public class CricketGame {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/cricket";
    public static final String USER = "root";
    public static final String PASS = "@Saurabh1";

    /*
    24-02-2022

    This method main is the entry point
    for our game.


    @param args String[]
     */
    public static void main(String[] args) throws SQLException {

        GameController.preGameSetUp();

    }

}
