package edu.chalmers.vaporwave.util;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Holder class for reading from an XML-file and returning the content as a NodeList.
 */
public class XMLReader {

    private File file;

    public XMLReader(File file) {
        this.file = file;
    }

    public NodeList read() throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(this.file);
        document.getDocumentElement().normalize();

        NodeList characterList = document.getElementsByTagName("gameCharacters");
        return characterList;
    }

}
