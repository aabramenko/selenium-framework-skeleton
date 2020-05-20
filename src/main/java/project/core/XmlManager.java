package project.core;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.net.URL;

public class XmlManager {

    private static Logger log = Logger.getLogger("");

    public static Document uploadXmlFromUrlToDocument(String url) {
        log.info("getting xml document from: " + url);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = db.parse(new URL(url).openStream());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        doc.getDocumentElement().normalize();
        return doc;
    }

    public static String getValueByXpath(Document doc, String xpathString) {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpathObj = xPathFactory.newXPath();
        XPathExpression expr = null;
        try {
            expr = xpathObj.compile(xpathString);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        NodeList node = null;
        try {
            node = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        String value = node.item(0).getTextContent();
        return value;
    }


}
