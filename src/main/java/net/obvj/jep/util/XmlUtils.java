package net.obvj.jep.util;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Utility methods for working with XML and XPath
 *
 * @author oswaldo.bapvic.jr
 */
public class XmlUtils
{

    private XmlUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Compiles the given XPath expression.
     *
     * @param expression the XPath expression to be validated
     * @return a {@code XPathExpression} object for later evaluation
     * @throws XPathExpressionException If the expression cannot be compiled
     */
    public static XPathExpression compileXPath(String expression) throws XPathExpressionException
    {
        return XPathFactory.newInstance().newXPath().compile(expression);
    }

    /**
     * Gets a value that matches the given JSONPath.
     *
     * @param xmlDocument the XML object to be queried
     * @param expression  the XPath to be evaluated
     * @return The value that matches the given XPath expression or {@code null} if the XML
     *         Document is null.
     * @throws XPathExpressionException If the expression cannot be evaluated.
     */
    public static Object evaluateXPath(Document xmlDocument, String expression) throws XPathExpressionException
    {
        if (xmlDocument == null)
        {
            return null;
        }
        return compileXPath(expression).evaluate(xmlDocument, XPathConstants.NODESET);
    }

    /**
     * Gets a value that matches the given JSONPath.
     *
     * @param xmlDocument the XML object to be queried
     * @param expression  the XPath to be evaluated
     * @return The value that matches the given XPath expression or {@code null} if the XML
     *         Document is null.
     * @throws XPathExpressionException     If the expression cannot be evaluated.
     * @throws SAXException                 if any parse error occurs
     * @throws IOException                  if the input string cannot be converted
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created
     */
    public static Object evaluateXPath(String xmlContent, String expression)
            throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
    {
        Document xmlDocument = convertToXML(xmlContent);
        return evaluateXPath(xmlDocument, expression);
    }

    /**
     * Converts a String to an XML Document.
     *
     * @param xmlContent the object to be converted in to an XML Document
     * @return the object as an XML Document
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created
     * @throws SAXException                 if any parse error occurs
     * @throws IOException                  if the input string cannot be converted
     */
    public static Document convertToXML(String xmlContent)
            throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlContent)));
    }

}
