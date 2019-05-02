package net.obvj.jep.util;

import org.junit.Test;

public class CollectionsUtilsTest
{
    /**
     * Tests that no instances of this utility class are created
     *
     * @throws Exception in case of error getting constructor metadata or instantiating the
     *                   private constructor via Reflection
     */
    @Test
    public void testNoInstancesAllowed() throws Exception
    {
        UtilitiesCommons.testNoInstancesAllowed(CollectionsUtils.class, IllegalStateException.class, "Utility class");
    }
}
