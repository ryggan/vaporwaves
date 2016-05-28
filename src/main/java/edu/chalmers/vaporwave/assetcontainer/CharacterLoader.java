package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.CharacterStat;
import edu.chalmers.vaporwave.util.MovableState;
import javafx.scene.image.Image;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple class for one static method; to sort nodes from XML-loader and create a
 * CharacterProperties object
 */
class CharacterLoader {

    private static final MovableState[] CHARACTER_CHARACTER_STATE = { MovableState.WALK, MovableState.IDLE, MovableState.FLINCH, MovableState.DEATH, MovableState.SPAWN };

    public static CharacterProperties loadCharacter(NodeList nodeList, String name) {
        Map<MovableState, CharacterSpriteProperties> spritePropertiesMap = new HashMap<>();
        Map<CharacterStat, Double> characterStats = new HashMap<>();

        // Loops through the nodelist that is created when using the XML-loader
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element allCharacterNodes = (Element)nodeList.item(i);

            for (int j = 0; j < allCharacterNodes.getElementsByTagName("character").getLength(); j++) {
                Element singleCharacterNodes = allCharacterNodes;
                String currentName = singleCharacterNodes.getElementsByTagName("name").item(j).getTextContent();

                // We are only looking for a specific character
                if (currentName.equals(name)) {

                    // Fetching sprite information for every character state and saving it
                    for (int k = 0; k < CHARACTER_CHARACTER_STATE.length; k++) {

                        Element currentSpriteNodes =(Element)singleCharacterNodes.getElementsByTagName(
                                CHARACTER_CHARACTER_STATE[k].toString().toLowerCase(Locale.ENGLISH)).item(j);

                        Image spritesheet = Container.getImage(ImageID.valueOf(
                                currentSpriteNodes.getElementsByTagName("spritesheet").item(0).getTextContent()));

                        int dimensionX = Integer.parseInt(currentSpriteNodes.getElementsByTagName("dimensionX").item(0).getTextContent());
                        int dimensionY = Integer.parseInt(currentSpriteNodes.getElementsByTagName("dimensionY").item(0).getTextContent());
                        int frames = Integer.parseInt(currentSpriteNodes.getElementsByTagName("frames").item(0).getTextContent());
                        double duration = Double.parseDouble(currentSpriteNodes.getElementsByTagName("duration").item(0).getTextContent());
                        int[] firstFrame = { Integer.parseInt(currentSpriteNodes.getElementsByTagName("firstFrameX").item(0).getTextContent()),
                                Integer.parseInt(currentSpriteNodes.getElementsByTagName("firstFrameY").item(0).getTextContent())};
                        double[] offset = { Double.parseDouble(currentSpriteNodes.getElementsByTagName("offsetX").item(0).getTextContent()),
                                Integer.parseInt(currentSpriteNodes.getElementsByTagName("offsetY").item(0).getTextContent())};

                        CharacterSpriteProperties property =
                                new CharacterSpriteProperties(CHARACTER_CHARACTER_STATE[k].toString().toLowerCase(Locale.ENGLISH),
                                spritesheet, dimensionX, dimensionY, frames, duration, firstFrame, offset);
                        spritePropertiesMap.put(CHARACTER_CHARACTER_STATE[k], property);
                    }

                    // Also fetching all stats for character
                    Double health = Double.parseDouble(singleCharacterNodes.getElementsByTagName("health").item(j).getTextContent());
                    characterStats.put(CharacterStat.HEALTH, health);
                    Double speed = Double.parseDouble(singleCharacterNodes.getElementsByTagName("speed").item(j).getTextContent());
                    characterStats.put(CharacterStat.SPEED, speed);
                    Double bombRange = Double.parseDouble(singleCharacterNodes.getElementsByTagName("bombRange").item(j).getTextContent());
                    characterStats.put(CharacterStat.BOMB_RANGE, bombRange);
                    Double bombCount = Double.parseDouble(singleCharacterNodes.getElementsByTagName("bombCount").item(j).getTextContent());
                    characterStats.put(CharacterStat.BOMB_COUNT, bombCount);
                    Double damage = Double.parseDouble(singleCharacterNodes.getElementsByTagName("damage").item(j).getTextContent());
                    characterStats.put(CharacterStat.DAMAGE, damage);
                }
            }
        }

        // bundling it up in one CharacterProperties object, for easy use
        return new CharacterProperties(name, spritePropertiesMap, characterStats);

    }

}
