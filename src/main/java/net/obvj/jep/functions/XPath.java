package net.obvj.jep.functions;

import java.io.IOException;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import net.obvj.jep.util.XmlUtils;

/**
 * This class implements a function that evaluates XPaths.
 *
 * @author oswaldo.bapvic.jr
 */
public class XPath extends PostfixMathCommand
{
    private static final String ERROR_XPATH_ARGUMENT_MISSING = "XPath argument missing";
    private static final String ERROR_NO_RESULTS_FOR_PATH = "No results for path: %s";
    private static final String ERROR_INVALID_XPATH = "Invalid XPath: %s";
    private static final String ERROR_VARIABLE_NOT_FOUND = "XML object not found: %s";
    private static final String ERROR_INVALID_XML = "Invalid XML: %s";

    /**
     * Builds this function with two parameters
     */
    public XPath()
    {
        numberOfParameters = 2;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommandI#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);

        Object xPathArg = stack.peek();
        if (xPathArg == null || xPathArg.toString().isEmpty())
        {
            throw new IllegalArgumentException(ERROR_XPATH_ARGUMENT_MISSING);
        }

        String xPathString = stack.pop().toString();
        Object xmlVariable = stack.pop();
        if (xmlVariable == null)
        {
            throw new IllegalArgumentException(String.format(ERROR_VARIABLE_NOT_FOUND, xmlVariable));
        }
        stack.push(executeXPath(xPathString, xmlVariable));
    }

    private Object executeXPath(String xPathString, Object xmlVariable)
    {
        try
        {
            Document xmlDocument = XmlUtils.convertToXML(xmlVariable);
            Object result = XmlUtils.evaluateXPathAsObjectList(xmlDocument, xPathString);

            if (result == null)
            {
                throw new IllegalArgumentException(String.format(ERROR_NO_RESULTS_FOR_PATH, xPathString));
            }
            return result;
        }
        catch (ParserConfigurationException | SAXException | IOException e)
        {
            throw new IllegalArgumentException(String.format(ERROR_INVALID_XML, e.getMessage()), e);
        }
        catch (XPathExpressionException e)
        {
            throw new IllegalArgumentException(String.format(ERROR_INVALID_XPATH, xPathString), e);
        }
    }

}
