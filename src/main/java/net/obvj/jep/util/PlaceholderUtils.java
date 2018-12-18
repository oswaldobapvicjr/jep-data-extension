package net.obvj.jep.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * A utility class for working with place-holders in strings
 *
 * @author oswaldo.bapvic.jr
 */
public class PlaceholderUtils
{
    private static final Pattern PATTERN_VARIABLE_PLACEHOLDER = Pattern.compile("(\\$\\{)[\\w]+(\\})");
    private static final Pattern PATTERN_VARIABLE_NAME = Pattern.compile("(?<=\\$\\{)[\\w]+(?=\\})");

    private PlaceholderUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Attempts to match the entire region against the pattern.
     *
     * @param string  The string to attempt the match.
     * @param pattern The pattern to use.
     *
     * @return {@code true} if matches, otherwise {@code false};
     */
    public static boolean matches(String string, Pattern pattern)
    {
        if (StringUtils.isEmpty(string)) return false;

        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * Returns the matches found for a given string using regular expression.
     *
     * @param string  The string to look for matches.
     * @param pattern The pattern to use.
     * @return A set of matches within the string.
     */
    public static Set<String> findMatches(String string, Pattern pattern)
    {
        if (StringUtils.isEmpty(string)) return Collections.emptySet();

        Set<String> expressions = new HashSet<>();
        Matcher matcher = pattern.matcher(string);

        while (matcher.find())
        {
            expressions.add(matcher.group());
        }
        return expressions;
    }

    /**
     * Returns the first match found for a given string using regular expression.
     *
     * @param string  The string to look for matches.
     * @param pattern The pattern to use.
     * @return The first match found within the string.
     */
    public static String findMatch(String string, Pattern pattern)
    {
        if (StringUtils.isEmpty(string)) return StringUtils.EMPTY;

        Matcher matcher = pattern.matcher(string);
        if (matcher.find())
        {
            return matcher.group();
        }

        return StringUtils.EMPTY;
    }

    /**
     * Checks if the string has place-holders
     *
     * @param string the given string to be replaced
     * @return a boolean value which means it has or not place-holders.
     */
    public static boolean hasPlaceHolders(String string)
    {
        Set<String> placeholders = findPlaceholders(string);
        return !placeholders.isEmpty();
    }

    /**
     * Method that finds the place-holders of a given string.
     *
     * @param string the given string to be used to find place-holders
     * @return the string set with the found place-holders
     */
    public static Set<String> findPlaceholders(String string)
    {
        return findMatches(string, PATTERN_VARIABLE_PLACEHOLDER);
    }

    /**
     * Method to replace place-holders with variables
     *
     * @param string    the given string to be replaced
     * @param variables A map containing place-holder names and value that will be used in
     *                  replacement.
     * @return The string with the replaced place-holders.
     */
    public static String replacePlaceholders(String string, Map<String, Object> variables)
    {
        String resultString = string;
        Set<String> placeholders = findPlaceholders(string);

        for (String placeholder : placeholders)
        {
            String variableName = findMatch(placeholder, PATTERN_VARIABLE_NAME);
            resultString = resultString.replaceAll(placeholder, String.valueOf(variables.get(variableName)));
        }
        return resultString;
    }

}
