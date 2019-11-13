package net.obvj.jep;

/**
 * This class implements a simple command line utility for evaluating JEP expressions.
 * 
 * <pre>
*   Usage: java net.obvj.jep.Console [expression]
 * </pre>
 * 
 * If an argument is passed, it is interpreted as an expression and evaluated. Otherwise,
 * a prompt is printed, and the user can enter expressions to be evaluated.
 * 
 * @author oswaldo.bapvic.jr
 */
public class Console extends org.lsmp.djepExamples.Console
{
    private static final String CUSTOM_APPLET_INFO = "jep-data-extension (1.0)\n" + "oswaldo.bapvic.jr - 2019\n\n";

    /**
     * Set up JEP Console with jep-data-extension functions, operators and preferences
     */
    @Override
    public void initialise()
    {
        super.j = JEPContextFactory.newContext();
    }

    /**
     * Append custom information to original Console information
     */
    @Override
    public String getAppletInfo()
    {
        return CUSTOM_APPLET_INFO + super.getAppletInfo();
    }

    public static void main(String args[])
    {
        new Console().run(args);
    }

}
