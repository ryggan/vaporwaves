package edu.chalmers.vaporwave.controller;

/**
 * A simple interface, to ge a State Pattern going in MainController,
 * with GameController and MenuController as its states.
 */
public interface ContentController {

    // This is called every time the game-timer is updated
    void timerUpdate(double timeSinceStart, double timeSinceLastCall) throws Exception;
}
