package edu.chalmers.vaporwave.event;

/**
 * This event is posted to the eventbus to tell Main class to switch fullscreen.
 */
public class ToggleFullScreenEvent {

    private double timeStamp;

    private boolean fullscreen;
    private boolean forced;

    public ToggleFullScreenEvent(double timeStamp) {
        this.timeStamp = timeStamp;
        this.forced = false;
    }

    public ToggleFullScreenEvent(double timeStamp, boolean fullscreen) {
        this.timeStamp = timeStamp;
        this.fullscreen = fullscreen;
        this.forced = true;
    }

    public boolean getFullscreen() {
        return this.fullscreen;
    }

    public boolean isForced() {
        return this.forced;
    }

    public double getTimeStamp() {
        return this.timeStamp;
    }
}
