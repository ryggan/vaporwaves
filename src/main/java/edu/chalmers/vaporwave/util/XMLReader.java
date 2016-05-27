package edu.chalmers.vaporwave.util;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLReader {

    private File file;

    public XMLReader(File file) {
        this.file = file;
    }

    /**
     * Method for reading from an XML-file and returning the content as a NodeList
     *
     * @return A NodeList containing all characters if reading successful, otherwise null.
     */
    public NodeList read() throws Exception {
//        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(this.file);
            document.getDocumentElement().normalize();

            NodeList characterList = document.getElementsByTagName("gameCharacters");
            return characterList;
//        } catch(ParserConfigurationException e) {
//            System.out.println(e);
//        } catch(IOException e) {
//            System.out.println(e);
//        } catch(SAXException e) {
//            System.out.println(e);
//        }

//        return null;
    }

}
