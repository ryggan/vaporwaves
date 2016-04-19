package edu.chalmers.vaporwave.util;

import edu.chalmers.vaporwave.model.SpriteProperties;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Created by andreascarlsson on 2016-04-19.
 */
public class CharacterLoader {

    public static SpriteProperties[] loadCharacters(NodeList nodeList) {

        SpriteProperties spriteProperties[] = new SpriteProperties[nodeList.getLength()];

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element)nodeList.item(i);

            String name = element.getElementsByTagName("name").item(0).getTextContent();
            String state = element.getElementsByTagName("state").item(0).getTextContent();
            int xDimension = Integer.parseInt(element.getElementsByTagName("xDimension").item(0).getTextContent());
            int yDimension = Integer.parseInt(element.getElementsByTagName("yDimension").item(0).getTextContent());
            int frames = Integer.parseInt(element.getElementsByTagName("frames").item(0).getTextContent());
            double duration = Double.parseDouble(element.getElementsByTagName("duration").item(0).getTextContent());
            int[] firstFrame = { Integer.parseInt(element.getElementsByTagName("firstFrameX").item(0).getTextContent()), Integer.parseInt(element.getElementsByTagName("firstFrameY").item(0).getTextContent())};

            SpriteProperties property = new SpriteProperties(name, state, xDimension, yDimension, frames, duration, firstFrame);
            spriteProperties[i] = property;


        }


        return spriteProperties;

    }

}
