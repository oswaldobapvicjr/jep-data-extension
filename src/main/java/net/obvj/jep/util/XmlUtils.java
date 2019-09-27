package net.obvj.jep.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
     * @param expression the XPath expression to be compiled
     * @return an {@code XPathExpression} object that can be used for further evaluation
     * @throws XPathExpressionException if the expression cannot be compiled
     */
    public static XPathExpression compileXPath(String expression) throws XPathExpressionException
    {
        return XPathFactory.newInstance().newXPath().compile(expression);
    }

    /**
     * Returns a list of XML nodes that match the given XPath expression.
     *
     * @param xmlDocument the XML object to be evaluated
     * @param expression the XPath expression to be used for evaluation
     * @return A list of XML nodes that match the given XPath expression or {@code null} if
     * the XML Document is null.
     * @throws XPathExpressionException if the expression cannot be evaluated.
     */
    public static NodeList evaluateXPath(Document xmlDocument, String expression) throws XPathExpressionException
    {
        return (NodeList) compileXPath(expression).evaluate(xmlDocument, XPathConstants.NODESET);
    }

    /**
     * Returns a list of XML nodes that match the given XPath expression.
     *
     * @param xmlContent a string in XML format representing the content to be evaluated
     * @param expression the XPath expression to be used for evaluation
     * @return a list of XML nodes that match the given XPath expression or {@code null} if
     * the XML content is null.
     * @throws XPathExpressionException if the expression cannot be compiled.
     * @throws SAXException if any error occurs trying to parse the XML content string
     * @throws IOException if the input string cannot be converted
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created
     */
    public static NodeList evaluateXPath(String xmlContent, String expression)
            throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
    {
        Document xmlDocument = convertToXML(xmlContent);
        return evaluateXPath(xmlDocument, expression);
    }

    /**
     * Returns a list of Objects that match the given XPath expression.
     *
     * @param xmlDocument the XML object to be evaluated
     * @param expression the XPath expression to be used for evaluation
     * @return A list of Objects that match the given XPath expression or {@code null} if the
     * XML Document is null.
     * @throws XPathExpressionException If the expression cannot be compiled.
     */
    public static List<Object> evaluateXPathAsObjectList(Document xmlDocument, String expression)
            throws XPathExpressionException
    {
        return asList(evaluateXPath(xmlDocument, expression));
    }

    /**
     * Returns a list of Objects that match the given XPath expression.
     *
     * @param xmlContent a string in XML format representing the content to be evaluated
     * @param expression the XPath expression to be used for evaluation
     * @return The value that matches the given XPath expression or {@code null} if the XML
     * Document is null.
     * @throws XPathExpressionException if the expression cannot be compiled.
     * @throws SAXException if any parse error occurs trying to parse the XML content string
     * @throws IOException if the input string cannot be converted
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created
     */
    public static List<Object> evaluateXPathAsObjectList(String xmlContent, String expression)
            throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
    {
        return asList(evaluateXPath(xmlContent, expression));
    }

    public static List<Object> asList(NodeList n)
    {
        return n.getLength() == 0 ? Collections.emptyList() : new NodeListWrapper(n);
    }

    /**
     * Converts an object into an XML Document.
     *
     * @param object the object to be converted in to an XML Document
     * @return the object as an XML Document
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created
     * @throws SAXException                 if any parse error occurs
     * @throws IOException                  if the input string cannot be converted
     */
    public static Document convertToXML(Object object)
            throws ParserConfigurationException, SAXException, IOException
    {
        if (object instanceof Document)
        {
            return (Document) object;
        }
        else if (object instanceof String)
        {
            return convertToXML((String) object);
        }
        throw new IllegalArgumentException("Input type " + object.getClass().getName() + " not supported as XML");
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

    private static final class NodeListWrapper extends AbstractList<Object> implements RandomAccess
    {
        private final NodeList list;

        NodeListWrapper(NodeList list)
        {
            this.list = list;
        }

        public Object get(int index)
        {
            Node node = list.item(index);
            return node.getNodeValue();
        }

        public int size()
        {
            return list.getLength();
        }
    }
}
