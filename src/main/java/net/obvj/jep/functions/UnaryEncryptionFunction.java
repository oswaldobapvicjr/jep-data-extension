package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.EncryptionUtils;

/**
 * An "abstract" function that accepts one object as parameter and converts it into an
 * encrypted string.
 *
 * @author oswaldo.bapvic.jr
 */
public class UnaryEncryptionFunction extends PostfixMathCommand implements MultiStrategyCommand
{
    /**
     * Defines particular strategies for string encryption, such as the encryption algorithm.
     */
    public enum EncryptionAlgorithm
    {
        @Function("sha256")
        SHA256
        {
            @Override
            String execute(String content)
            {
                return EncryptionUtils.sha256(content);
            }
        },

        @Function("toBase64")
        TO_BASE64
        {
            @Override
            String execute(String content)
            {
                return EncryptionUtils.toBase64(content);
            }
        },

        @Function("fromBase64")
        FROM_BASE64
        {
            @Override
            String execute(String content)
            {
                return EncryptionUtils.fromBase64(content);
            }
        };

        abstract String execute(String content);
    }

    private final EncryptionAlgorithm encryptionAlgorithm;

    /**
     * Builds this function with a fixed number of three parameters and the given encryption
     * algorithm.
     *
     * @param encryptionAlgorithm the {@link EncryptionAlgorithm} to be set
     */
    public UnaryEncryptionFunction(EncryptionAlgorithm encryptionAlgorithm)
    {
        numberOfParameters = 1;
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommand#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        Object arg1 = stack.pop();
        String sourceString = arg1.toString();
        String result = encryptionAlgorithm.execute(sourceString);
        stack.push(result);
    }

    /**
     * @see net.obvj.jep.functions.MultiStrategyCommand#getStrategy()
     */
    @Override
    public Object getStrategy()
    {
        return encryptionAlgorithm;
    }

}
