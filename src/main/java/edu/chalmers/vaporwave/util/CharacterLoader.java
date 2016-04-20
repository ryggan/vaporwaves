package edu.chalmers.vaporwave.util;

import edu.chalmers.vaporwave.model.CharacterProperties;
import edu.chalmers.vaporwave.model.SpriteProperties;
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

        Map<State, SpriteProperties> spritePropertiesMap = new HashMap<State, SpriteProperties>();

        for (int i = 0; i < nodeList.getLength(); i++) {

            Element allCharacterNodes = (Element)nodeList.item(i);

            for (int j = 0; j < allCharacterNodes.getElementsByTagName("character").getLength(); j++) {
                Element singleCharacterNodes = (Element)allCharacterNodes;
                String currentName = singleCharacterNodes.getElementsByTagName("name").item(j).getTextContent();
                if (currentName.equals(name)) {

                    for (int k = 0; k < State.values().length - 1; k++) {

                        Element currentSpriteNodes = (Element)singleCharacterNodes.getElementsByTagName(Constants.CHARACTER_STATE[k].toString().toLowerCase()).item(j);

                        Image spritesheet = new Image(currentSpriteNodes.getElementsByTagName("spritesheet").item(0).getTextContent());
                        int xDimension = Integer.parseInt(currentSpriteNodes.getElementsByTagName("xDimension").item(0).getTextContent());
                        int yDimension = Integer.parseInt(currentSpriteNodes.getElementsByTagName("yDimension").item(0).getTextContent());
                        int frames = Integer.parseInt(currentSpriteNodes.getElementsByTagName("frames").item(0).getTextContent());
                        double duration = Double.parseDouble(currentSpriteNodes.getElementsByTagName("duration").item(0).getTextContent());
                        int[] firstFrame = { Integer.parseInt(currentSpriteNodes.getElementsByTagName("firstFrameX").item(0).getTextContent()),
                                Integer.parseInt(currentSpriteNodes.getElementsByTagName("firstFrameY").item(0).getTextContent())};

                        SpriteProperties property = new SpriteProperties(Constants.CHARACTER_STATE[k].toString().toLowerCase(),
                                spritesheet, xDimension, yDimension, frames, duration, firstFrame);
                        spritePropertiesMap.put(Constants.CHARACTER_STATE[k], property);

                    }

                }

            }

        }

        return new CharacterProperties(name, spritePropertiesMap);

    }

}
