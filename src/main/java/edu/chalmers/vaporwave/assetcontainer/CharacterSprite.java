package edu.chalmers.vaporwave.assetcontainer;

/**
 * A class to keep track of all sprites for one character, because they are so many.
 */
class CharacterSprite {

    private String name;

    private Sprite[] spawnSprite = new Sprite[1];
    private Sprite[] idleSprite = new Sprite[4];
    private Sprite[] walkSprite = new Sprite[4];
    private Sprite[] flinchSprite = new Sprite[4];
    private Sprite[] deathSprite = new Sprite[1];

    public CharacterSprite(String name) {
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSpawnSprite(AnimatedSprite sprite) {
        sprite.setStartFromBeginning(true);
        sprite.setLoops(1);
        spawnSprite[0] = sprite;
    }

    public void setIdleSprite(Sprite sprite, int index) {
        idleSprite[index] = sprite;
    }

    public void setWalkSprite(Sprite sprite, int index) {
        walkSprite[index] = sprite;
    }

    public void setFlinchSprite(Sprite sprite, int index) {
        flinchSprite[index] = sprite;
    }

    public void setDeathSprite(Sprite sprite) {
        if (sprite instanceof AnimatedSprite) {
            ((AnimatedSprite) sprite).setStartFromBeginning(true);
            ((AnimatedSprite) sprite).setLoops(1);
            deathSprite[0] = sprite;
        }
    }

    public Sprite[] getDeathSprites() {
        return spriteArrayCloner(deathSprite);
    }

    public Sprite[] getIdleSprites() {
        return spriteArrayCloner(idleSprite);
    }

    public Sprite[] getWalkSprites() {
        return spriteArrayCloner(walkSprite);
    }

    public Sprite[] getFlinchSprites() {
        return spriteArrayCloner(flinchSprite);
    }

    public Sprite[] getSpawnSprites() {
        return spriteArrayCloner(spawnSprite);
    }

    public Sprite[] spriteArrayCloner(Sprite[] spriteArray) {
        Sprite[] temporary = new Sprite[spriteArray.length];
        for (int i = 0; i < spriteArray.length; i++) {
            temporary[i] = spriteArray[i];
        }
        return temporary;
    }
}
