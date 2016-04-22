package edu.chalmers.vaporwave.view;

/**
 * Created by FEngelbrektsson on 22/04/16.
 */
public class PowerUpSprite {
    private String type;

    /**
     * Probably have to change the size of the arrays, when we've got proper sprites for powerups
     * todo: this
     */
    private Sprite[] healthSprite = new Sprite[1];
    private Sprite[] bombCountSprite = new Sprite[4];
    private Sprite[] rangeSprite = new Sprite[4];
    private Sprite[] speedSprite = new Sprite[4];
    private Sprite[] deathSprite = new Sprite[1];

    public PowerUpSprite(String type) {
        setType(type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setHealthSprite(Sprite sprite) {
        ((AnimatedSprite)sprite).setStartFromBeginning(true);
        ((AnimatedSprite)sprite).setLoops(1);
        healthSprite[0] = sprite;
    }

    public void setBombCountSprite(Sprite sprite) {
        ((AnimatedSprite)sprite).setStartFromBeginning(true);
        ((AnimatedSprite)sprite).setLoops(1);
        bombCountSprite[0] = sprite;    }

    public void setRangeSprite(Sprite sprite) {
        ((AnimatedSprite)sprite).setStartFromBeginning(true);
        ((AnimatedSprite)sprite).setLoops(1);
        rangeSprite[0] = sprite;
    }

    public void setSpeedSprite(Sprite sprite) {
        ((AnimatedSprite)sprite).setStartFromBeginning(true);
        ((AnimatedSprite)sprite).setLoops(1);
        healthSprite[0] = sprite;
    }

    public Sprite[] getHealthSprites() {
        return healthSprite;
    }

    public Sprite[] getBombCountSprites() {
        return bombCountSprite;
    }

    public Sprite[] getSpeedSprites() {
        return speedSprite;
    }

    public Sprite[] getRangeSprites() {
        return rangeSprite;
    }
}
