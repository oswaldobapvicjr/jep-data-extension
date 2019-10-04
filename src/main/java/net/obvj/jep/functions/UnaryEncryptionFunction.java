package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.EncryptionUtils;

/**
 * A JEP function that accepts one parameter and converts it into an encrypted string
 *
 * @author oswaldo.bapvic.jr
 */
public class UnaryEncryptionFunction extends PostfixMathCommand implements MultiStrategyCommand
{
    public enum EncryptionAlgorithm
    {
        @Function("md5")
        MD5
        {
            @Override
            String encrypt(String content)
            {
                return EncryptionUtils.md5(content);
            }
        },

        @Function("sha1")
        SHA1
        {
            @Override
            String encrypt(String content)
            {
                return EncryptionUtils.sha1(content);
            }
        },

        @Function("sha256")
        SHA256
        {
            @Override
            String encrypt(String content)
            {
                return EncryptionUtils.sha256(content);
            }
        };

        abstract String encrypt(String content);
    }

    private final EncryptionAlgorithm encryptionAlgorithm;

    /**
     * Builds this function with a fixed number of three parameters and the given encryption
     * algorithm
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
        String result = encryptionAlgorithm.encrypt(sourceString);
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
