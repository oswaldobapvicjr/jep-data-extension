# jep-data-extension

[![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/oswaldobapvicjr/jep-data-extension/maven.yml)](https://github.com/oswaldobapvicjr/jep-data-extension/actions/workflows/maven.yml)
[![Coverage](https://img.shields.io/codecov/c/github/oswaldobapvicjr/jep-data-extension)](https://codecov.io/gh/oswaldobapvicjr/jep-data-extension)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.obvj/jep-data-extension/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.obvj/jep-data-extension)
[![javadoc](https://javadoc.io/badge2/net.obvj/jep-data-extension/javadoc.svg)](https://javadoc.io/doc/net.obvj/jep-data-extension)

**jep-data-extension** is a project that  extends JEP, the powerful mathematical expression parser and evaluator for Java, introducing data manipulation capabilities with custom functions and operators ready to use, including **RegEx**, **XPath**, **JSONPath** and **Web Services**.

> For details about JEP core features, access [JEP documentation](http://www.singularsys.com/jep/doc/html/index.html).

---

## New functions

<table>
<tr>
<th>Package</th>
<th>Name</th>
<th>Description</th>
</tr>

<tr>
<td rowspan="18"><b>String functions</b></td>
<td>camel</td>
<td>Converts a string to camel case
</td>
</tr>

<tr>
<td>concat</td>
<td>Concatenates the arguments into a string.
</td>
</tr>

<tr>
<td>endsWith</td>
<td>Returns 1 (true) if a string ends with the specified suffix.
</td>
</tr>

<tr>
<td>findMatch</td>
<td>Returns the first match of the given regular expression found inside a string.
</td>
</tr>

<tr>
<td>findMatches</td>
<td>Returns a list containing all matches of the given regular expression inside a string.
</td>
</tr>

<tr>
<td>formatString</td>
<td>Returns a formatted string according to given pattern and variable arguments.
</td>
</tr>

<tr>
<td>lpad/leftPad</td>
<td>Left-pads a string to a given size. 
</td>
</tr>

<tr>
<td>lcase/lower</td>
<td>Converts a text string to all lower-case letters.
</td>
</tr>

<tr>
<td>matches</td>
<td>Returns a 1 if the given string contains at least one match of a given RegEx.
</td>
</tr>

<tr>
<td>normalizeString</td>
<td>Normalizes a Unicode string, replacing accents and other diacritics with ASCII chars.
</td>
</tr>

<tr>
<td>proper</td>
<td>Converts a string to proper case, i.e., only the first letter in each word in upper-case.
</td>
</tr>

<tr>
<td>replace</td>
<td>Replaces all occurrences of a given string with another one.
</td>
</tr>

<tr>
<td>replaceRegex</td>
<td>Replaces all matches of a given regular expression.
</td>
</tr>

<tr>
<td>rpad/rightPad</td>
<td>Right-pads a string to a given size. 
</td>
</tr>

<tr>
<td>split</td>
<td>Splits a string into a string array based on a separating string or regular expression.
</td>
</tr>

<tr>
<td>startsWith</td>
<td>Returns 1 if a string starts a given prefix.
</td>
</tr>

<tr>
<td>trim</td>
<td>Removes leading and trailing spaces.
</td>
</tr>

<tr>
<td>ucase/upper</td>
<td>Converts a text string to all upper-case letters.
</td>
</tr>


<tr>
<td rowspan="16"><b>Date functions</b></td>
<td>now/sysdate</td>
<td>Returns the system's current date & time.
</td>
</tr>

<tr>
<td>str2date</td>
<td>Converts a string into date by trying different patterns such as RFC-3339, RFC-822 and common ISO-8601 variations.
</td>
</tr>

<tr>
<td>date2str</td>
<td>Converts a date into string using a specified format.
</td>
</tr>

<tr>
<td>daysBetween</td>
<td>Returns the number of days between two dates.
</td>
</tr>

<tr>
<td>endOfMonth</td>
<td>Returns a date corresponding to the last day of the month given a source date.
</td>
</tr>

<tr>
<td>isLeapYear</td>
<td>Returns 1 if the given date or year number is a leap year, i.e., an year with 366 days.
</td>
</tr>

<tr>
<td>year</td>
<td>Returns the year for a given date.
</td>
</tr>

<tr>
<td>quarter</td>
<td>Returns the quarter of the year for a given date.
</td>
</tr>

<tr>
<td>month</td>
<td>Returns the month for a given date, a number starting from 1 (Jan) to 12 (Dec).
</td>
</tr>

<tr>
<td>isoWeekNumber</td>
<td>Returns the ISO week number for a given date.
</td>
</tr>

<tr>
<td>weekday</td>
<td>Returns the day of the week of a date, a number from 1 (Sunday) to 7 (Saturday).
</td>
</tr>

<tr>
<td>day</td>
<td>Returns the day of the month for a given date..
</td>
</tr>

<tr>
<td>hour</td>
<td>Returns the hour of day for a given date, as a number from 0 (12AM) to 23 (11PM).
</td>
</tr>

<tr>
<td>minute</td>
<td>Returns the minute within the hour for a given date, as a number from 0 to 59.
</td>
</tr>

<tr>
<td>second</td>
<td>Returns the seconds within the minute for a given date, a number from 0 to 59.
</td>
</tr>

<tr>
<td>addDays</td>
<td>Returns the result from adding a number of days to a date.
</td>
</tr>

<tr>
<td rowspan="2"></b>Math functions</b></td>
<td>arabic</td>
<td>Converts a Roman numeral to Arabic.
</td>
</tr>

<tr>
<td>roman</td>
<td>Converts an Arabic numeral to Roman.
</td>
</tr>



<tr>
<td rowspan="2"></b>Data manipulation & filtering</b></td>
<td>xpath</td>
<td>Returns the result of the given XPath expression at the specified XML.
</td>
</tr>

<tr>
<td>jsonpath</td>
<td>Returns the result of the given JSONPath expression at the specified JSON.
</td>
</tr>



<tr>
<td rowspan="4"></b>Statistical functions</b></td>
<td>average</td>
<td>Returns the average of the elements inside a given array, JSON array or collection (including valid representations).
</td>
</tr>

<tr>
<td>count</td>
<td>Returns the number of elements inside a given array, JSON array or collection (including valid string representations).
</td>
</tr>

<tr>
<td>max</td>
<td>Returns the largest value in a given array, JSON array or collection (including valid string representations).
</td>
</tr>

<tr>
<td>min</td>
<td>Returns the smallest value in a given array, JSON array or collection (including valid string representations).
</td>
</tr>



<tr>
<td rowspan="1"></b>Random</b></td>
<td>uuid</td>
<td>Produces a randomly generated type 4 UUID (Universally-unique Identifier) string.
</td>
</tr>



<tr>
<td rowspan="8"><b>Utility functions</b></td>
<td>distinct</td>
<td>Returns a list consisting of the distinct elements of a given list, array or JSON array.
</td>
</tr>

<tr>
<td>getEnv</td>
<td>Returns the value of an environment variable.
</td>
</tr>

<tr>
<td>getSystemProperty</td>
<td>Returns the value of system property.
</td>
</tr>

<tr>
<td>isDecimal</td>
<td>Returns 1 if the given parameter is a number (including strings) containing decimals.
</td>
</tr>

<tr>
<td>isEmpty</td>
<td>Returns 1 if a given parameter is either null or an empty String, JSON or collection.
</td>
</tr>

<tr>
<td>isInteger</td>
<td>Returns 1 if the given number (or valid string) represents a whole number, i.e, which lacks decimals.
</td>
</tr>

<tr>
<td>readFile</td>
<td>Returns the content of a text file in the file system.
</td>
</tr>

<tr>
<td>typeOf/class</td>
<td>Returns the Java type associated with the given variable.
</td>
</tr>



<tr>
<td rowspan="5"><b>Cryptography functions</b></td>
<td>md5</td>
<td>Computes the MD5 hash of the given string and transforms the binary result into a hexadecimal string.
</td>
</tr>

<tr>
<td>sha1</td>
<td>Computes the SHA1 hash of the given string and transforms the binary result into a hexadecimal string.
</td>
</tr>

<tr>
<td>sha256</td>
<td>Computes the SHA-256 hash of the given string and transforms the binary result into a hexadecimal string.
</td>
</tr>

<tr>
<td>toBase64</td>
<td>Encodes a string to Base64 encoding scheme.
</td>
</tr>

<tr>
<td>fromBase64</td>
<td>Decodes a Base64 encoded string.
</td>
</tr>



<tr>
<td rowspan="6"><b>Web Services</b></td>
<td>httpGet</td>
<td>Returns data from a Web Service or RESTful API, as string.
</td>
</tr>

<tr>
<td>http</td>
<td>Invokes a specific method towards a Web Service/REST API.
</td
</tr>

<tr>
<td>httpHeader</td>
<td>This function groups a variable number of HTTP header entries for usage with other HTTP functions.
</td>
</tr>

<tr>
<td>basicAuthorizationHeader</td>
<td>Generates a basic authorization header from a given username and password.
</td>
</tr>

<tr>
<td>httpStatusCode</td>
<td>Returns the HTTP status code of a given WebServiceResponse.
</td>
</tr>

<tr>
<td>httpResponse</td>
<td>Returns the HTTP response body from given WebServiceResponse, as string.
</td>
</tr>

</table>

> ℹ️ [Find examples in the wiki.](https://github.com/oswaldobapvicjr/jep-data-extension/wiki)

---

## Custom operators

JEP offers a good set of operators for common arithmetic and logical operations, for example: addition (`+`), subtraction (`-`), multiplication (`*`), division (`/`), modulus (`%`), power (`^`), "Boolean And" (`&&`), "Boolean Or" (`||`), and "Boolean Not" (`!`). These operators can be used when required and are supported by JEP.

### Comparative operators

Standard JEP relational operators accept only numerical variables (including string representation of non-complex, numerical values). These operators have been overloaded to allow comparison of dates, as well as string representations of valid dates:

- Less than (`<`)
- Less than or equal to (`<=`)
- Greater than (`>`)
- Greater than or equal to (`<=`)
- Equal (`==`)
- Not equal (`!=`)

This allows the usage of dates in expressions that can be evaluated to `true` or `false`. For example:  

```java
if("2017-03-11T10:15:00:123Z" < now(), "past", "not past")
```

### Element operator

Standard "element" operator (`[]`) was overloaded to support data extraction out of JSON arrays and other collections by index.

```java
jsonArray[1] //extracts the first element of the given jsonArray
```

> **Note:** Also achievable using the function `get(jsonArray, 1)`.

---

## How to include it

If you are using Maven, add **jep-data-extension** as a dependency to your pom.xml file.

```xml
<dependency>
    <groupId>net.obvj</groupId>
    <artifactId>jep-data-extension</artifactId>
    <version>1.0.6</version>
</dependency>
```

If you use other dependency managers (such as Gradle, Grape, Ivy, etc.) click [here](https://search.maven.org/artifact/net.obvj/jep-data-extension).

---

## How to use it

### Example 1: replacing characters from a text variable using the `JEPContextFactory`

1. Add `jep-data-extensions` to your class path

2. Put your source variables in a map:

    ```java
    Map<String, Object> myVariables = new HashMap<>();
    myVariables.put("myText", "foo");
    ```

3. Use the `JEPContextFactory` class to create an extended JEP object with all available extensions:

    ````java
    JEP jep = JEPContextFactory.newContext(myVariables);
    ````

    > **Note:** Alternatively, you may use the `JEPContextFactory.newContext()` to receive a new context with no initial variables. You may add them later, calling `jep.addVariable(String, Object)`.

4. Parse your expression:

    ````java
    Node node = jep.parseExpresion("replace(myText, \"oo\", \"ee\")");
    ````

5. Evaluate the expression with JEP's `evaluate` method:

    ````java
    String result = (String) jep.evaluate(node); //result = "fee"
    ````

6. Alternatively, you can use the assignment operator inside an expression. The new variable will be available in JEP context for reuse and can be retrieved using the `getVarValue` method:

    ````java
    Node node = jep.parseExpression("myText = replace(\"fear\", \"f\", \"d\")"));
    jep.evaluate(node);
    String result = (String) jep.getVarValue("myText"); //result = "dear"
    ````



### Example 2: comparing dates using the `ExpressionEvaluatorFacade`

The `ExpressionEvaluatorFacade` is a convenient object to quickly parse a single expression. A single call to the `evaluate` method will create a new evaluation context and return the expression result directly. It accepts a map with initial variables for use in the operation.

1. Put your source variables in a map:

    ```java
    Map<String, Object> myVariables = new HashMap<>();
    myVariables.put("date1", "2018-12-20T09:10:00.123456789Z");
    ```

2. Evaluate your expression:

    ````java
    String expression = "if(date1 < now(), \"overdue\", \"not overdue\")";
    String result = (String) ExpressionEvaluatorFacade.evaluate(expression, myVariables); 
    //result = "overdue"
    ````



### Example 3: Consuming data from a RESTful API using the Evaluation Console

1. Run `jep-data-extension` JAR file from a terminal:

    ```
    $ java -jar jep-data-extension-1.0.2.jar
    ```

2. Consume the RESTful API with the `httpGet` function:

    ```
    JEP > dateTime = httpGet("http://date.jsontest.com/")
    {
       "time": "08:31:19 PM",
       "date": "05-12-2012"
    }
    ```

3. Filter the JSON using a JsonPath expression that extracts the date field:

    ```
    JEP > date = jsonpath(dateTime, "$.date")
    05-12-2012
    ```

3. Check the type of the generated `date` variable:

    ```
    JEP > class(date)
    java.lang.String
    ```

4. Convert the string to Date:

    ```
    JEP > date = str2date(date, "dd-MM-yyyy")
    Wed Dec 05 00:00:00 BRST 2012
    ```

5. Extract the week number from the converted date:

    ```
    JEP > isoWeekNumber(date)
    49
    ```
