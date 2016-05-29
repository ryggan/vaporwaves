package edu.chalmers.vaporwave.event;

/**
 * This event is posted to the eventbus to tell Main class to switch fullscreen.
 */
public class SetFullScreenEvent {

    private boolean fullscreen;

    public SetFullScreenEvent(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public boolean isFullscreen() {
        return this.fullscreen;
    }
}
