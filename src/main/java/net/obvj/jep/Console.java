package net.obvj.jep;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.SymbolTable;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.ColumnData;
import com.github.freva.asciitable.HorizontalAlign;

/**
 * This class implements a simple command line utility for evaluating JEP expressions.
 *
 * @author oswaldo.bapvic.jr
 */
public class Console implements Runnable
{
    private static final String PROMPT = "JEP> ";

    private enum Command
    {
        FUNCTIONS("functions")
        {
            @Override
            public void execute(JEP jep, PrintStream out)
            {
                out.println("\nAvailable functions:");
                jep.getFunctionTable().keySet().stream().sorted().forEach(alias -> out.println(" - " + alias));
            }
        },

        OPERATORS("operators")
        {
            @Override
            public void execute(JEP jep, PrintStream out)
            {
                out.println("\nAvailable operators:");
                Arrays.stream(jep.getOperatorSet().getOperators()).forEach(operator -> out.println(" - " + operator));
            }
        },

        VARIABLES("variables")
        {
            private final List<ColumnData<Pair<String, Object>>> columns = Arrays.asList(
                    new Column().header("Name")
                            .headerAlign(HorizontalAlign.CENTER)
                            .dataAlign(HorizontalAlign.RIGHT)
                            .with(Pair::getKey),

                    new Column().header("Type")
                            .headerAlign(HorizontalAlign.CENTER)
                            .dataAlign(HorizontalAlign.LEFT)
                            .with(this::getType),

                    new Column().header("Value")
                            .headerAlign(HorizontalAlign.CENTER)
                            .dataAlign(HorizontalAlign.LEFT)
                            .with(this::getValue));

            @Override
            public void execute(JEP jep, PrintStream out)
            {
                SymbolTable symbolTable = jep.getSymbolTable();

                if (symbolTable.isEmpty())
                {
                    out.println("No variable set");
                    return;
                }

                List<Pair<String, Object>> entryList = parseEntries(symbolTable);
                String result = AsciiTable.getTable(entryList, columns);
                out.println("\n" + result);
            }

            private List<Pair<String, Object>> parseEntries(SymbolTable symbolTable)
            {
                List<Pair<String, Object>> entryList = new ArrayList<>();
                symbolTable.keySet().forEach(key ->
                {
                    Object value = symbolTable.getValue(key);
                    entryList.add(Pair.of(String.valueOf(key), value));
                });

                entryList.sort(Comparator.comparing(Pair::getKey));
                return entryList;
            }

            private String getType(Pair<String, Object> pair)
            {
                Object value = pair.getValue();
                return value != null ? value.getClass().getName() : "null";
            }

            private String getValue(Pair<String, Object> pair)
            {
                return String.valueOf(pair.getValue());
            }

        };

        private String alias;

        private Command(String alias)
        {
            this.alias = alias;
        }

        public String getAlias()
        {
            return alias;
        }

        public static Optional<Command> getCommand(String alias)
        {
            return Arrays.stream(values()).filter(command -> command.getAlias().equals(alias)).findFirst();
        }

        public abstract void execute(JEP jep, PrintStream out);
    }

    protected final InputStream in;
    protected final PrintStream out;

    private JEP jep = JEPContextFactory.newContext();

    public Console()
    {
        this(null, null);
    }

    protected Console(InputStream in, PrintStream out)
    {
        this.in = ObjectUtils.defaultIfNull(in, System.in);
        this.out = ObjectUtils.defaultIfNull(out, System.out);
    }

    @Override
    public void run()
    {
        printHeader();
        try (Scanner scanner = new Scanner(in))
        {
            prompt();
            loop(scanner);
        }
        out.println("Goodbye!");

    }

    private void loop(Scanner scanner)
    {
        while (scanner.hasNext())
        {
            String line = scanner.nextLine();
            if (!StringUtils.isEmpty(line))
            {
                if (StringUtils.equalsAny(line.trim(), "exit", "quit"))
                {
                    break;
                }
                handleLine(line);
            }
            out.println();
            prompt();
        }
    }

    private void printHeader()
    {
        out.println("     __ _____ _____");
        out.println("  __|  |   __|  _  |  obvj.net");
        out.println(" |  |  |   __|   __|  JEP-DATA-EXTENSION");
        out.println(" |_____|_____|__|");
        out.println();
        out.println(" Enter your expression or type ...");
        out.println();
        out.println(" > functions  to list functions");
        out.println(" > operators  to list operators");
        out.println(" > variables  to list variables");
        out.println(" > exit/quit  to quit the console");
        out.println();
    }

    private void prompt()
    {
        out.flush();
        out.print(PROMPT);
    }

    private void handleLine(String line)
    {
        Optional<Command> command = Command.getCommand(line);
        if (command.isPresent())
        {
            command.get().execute(jep, out);
            return;
        }
        handleExpression(line);

    }

    private void handleExpression(String line)
    {
        try
        {
            Node node = jep.parse(line);
            Object result = jep.evaluate(node);
            out.println(result);
        }
        catch (Throwable exception) // Unfortunately JEP throws Error instead of Exception
        {
            handleThrowable(exception);
        }
    }

    private void handleThrowable(Throwable throwable)
    {
        out.println(throwable.getClass().getName() + ": " + throwable.getMessage());
    }

    public static void main(String[] args)
    {
        new Console().run();
    }

}
