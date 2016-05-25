package edu.chalmers.vaporwave.controller;

public interface ContentController {
    // This one is called every time the game-timer is updated
    void timerUpdate(double timeSinceStart, double timeSinceLastCall);
}
