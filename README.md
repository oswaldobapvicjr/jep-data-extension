# jep-data-extension

[![Build Status](https://travis-ci.org/oswaldobapvicjr/jep-data-extension.svg?branch=master)](https://travis-ci.org/oswaldobapvicjr/jep-data-extension)
[![Coverage Status](https://coveralls.io/repos/github/oswaldobapvicjr/jep-data-extension/badge.svg?branch=master)](https://coveralls.io/github/oswaldobapvicjr/jep-data-extension?branch=master)

**jep-data-extension** is a library that  extends JEP, a powerful mathematical expression parser and evaluator for Java, introducing data manipulation capabilities with custom data functions and operators, including 
**RegEx**, **XPath** and **JSONPath**.

> For details about JEP core functionality, click [here](http://www.singularsys.com/jep/doc/html/index.html). 

---

## String functions

### Concatenation

Concatenates the elements passed to as arguments into a string. The function supports concatenation of strings and numbers.

```java
concat("{id:", 123, "}") //result: "{id:123}"
```

> **Note:** String concatenation can also be achieved using the Addition operator (+), provided that all arguments are string variables or literals. For example: "a" + "b"


### Ends with

Returns 1 (true) if the string received in the 1st parameter ends with the suffix specified in the 2nd parameter (case-sensitive).

```java
endsWith("abcdef", "def") //result: 1.0
```


### Find match

Returns the first match of the given regular expression found inside a string.

```java
findMatch("user@domain.com", "(?<=@)[^.]+(?=\.)") //result: "domain"
```


### Find matches

Returns a list containing all matches of the given regular expression found inside a string.

```java
findMatches("Sample tweet #java #jep", "([#][A-z]+)") //result: ["#java", "#jep"]
```

### Lower

Converts a text string to all lower-case letters.

```java
lower("STRING") //result: "string"
```

### Matches

Returns a 1 (true) if the given string contains at least one match of the given regular expression or 0 (false) if not.

```java
matches("user@domain.com", "(?<=@)[^.]+(?=\.)") //result: 1.0
```

### Normalize String

Normalizes a Unicode string, replacing accents and other diacritics with ASCII characters. 

```java
normalizeString("ações") //result: "acoes"
```

### Replace

Returns a new string after replacing all occurrences of the search criteria within the original string with the replacement argument.

```java
replace("foo-boo", "oo", "ee") //result: "fee-bee"
```

### Replace RegEx

Returns a new string after replacing all matches of the given regular expression with the replacement argument.

```java
replaceRegex("file1.json", "(\\.\\w+$)", "") //result: "file1"
```

### Starts with

Returns 1 (true) if the string received in the 1st parameter starts with the prefix specified in the 2nd parameter (case-sensitive).

```java
startsWith("abcdef", "abc") //result: 1.0
```


### Trim

Removes leading and trailing spaces.

```java
trim("   string   ") //result: "string"
```

### Upper

Converts a text string to all upper-case letters.

```java
upper("string") //result: "STRING"
```

---

## Date functions

### System current date

Returns the system's current date & time, with precision of milliseconds.

```java
 now()
```

### String to date conversion

Parses a string representing a date by trying different parse patterns, supporting RFC-3339, RFC-822 and a set of common ISO-8601 variations. This function can be the fastest choice when handling RFC-3339 dates. Also useful to convert from other formats when the parse pattern is heterogeneous or unknown. 

```java
str2date("2015-10-03T08:00:01.123Z")
```

### String to date conversion with user-defined pattern

Parses a string representing a date with user-defined pattern. This can be the fastest approach when the date format is known.

```java
str2date("2015-10-03", "YYYY-MM-DD")
```

> **Note:** This function also supports variable parse pattern arguments, so it will try each pattern specified until it finds one that converts the source string to a valid date.


### Date to string formatting

Converts a date into string using the specified format.

```java
date2str(date1, "YYYY-MM-DD'T'HH:mm:ssZZ")
```

### Days between dates

Returns the number of days between two dates (or valid date representations as string).

```java
daysBetween(date1, date2)
```

### Is leap year

Returns 1 (true) if the given argument is a leap year, that is, an year with 366 days, or 0 (false) if not.
This function accepts an year (Number), a Date or a valid date representation as string in RFC-3339 format. 

```java
isLeapYear(date1)
```

### Year

Returns the year for a given date.

```java
year("2017-03-11T20:15:00:123Z") //result: 2017.0
```

### Quarter

Returns the quarter of the year, for a given date, as a number from 1 to 4.

```java
quarter("2017-03-11T20:15:00:123Z") //result: 1.0
```

### Month

Returns the month for a given date, a number starting from 1 (January) to 12 (December).

```java
month("2017-03-11T20:15:00:123Z") //result: 3.0
```

### ISO week number

Returns the ISO week number in the year for a given date.
According to the standard, the first week of an year will be the first one with a minimum of 4 days, starting with Monday.
This function accepts Dates or valid date representation as string in RFC-3339 format. 

```java
isoWeekNumber("2017-03-11T20:15:00:123Z") //result: 10.0
```

### Day

Returns the day of the month for a given date. The first day of the month is 1.

```java
day("2017-03-11T20:15:00:123Z") //result: 11.0
```

### Hour

Returns the hour of day, for a given date, as a number from 0 (12:00 AM) to 23 (11:00 PM).

```java
hour("2017-03-11T20:15:00:123Z") //result: 20.0
```

### Minute

Returns the minute within the hour, for a given date, as a number from 0 to 59.

```java
minute("2017-03-11T20:15:00:123Z") //result: 15.0
```

### Second

Returns the second within the minute, for a given date, as a number from 0 to 59.

```java
second("2017-03-11T20:15:00:123Z") //result: 0.0
```

### Millisecond

Returns the milliseconds within the second, for a given date.

```java
millisecond("2017-03-11T20:15:00:123Z") //result: 123.0
```

---

## Database functions

### Database Lookup

Executes an SQL query onto a relation data-source to retrieve data (only select statements allowed).

```java
dblookup("dataSource1", "SELECT b.name FROM book b WHERE b.id = '247dc019'")
```

> **Note:** Not yet implemented

---

## Data manipulation & filtering functions

### XPath

Returns the evaluation result of the given XPath expression for the specified XML object.

```java
xpath(xmlObject1, "/bookstore/book[@category='fiction']/title/text()")
```

> **Note:** This function currently supports XPath version 1.0 only. For additional details, refer to the [W3C's XML Path Language Version 1.0](https://www.w3.org/TR/1999/REC-xpath-19991116/) page.

### JSONPath

Returns the evaluation result of the given JSONPath expression for the specified JSON object.

```java
jsonpath(jsonObject1, "$.phoneNumbers[:1].type")
```

> **Note:** For additional details, see [Jayway's JsonPath](https://github.com/json-path/JsonPath) project.

---

## Statistical functions

### Average

Returns the average (arithmetic mean) of the elements inside the given array. It also accepts JSON arrays, Java Collections and valid string representations of JSON arrays, provided that the collection contains only number elements. 

```java
average(collection1)
```

> **Note**: Not yet implemented

### Count

Returns the number of elements inside the given array. It also accepts JSON arrays, Java Collections and valid string representations of JSON arrays. 

If a regular object (i.e., not a collection) is passed to the function, the result will always be 1 (one); if a null or empty object is passed to this function, the result will always be 0 (zero).

```java
count(collection1)
```

### Max

Returns the largest value in the given array. It also accepts JSON arrays, Java Collections, and valid string representations of JSON arrays.

```java
max(collection1)
```

> **Note:** Supported data types: numbers, dates and string representations of date in RFC-3339 format.

### Min

Returns the smallest value in the given array or set. It also accepts JSON arrays, Java Collections, and valid string representations of JSON arrays.

```java
min(array1)
```

> **Note:** Supported data types: numbers, dates and string representations of date in RFC-3339 format.

---

## Random numbers

### UUID

Produces a randomly generated type 4 UUID (Universally-unique Identifier) string. E.g.: `"247dc019-7d5a-465a-956d-7cada045ceb3"`

```java
uuid()
```

---

## Utility functions

### Get Environment Variable

Returns the value of an environment variable associated with the given key in the Operating System.

```java
getEnv("TEMP")
```

### Get Property

Returns the value of property associated with the given key in the given properties file.

```java
getProperty("integration.properties", "database-host")
```

> **Note:** Not yet implemented

### Get System Property

Returns the value of system property associated with the given key.

```java
getSystemProperty("os.name")
```

### Is Empty

Returns 1 (true) if the given parameter is either a null object or an empty String, JSON or Collection.

If a given string can be parsed as JSON object or array, it will be first converted into JSON, then its structure will be evaluated for emptiness.

```java
isEmpty(object1)
```


### Read file

Returns the content of a text file in the file system.

```java
readFile("/tmp/data.json")
```


### Type of

Returns the Java type associated with the given variable 

```java
typeOf("text") //result: "java.lang.String"
```

---

## Cryptography functions

### MD5

Computes the MD5 hash of the given string and transforms the binary result into a hexadecimal lower case string.

```java
md5("asd") //result: "7815696ecbf1c96e6894b779456d330e"
```

### SHA1

Computes the SHA1 hash of the given string and transforms the binary result into a hexadecimal lower case string.

```java
sha1("dsasd") //result: "2fa183839c954e6366c206367c9be5864e4f4a65"
```

### SHA-256

Computes the SHA-256 hash of the given string and transforms the binary result into a hexadecimal lower case string.

```java
sha256("hello") //result: "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824"
```

---

## Web Services

### HTTP Get

Returns data from a Web Service or RESTful API.

```java
httpGet("http://sampleservice.com/data/v1/wheather")
```

> **Note:** Not yet implemented

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

This enables the use of these operators in any expression that can be evaluated to `true` or `false` using dates. For example:  

```java
if("2017-03-11T10:15:00:123Z" < now(), "past", "not past")
```

### Element operator

Standard "element" operator (`[]`) was overloaded to support extracting values from JSON arrays and other collections by index.

```java
jsonArray[1] //extracts the first element of the given jsonArray
```

> **Note:** Also achievable using the function `get(jsonArray, 1)`.

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
    String result = (String) jep.evaluate(node); //result="fee"
    ````


### Example 2: comparing dates using the `ExtendedExpressionEvaluatorFacade`

1. Put your source variables in a map:

    ```java
    Map<String, Object> myVariables = new HashMap<>();
    myVariables.put("date1", "2018-12-20T09:10:00.123456789Z");
    myVariables.put("date2", "2018-12-20T12:10:00.234567890Z");
    ```

2. Evaluate your expression:

    ````java
    String expression = "if(date1<date2,\"true\",\"false\")";
    String result = (String) ExtendedExpressionEvaluatorFacade.evaluate(expression, myVariables); 
    //result="true"
    ````
