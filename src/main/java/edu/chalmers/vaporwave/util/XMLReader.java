package edu.chalmers.vaporwave.util;

import com.sun.org.apache.xerces.internal.dom.AttributeMap;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by andreascarlsson on 2016-04-19.
 */
public class XMLReader {
    private String file;

    public XMLReader(String file) {
        this.file = file;
    }

    /**
     * Method for reading from an XML-file and returning the content as a NodeList
     *
     * @return A NodeList containing all characters if reading successful, otherwise null.
     */
    public NodeList read() {
        try {
            File file = new File(this.file);
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();

            NodeList characterList = document.getElementsByTagName("gameCharacters");
            return characterList;
        } catch(ParserConfigurationException e) {
            System.out.println(e);
        } catch(IOException e) {
            System.out.println(e);
        } catch(SAXException e) {
            System.out.println(e);
        }

        return null;
    }

}
