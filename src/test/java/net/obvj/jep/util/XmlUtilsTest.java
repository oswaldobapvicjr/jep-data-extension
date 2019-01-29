package net.obvj.jep.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlUtilsTest
{
    private static final String STRING_XML_BOOKS = TextFileReader.readFile("books.xml");

    private static final String XPATH_ALL_BOOK_TITLES = "/bookstore/book/title/text()";
    private static final String XPATH_ALL_WEB_BOOK_TITLES = "/bookstore/book[@category='web']/title/text()";

    private static final String XPATH_ALL_BOOK_TITLES_INVALID = "/bookstore/book/title/text(";

    /**
     * Tests that no instances of this utility class are created
     *
     * @throws Exception in case of error getting constructor metadata or instantiating the
     *                   private constructor via Reflection
     */
    @Test(expected = InvocationTargetException.class)
    public void testNoInstancesAllowed() throws Exception
    {
        try
        {
            Constructor<XmlUtils> constructor = XmlUtils.class.getDeclaredConstructor();
            assertTrue("Constructor is not private", Modifier.isPrivate(constructor.getModifiers()));

            constructor.setAccessible(true);
            constructor.newInstance();
        }
        catch (InvocationTargetException ite)
        {
            Throwable cause = ite.getCause();
            assertEquals(IllegalStateException.class, cause.getClass());
            assertEquals("Utility class", cause.getMessage());
            throw ite;
        }
    }

    @Test
    public void testConvertStringToXmlDocument() throws ParserConfigurationException, SAXException, IOException
    {
        Document xml = XmlUtils.convertToXML(STRING_XML_BOOKS);
        assertEquals("bookstore", xml.getDocumentElement().getNodeName());
    }

    @Test
    public void testCompileXPathWithValidExpression() throws XPathExpressionException
    {
        XPathExpression compiledXPath = XmlUtils.compileXPath(XPATH_ALL_BOOK_TITLES);
        assertNotNull(compiledXPath);
    }

    @Test(expected = XPathExpressionException.class)
    public void testCompileXPathWithInValidExpression() throws XPathExpressionException
    {
        XPathExpression compiledXPath = XmlUtils.compileXPath(XPATH_ALL_BOOK_TITLES_INVALID);
        assertNotNull(compiledXPath);
    }
}
