package com.company.interfaces;


import java.sql.SQLException;

/*
23-02-2022

This interface Subject is responsible for
storing the observers to notify when an update
comes.
 */
public interface Subject {
    void registerObserver(Observer o, String typeOfObserver);
    void unregisterObserver(Observer o);
    void notifyObservers() throws SQLException;
}
