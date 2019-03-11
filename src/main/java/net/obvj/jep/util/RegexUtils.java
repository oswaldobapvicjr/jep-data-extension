package net.obvj.jep.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * A utility class for working with regular expressions in strings
 *
 * @author oswaldo.bapvic.jr
 */
public class RegexUtils
{
    protected static final Pattern PATTERN_UNIX_LIKE_VARIABLE_PLACEHOLDER = Pattern.compile("(\\$\\{)[\\w]+(\\})");
    protected static final Pattern PATTERN_UNIX_LIKE_VARIABLE_NAME = Pattern.compile("(?<=\\$\\{)[\\w]+(?=\\})");

    private RegexUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Returns true if the given string contains at least one match of the given pattern
     *
     * @param string  The string to be matched
     * @param pattern The pattern to use
     *
     * @return {@code true} if matches, otherwise {@code false}
     */
    public static boolean matches(String string, Pattern pattern)
    {
        if (StringUtils.isEmpty(string)) return false;

        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }

    /**
     * Returns true if the given string contains at least one match of the given regular
     * expression
     *
     * @param string The string to be matched
     * @param regex  The regular expression to use
     *
     * @return {@code true} if matches, otherwise {@code false}
     */
    public static boolean matches(String string, String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        return matches(string, pattern);
    }

    /**
     * Returns the matches found for a given string using regular expression
     *
     * @param string  The string to be matched
     * @param pattern The pattern to use
     * @return A list containing all matches of the given pattern within the string
     */
    public static List<String> findMatches(String string, Pattern pattern)
    {
        if (StringUtils.isEmpty(string)) return Collections.emptyList();

        List<String> expressions = new ArrayList<>();
        Matcher matcher = pattern.matcher(string);

        while (matcher.find())
        {
            expressions.add(matcher.group());
        }
        return expressions;
    }

    /**
     * Returns the matches found for a given string using regular expression
     *
     * @param string The string to be matched
     * @param regex  The regular expression to use
     * @return A list containing all matches of the given regular expression within the string
     */
    public static List<String> findMatches(String string, String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        return findMatches(string, pattern);
    }

    /**
     * Returns the first match found for a given string using regular expression
     *
     * @param string  The string to be matched
     * @param pattern The pattern to use
     * @return The first match found for the given pattern within the string
     */
    public static String firstMatch(String string, Pattern pattern)
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
     * Returns the first match found for a given string using regular expression
     *
     * @param string The string to be matched
     * @param regex  The regular expression to use
     * @return The first match of the given regular expression found within the string
     */
    public static String firstMatch(String string, String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        return firstMatch(string, pattern);
    }

    /**
     * Replaces all matches found for the given regular expression with a replacement string
     *
     * @param string      The string to be matched
     * @param regex       The regular expression to use
     * @param replacement The replacement string
     * @return The first match found for the given pattern within the string
     */
    public static String replaceMatches(String string, String regex, String replacement)
    {
        if (StringUtils.isEmpty(string)) return string;

        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(string).replaceAll(replacement);
    }

    /**
     * Checks if the string has place-holders for variables in Unix-like pattern
     *
     * @param string the string to be replaced
     * @return a boolean value which means it has or not place-holders in Unix-like pattern
     */
    public static boolean hasUnixLikeVariablePlaceholders(String string)
    {
        List<String> placeholders = findUnixLikeVariablePlaceholders(string);
        return !placeholders.isEmpty();
    }

    /**
     * Method that finds the place-holders in Unix-like pattern for the given string
     *
     * @param string the string to be used to find place-holders
     * @return the string set with the found place-holders
     */
    public static List<String> findUnixLikeVariablePlaceholders(String string)
    {
        return findMatches(string, PATTERN_UNIX_LIKE_VARIABLE_PLACEHOLDER);
    }

    /**
     * Method that finds the variable name given a string containing a place-holder
     *
     * @param string the string to be used to find the variable name
     * @return the the variable name, if found
     */
    private static String findUnixLikeVariableName(String placeholder)
    {
        return firstMatch(placeholder, PATTERN_UNIX_LIKE_VARIABLE_NAME);
    }

    /**
     * Method to replace place-holders with variables
     *
     * @param string    The given string to be replaced
     * @param variables A map containing place-holder names and value that will be used in
     *                  replacement
     * @return The string with the replaced place-holders
     */
    public static String replacePlaceholdersWithVariables(String string, Map<String, Object> variables)
    {
        if (StringUtils.isEmpty(string)) return string;

        String resultString = string;
        List<String> placeholders = findUnixLikeVariablePlaceholders(string);

        for (String placeholder : placeholders)
        {
            String variableName = findUnixLikeVariableName(placeholder);
            resultString = resultString.replace(placeholder, String.valueOf(variables.get(variableName)));
        }
        return resultString;
    }

}
