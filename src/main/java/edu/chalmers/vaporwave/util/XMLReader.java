package edu.chalmers.vaporwave.util;

import org.w3c.dom.Document;
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

    public void read() {
        System.out.println(file);


        try {
            File file = new File(this.file);
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
            System.out.println(document.getDocumentElement().getNodeName());

        } catch(ParserConfigurationException e) {
            System.out.println(e);
        } catch(IOException e) {
            System.out.println(e);
        } catch(SAXException e) {
            System.out.println(e);
        }
    }

}
