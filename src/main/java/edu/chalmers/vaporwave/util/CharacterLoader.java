package edu.chalmers.vaporwave.util;

import edu.chalmers.vaporwave.model.CharacterProperties;
import edu.chalmers.vaporwave.model.CharacterSpriteProperties;
import javafx.scene.image.Image;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andreascarlsson on 2016-04-19.
 */
public class CharacterLoader {

    public static CharacterProperties loadCharacter(NodeList nodeList, String name) {

        Map<CharacterState, CharacterSpriteProperties> spritePropertiesMap = new HashMap<CharacterState, CharacterSpriteProperties>();

        for (int i = 0; i < nodeList.getLength(); i++) {

            Element allCharacterNodes = (Element)nodeList.item(i);

            for (int j = 0; j < allCharacterNodes.getElementsByTagName("character").getLength(); j++) {
                Element singleCharacterNodes = (Element)allCharacterNodes;
                String currentName = singleCharacterNodes.getElementsByTagName("name").item(j).getTextContent();
                if (currentName.equals(name)) {

                    for (int k = 0; k < Constants.CHARACTER_CHARACTER_STATE.length; k++) {

                        Element currentSpriteNodes = (Element)singleCharacterNodes.getElementsByTagName(Constants.CHARACTER_CHARACTER_STATE[k].toString().toLowerCase()).item(j);

                        Image spritesheet = new Image(currentSpriteNodes.getElementsByTagName("spritesheet").item(0).getTextContent());
                        int dimensionX = Integer.parseInt(currentSpriteNodes.getElementsByTagName("dimensionX").item(0).getTextContent());
                        int dimensionY = Integer.parseInt(currentSpriteNodes.getElementsByTagName("dimensionY").item(0).getTextContent());
                        int frames = Integer.parseInt(currentSpriteNodes.getElementsByTagName("frames").item(0).getTextContent());
                        double duration = Double.parseDouble(currentSpriteNodes.getElementsByTagName("duration").item(0).getTextContent());
                        int[] firstFrame = { Integer.parseInt(currentSpriteNodes.getElementsByTagName("firstFrameX").item(0).getTextContent()),
                                Integer.parseInt(currentSpriteNodes.getElementsByTagName("firstFrameY").item(0).getTextContent())};
                        double[] offset = { Double.parseDouble(currentSpriteNodes.getElementsByTagName("offsetX").item(0).getTextContent()),
                                Integer.parseInt(currentSpriteNodes.getElementsByTagName("offsetY").item(0).getTextContent())};

                        CharacterSpriteProperties property = new CharacterSpriteProperties(Constants.CHARACTER_CHARACTER_STATE[k].toString().toLowerCase(),
                                spritesheet, dimensionX, dimensionY, frames, duration, firstFrame, offset);
                        spritePropertiesMap.put(Constants.CHARACTER_CHARACTER_STATE[k], property);

                    }

                }

            }

        }

        return new CharacterProperties(name, spritePropertiesMap);

    }

}
