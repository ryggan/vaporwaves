package edu.chalmers.vaporwave.assetcontainer;

public class CharacterSprite {

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
        ((AnimatedSprite)sprite).setStartFromBeginning(true);
        ((AnimatedSprite)sprite).setLoops(1);
        deathSprite[0] = sprite;
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

    // Not sure if these are needed, maybe delete later:

//    public Sprite getSpawnSprite() {
//        return spawnSprite[0];
//    }
//
//    public Sprite getIdleSprite(int index) {
//        return idleSprite[index];
//    }
//
//    public Sprite getWalkSprite(int index) {
//        return walkSprite[index];
//    }
//
//    public Sprite getFlinchSprite(int index) {
//        return flinchSprite[index];
//    }
//
//    public Sprite getDeathSprite() {
//        return deathSprite[0];
//    }

//    public void setIdleSprites(Sprite[] sprites) {
//        if (sprites.length != 4)
//            throw new IllegalArgumentException();
//
//        for (int i = 0; i < 4; i++) {
//            idleSprite[i] = sprites[i];
//        }
//    }
//
//    public void setWalkSprites(Sprite[] sprites) {
//        if (sprites.length != 4)
//            throw new IllegalArgumentException();
//
//        for (int i = 0; i < 4; i++) {
//            walkSprite[i] = sprites[i];
//        }
//    }
//
//    public void setFlinchSprites(Sprite[] sprites) {
//        if (sprites.length != 4)
//            throw new IllegalArgumentException();
//
//        for (int i = 0; i < 4; i++) {
//            flinchSprite[i] = sprites[i];
//        }
//    }

    public Sprite[] spriteArrayCloner(Sprite[] spriteArray) {
        Sprite[] temporary = new Sprite[spriteArray.length];
        for (int i = 0; i < spriteArray.length; i++) {
            temporary[i] = spriteArray[i];
        }
        return temporary;
    }
}
