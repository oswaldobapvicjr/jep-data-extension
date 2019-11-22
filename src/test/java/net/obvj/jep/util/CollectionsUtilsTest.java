package net.obvj.jep.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class CollectionsUtilsTest
{
    private static final String KEY2_EQUAL_VALUE2 = "key2=value2";
    private static final String KEY1_COLON_VALUE1 = "key1:value1";
    private static final String KEY1_EQUAL_VALUE1 = "key1=value1";
    private static final String KEY1 = "key1";
    private static final String KEY2 = "key2";
    private static final String VALUE1 = "value1";
    private static final String VALUE2 = "value2";
    private static final String EMPTY_STRING = "";

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

    @Test
    public void parseMapEntryWithValidStringSeparatedByEqualSign()
    {
        Map.Entry<String, String> entry = CollectionsUtils.parseMapEntry(KEY1_EQUAL_VALUE1);
        assertThat(entry.getKey(), is(KEY1));
        assertThat(entry.getValue(), is(VALUE1));
    }

    @Test
    public void parseMapEntryWithValidStringSeparatedByColon()
    {
        Map.Entry<String, String> entry = CollectionsUtils.parseMapEntry(KEY1_COLON_VALUE1);
        assertThat(entry.getKey(), is(KEY1));
        assertThat(entry.getValue(), is(VALUE1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseMapEntryWithAStringContainingNoEqualSign()
    {
        CollectionsUtils.parseMapEntry(KEY1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseMapEntryWithAnEmptyString()
    {
        CollectionsUtils.parseMapEntry(EMPTY_STRING);
    }

    @Test
    public void parseMapEntryWithAStringContainingTheEqualSignButNoValue()
    {
        Map.Entry<String, String> entry = CollectionsUtils.parseMapEntry("key1=");
        assertThat(entry.getKey(), is(KEY1));
        assertThat(entry.getValue(), is(EMPTY_STRING));
    }

    @Test
    public void parseMapEntryWithAValidStringContainingSpaces()
    {
        Map.Entry<String, String> entry = CollectionsUtils.parseMapEntry(" key1 =   value1 ");
        assertThat(entry.getKey(), is(KEY1));
        assertThat(entry.getValue(), is(VALUE1));
    }

    @Test
    public void parseMapEntryWithAValidStringContainingMoreThanOneSeparatorSign()
    {
        Map.Entry<String, String> entry = CollectionsUtils.parseMapEntry("key1=value1=and=value2");
        assertThat(entry.getKey(), is(KEY1));
        assertThat(entry.getValue(), is("value1=and=value2"));
    }

    @Test
    public void asMapWithAValidEntryAsString()
    {
        Map<String, String> map = CollectionsUtils.asMap(KEY1_EQUAL_VALUE1);
        assertThat(map.size(), is(1));
        assertThat(map.containsKey(KEY1), is(true));
        assertThat(map.get(KEY1), is(VALUE1));
    }

    @Test
    public void asMapWithTwoValidEntriesAsString()
    {
        Map<String, String> map = CollectionsUtils.asMap(KEY1_EQUAL_VALUE1, KEY2_EQUAL_VALUE2);
        assertThat(map.size(), is(2));
        assertThat(map.get(KEY1), is(VALUE1));
        assertThat(map.get(KEY2), is(VALUE2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void asMapWithEmptyEntry()
    {
        CollectionsUtils.asMap(EMPTY_STRING);
    }

    @Test
    public void asMapWithEmptyStringArray()
    {
        assertThat(CollectionsUtils.asMap(new String[] {}).isEmpty(), is(true));
    }
    
    @Test
    public void distinctListWithAValidList()
    {
        List<Object> sourceList = Arrays.asList(KEY1, KEY2, KEY2, VALUE1, VALUE2, KEY1, 0, 1.1, 0);
        List<Object> expectedList = Arrays.asList(KEY1, KEY2, VALUE1, VALUE2, 0, 1.1);
        assertThat(CollectionsUtils.distincList(sourceList), is(expectedList));
    }
}
