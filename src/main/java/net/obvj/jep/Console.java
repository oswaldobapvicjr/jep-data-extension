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
    /**
     * Set up JEP Console with jep-data-extension functions, operators and preferences
     */
    @Override
    public void initialise()
    {
        super.j = JEPContextFactory.newContext();
    }

    public static void main(String[] args)
    {
        new Console().run(args);
    }

}
