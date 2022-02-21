package com.company.controllers;

import com.company.controllers.Observer;

public interface Subject {
    public void registerObserver(Observer o, String typeOfObserver);
    public void unregisterObserver(Observer o);
    public void notifyObservers();
}
