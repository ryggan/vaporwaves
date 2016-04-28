package edu.chalmers.vaporwave.util;

import edu.chalmers.vaporwave.model.PowerUpProperties;
import edu.chalmers.vaporwave.model.PowerUpSpriteProperties;
import javafx.scene.image.Image;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by FEngelbrektsson on 22/04/16.
 */
public class PowerUpLoader {
    public static PowerUpProperties loadPowerUp(NodeList nodeList, String type) {

        Map<PowerUpState, PowerUpSpriteProperties> spritePropertiesMap = new HashMap<PowerUpState, PowerUpSpriteProperties>();

        for (int i = 0; i < nodeList.getLength(); i++) {

            Element allPowerUpNodes = (Element)nodeList.item(i);

            for (int j = 0; j < allPowerUpNodes.getElementsByTagName("powerUp").getLength(); j++) {
                Element singlePowerUpNodes = (Element)allPowerUpNodes;
                String currentType = singlePowerUpNodes.getElementsByTagName("type").item(j).getTextContent();
                if (currentType.equals(type)) {
                    for (int k = 0; k < Constants.POWERUP_STATE.length; k++) {
                        Element currentSpriteNodes = (Element)singlePowerUpNodes.getElementsByTagName(Constants.POWERUP_STATE[k].toString().toLowerCase()).item(j);
                        Image spritesheet = new Image(currentSpriteNodes.getElementsByTagName("spritesheet").item(0).getTextContent());
                        int dimensionX = Integer.parseInt(currentSpriteNodes.getElementsByTagName("dimensionX").item(0).getTextContent());
                        int dimensionY = Integer.parseInt(currentSpriteNodes.getElementsByTagName("dimensionY").item(0).getTextContent());
                        int frames = Integer.parseInt(currentSpriteNodes.getElementsByTagName("frames").item(0).getTextContent());
                        double duration = Double.parseDouble(currentSpriteNodes.getElementsByTagName("duration").item(0).getTextContent());
                        int[] firstFrame = { Integer.parseInt(currentSpriteNodes.getElementsByTagName("firstFrameX").item(0).getTextContent()),
                                Integer.parseInt(currentSpriteNodes.getElementsByTagName("firstFrameY").item(0).getTextContent())};
                        double[] offset = { Double.parseDouble(currentSpriteNodes.getElementsByTagName("offsetX").item(0).getTextContent()),
                                Integer.parseInt(currentSpriteNodes.getElementsByTagName("offsetY").item(0).getTextContent())};

                        //samma här
                        PowerUpSpriteProperties property = new PowerUpSpriteProperties(Constants.POWERUP_STATE[k].toString().toLowerCase(),
                                spritesheet, dimensionX, dimensionY, frames, duration, firstFrame, offset);
                        spritePropertiesMap.put(Constants.POWERUP_STATE[k], property);

                    }

                }

            }

        }

        return new PowerUpProperties(type, spritePropertiesMap);

    }

}
