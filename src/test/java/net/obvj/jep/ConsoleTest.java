package net.obvj.jep;

import static net.obvj.junit.utils.matchers.AdvancedMatchers.containsAll;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Test;

/**
 * Tests for the {@link Console} class.
 *
 * @author oswaldo.bapvic.jr
 */
public class ConsoleTest
{
    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    private Console console;

    private void runCommand(String line)
    {
        InputStream in = new ByteArrayInputStream(line.getBytes());
        PrintStream out = new PrintStream(output);
        console = new Console(in, out);
        console.run();
    }

    @After
    public void quit()
    {
        if (console != null)
        {
            runCommand("quit");
        }
    }

    @Test
    public void run_functions_keyFunctionsFound()
    {
        runCommand("functions");
        assertThat(output.toString(),
                containsAll("concat", "sysdate", "xpath", "max", "uuid", "sha256", "httpGet", "arabic"));
    }

    @Test
    public void run_operators_keyOperatorsFound()
    {
        runCommand("operators");
        assertThat(output.toString(), containsAll("==", ">=", "<=", "+", "*", "-", "/", "%"));
    }

    @Test
    public void run_variablesAndNoVariableSet_noVariableSet()
    {
        runCommand("variables");
        assertThat(output.toString(), containsAll("No variable set"));
    }

    @Test
    public void run_variablesAfterOneVariableSet_validContent()
    {
        runCommand("test=123\nvariables");
        assertThat(output.toString(), containsAll("test | java.lang.Double | 123.0"));
    }

    @Test
    public void run_variablesAfterTwoVariablesSet_validContents()
    {
        runCommand("dblVar=123\nstrVar=\"abc\"\nvariables");
        assertThat(output.toString(), containsAll("dblVar | java.lang.Double | 123.0",
                                                  "strVar | java.lang.String | abc"));
    }

    @Test
    public void run_variablesAfterNullVariableSet_variableContent()
    {
        runCommand("test=null\nvariables");
        assertThat(output.toString(), containsAll("test | null | null"));
    }

    @Test
    public void constructor_default_standardInputAndOutput()
    {
        console = new Console();
        assertThat(console.in, equalTo(System.in));
        assertThat(console.out, equalTo(System.out));
    }

}
