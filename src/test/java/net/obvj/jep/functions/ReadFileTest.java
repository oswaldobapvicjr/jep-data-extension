package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.nfunk.jep.ParseException;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import net.obvj.jep.util.CollectionsUtils;
import net.obvj.jep.util.FileUtils;

/**
 * Unit tests for the {@link ReadFile} function
 *
 * @author oswaldo.bapvic.jr
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(FileUtils.class)
public class ReadFileTest
{
    private static final String PATH = "/tmp/data.json";
    private static final String CONTENT = "test123";

    // Test subject
    private static ReadFile function = new ReadFile();

    /**
     * Setup test objects before each test
     */
    @Before
    public void setup()
    {
        PowerMockito.mockStatic(FileUtils.class);
    }

    /**
     * Checks that the correct method from TextFileUtils was called. Successful scenario
     */
    @Test
    public void testSuccessfulScenario() throws org.nfunk.jep.ParseException, IOException
    {
        PowerMockito.when(FileUtils.readFromFileSystem(PATH)).thenReturn(CONTENT);

        Stack<Object> parameters = CollectionsUtils.newParametersStack(PATH);
        function.run(parameters);

        // Check that the content from mocked TextFileReader is returned
        assertEquals(CONTENT, parameters.pop());

        // Check that the correct method from the mock was called once
        PowerMockito.verifyStatic(FileUtils.class, BDDMockito.times(1));
        FileUtils.readFromFileSystem(PATH);
    }

    /**
     * Checks that the ParseException is thrown is case of an I/O exception
     */
    @Test(expected = ParseException.class)
    public void testExceptionalScenario() throws org.nfunk.jep.ParseException, IOException
    {
        PowerMockito.when(FileUtils.readFromFileSystem(PATH)).thenThrow(new IOException("Mocked exception"));

        Stack<Object> parameters = CollectionsUtils.newParametersStack(PATH);
        function.run(parameters);
    }

}
