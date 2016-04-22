package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.view.AnimatedSprite;

public class AnimationFinishedEvent {
    private AnimatedSprite animatedSprite;

    public AnimationFinishedEvent(AnimatedSprite animatedSprite) {
        this.animatedSprite = animatedSprite;
    }

    public AnimatedSprite getAnimatedSprite() {
        return this.animatedSprite;
    }

}
