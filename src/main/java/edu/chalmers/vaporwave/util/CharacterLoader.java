package edu.chalmers.vaporwave.util;

import edu.chalmers.vaporwave.model.SpriteProperties;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Created by andreascarlsson on 2016-04-19.
 */
public class CharacterLoader {

    public static SpriteProperties[] loadCharacter(NodeList nodeList, String name) {


        SpriteProperties spriteProperties[] = new SpriteProperties[nodeList.getLength()];

        System.out.println("sprite");

        for (int i = 0; i < nodeList.getLength(); i++) {

            Element allCharacterNodes = (Element)nodeList.item(i);

            for (int j = 0; j < allCharacterNodes.getElementsByTagName("character").getLength(); j++) {
                Element singleCharacterNodes = (Element)nodeList.item(i);
                String currentName = singleCharacterNodes.getElementsByTagName("name").item(j).getTextContent();
                if (currentName.equals(name)) {
                    System.out.println("Fetching info for: " + name);


                }

            }

//            String state = element.getElementsByTagName("state").item(0).getTextContent();
//            int xDimension = Integer.parseInt(element.getElementsByTagName("xDimension").item(0).getTextContent());
//            int yDimension = Integer.parseInt(element.getElementsByTagName("yDimension").item(0).getTextContent());
//            int frames = Integer.parseInt(element.getElementsByTagName("frames").item(0).getTextContent());
//            double duration = Double.parseDouble(element.getElementsByTagName("duration").item(0).getTextContent());
//            int[] firstFrame = { Integer.parseInt(element.getElementsByTagName("firstFrameX").item(0).getTextContent()), Integer.parseInt(element.getElementsByTagName("firstFrameY").item(0).getTextContent())};
//
//            SpriteProperties property = new SpriteProperties(state, xDimension, yDimension, frames, duration, firstFrame);
//            spriteProperties[i] = property;


        }


        return spriteProperties;

    }

}
