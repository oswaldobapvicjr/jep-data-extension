package net.obvj.jep.functions;

import java.io.IOException;
import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.FileUtils;

/**
 * A function that reads the content a text file from the file system
 *
 * @author oswaldo.bapvic.jr
 */
public class ReadFile extends PostfixMathCommand
{

    private static final String UNABLE_TO_READ_FILE_MESSAGE = "Unable to read file '%s': Cause: %s";

    /**
     * Builds this custom command with a fixed number of 1 parameter
     */
    public ReadFile()
    {
        numberOfParameters = 1;
    }

    /**
     * @see org.nfunk.jep.function.Comparative#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        String fileName = stack.pop().toString();
        try
        {
            stack.push(FileUtils.readFromFileSystem(fileName));
        }
        catch (IOException e)
        {
            throw new ParseException(
                    String.format(UNABLE_TO_READ_FILE_MESSAGE, fileName, e.getClass().getName()));
        }
    }

}
