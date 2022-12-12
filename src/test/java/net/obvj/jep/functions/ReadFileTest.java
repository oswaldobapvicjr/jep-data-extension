package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Stack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.CollectionsUtils;
import net.obvj.jep.util.FileUtils;

/**
 * Unit tests for the {@link ReadFile} function
 *
 * @author oswaldo.bapvic.jr
 */
@RunWith(MockitoJUnitRunner.class)
public class ReadFileTest
{
    private static final String PATH = "/tmp/data.json";
    private static final String CONTENT = "test123";

    // Test subject
    private static ReadFile function = new ReadFile();

    /**
     * Checks that the correct method from TextFileUtils was called. Successful scenario
     */
    @Test
    public void testSuccessfulScenario() throws org.nfunk.jep.ParseException, IOException
    {
        try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class))
        {
            fileUtils.when(() -> FileUtils.readFromFileSystem(PATH)).thenReturn(CONTENT);

            Stack<Object> parameters = CollectionsUtils.newParametersStack(PATH);
            function.run(parameters);

            // Check that the content from mocked TextFileReader is returned
            assertEquals(CONTENT, parameters.pop());
        }

    }

    /**
     * Checks that the ParseException is thrown is case of an I/O exception
     */
    @Test(expected = ParseException.class)
    public void testExceptionalScenario() throws org.nfunk.jep.ParseException, IOException
    {
        try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class))
        {
            fileUtils.when(() -> FileUtils.readFromFileSystem(PATH))
                    .thenThrow(new IOException("Mocked exception"));

            Stack<Object> parameters = CollectionsUtils.newParametersStack(PATH);
            function.run(parameters);
        }
    }

}
