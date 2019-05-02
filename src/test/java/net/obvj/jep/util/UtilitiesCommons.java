package net.obvj.jep.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * Common assertions for JEP utility classes
 *
 * @author oswaldo.bapvic.jr
 *
 */
public class UtilitiesCommons
{
    /**
     * Tests that no instances of an utility class are created.
     *
     * @param utilityClass the class subject to test
     * @param expectedThrowableClass the expected throwable to be checked in case the
     * constructor is called
     * @param expectedErrorMessage the expected error message to be checked in case the
     * constructor is called
     * @throws Exception in case of errors getting constructor metadata or instantiating the
     * private constructor via Reflection
     */
    public static void testNoInstancesAllowed(Class<?> utilityClass, Class<? extends Throwable> expectedThrowableClass,
            String expectedErrorMessage) throws Exception
    {
        try
        {
            Constructor<?> constructor = utilityClass.getDeclaredConstructor();
            assertTrue("Constructor is not private", Modifier.isPrivate(constructor.getModifiers()));
            constructor.setAccessible(true);
            constructor.newInstance();
            fail("Class was instantiated");
        }
        catch (InvocationTargetException ite)
        {
            Throwable cause = ite.getCause();
            assertEquals(expectedThrowableClass, cause.getClass());
            assertEquals(expectedErrorMessage, cause.getMessage());
        }
    }
}
