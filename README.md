# jep-data-extension

[![Build Status](https://travis-ci.org/oswaldobapvicjr/jep-data-extension.svg?branch=master)](https://travis-ci.org/oswaldobapvicjr/jep-data-extension)
[![Coverage Status](https://coveralls.io/repos/github/oswaldobapvicjr/jep-data-extension/badge.svg?branch=master)](https://coveralls.io/github/oswaldobapvicjr/jep-data-extension?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.obvj/jep-data-extension/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.obvj/jep-data-extension)
[![javadoc](https://javadoc.io/badge2/net.obvj/jep-data-extension/javadoc.svg)](https://javadoc.io/doc/net.obvj/jep-data-extension)

**jep-data-extension** is a project that  extends JEP, the powerful mathematical expression parser and evaluator for Java, introducing data manipulation capabilities with custom functions and operators ready to use, including **RegEx**, **XPath**, **JSONPath** and **Web Services**.

> For details about JEP core features, access the [documentation](http://www.singularsys.com/jep/doc/html/index.html).

---

## String functions

### Camel

Converts a string to camel case.

```java
camel("This is-a_STRING") //result: "thisIsAString"
```

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

### Format string

Returns a formatted string according to given pattern and variable arguments.

```java
formatString("%s=%.0f", "test", 2.0) //result: "test=2"
```

### Left pad

Left-pads a given string to the given size with white-spaces or a custom padding string. 

```java
leftPad("123", 5)      //result: "  123"
leftPad("123", 5, "0") //result: "00123"
```

> **Note:** Also achievable using the alias: `lpad`

### Lower

Converts a text string to all lower-case letters.

```java
lower("STRING") //result: "string"
```

> **Note:** Also achievable using the alias: `lcase`

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

### Proper

Converts a string to proper case: the first letter in each word to upper-case, and all other letter to lower-case.

```java
proper("good DAY!") //result: "Good Day!"
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

### Right pad

Right-pads a given string to the given size with white-spaces or a custom padding string. 

```java
rightPad("abc", 5)      //result: "abc  "
rightPad("abc", 5, ".") //result: "abc.."
```

> **Note:** Also achievable using the alias: `rpad`


### Split

Splits a string into a string array based on a separating string or regular expression.

```java
split("do-re-mi", "-")            //result: ["do", "re", "mi"]
split("192.168.0.1/24", "[.\\/]") //result: ["192", "168", "0", "1", "24"]
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

> **Note:** Also achievable using the alias: `ucase`

---

## Date functions

### System current date

Returns the system's current date & time, with precision of milliseconds.

```java
 now()
```

> **Note:** Also achievable using the alias: `sysdate()`

### String to date conversion

Parses a string representing a date by trying different parse patterns, supporting RFC-3339, RFC-822 and a set of common ISO-8601 variations. This function can be the fastest choice when handling RFC-3339 dates. Also useful to convert from other formats when the parse pattern is heterogeneous or unknown. 

```java
str2date("2015-10-03T08:00:01.123Z")
```

### String to date conversion with user-defined pattern

Parses a string representing a date with user-defined pattern. This can be the fastest approach when the date format is known.

```java
str2date("2015-10-03", "yyyy-MM-dd")
```

> **Note:** This function also supports variable parse pattern arguments, so it will try each pattern specified until it finds one that converts the source string to a valid date.


### Date to string formatting

Converts a date into string using the specified format.

```java
date2str(date1, "yyyy-MM-dd'T'HH:mm:ssZZ")
```

### Days between dates

Returns the number of days between two dates (or valid date representations as string).

```java
daysBetween("2019-01-19T22:40:38.678912543Z", "2019-02-19T22:41:39.123Z") //result: 31.0
```

### End of month

Returns a date corresponding to the last day of the month given a source date.

```java
endOfMonth("2019-02-10T08:15:26.109Z") //result: "2019-02-28T23:59:59.999Z"
```

### Is leap year

Returns 1 (true) if the given argument is a leap year, that is, an year with 366 days, or 0 (false) if not.
This function accepts an year (Number), a Date or a valid date representation as string in RFC-3339 format. 

```java
isLeapYear("2020-02-20T22:41:39.123Z") //result: 1.0
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

### Week day

Returns the day of the week of a date, a number from 1 (Sunday) to 7 (Saturday).

```java
weekday("2019-06-12T18:00:01:988Z") //result: 4.0
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

### addDays

Returns the result from adding the number of days to a given date (or a valid string representation of date).

```java
addDays("2017-03-11T20:15:00:123Z",  1) //result: "2017-03-12T20:15:00:123Z"
addDays("2017-03-11T20:15:00:123Z", -1) //result: "2017-03-10T20:15:00:123Z"
```

> **Note:** There are also other similar functions available: `addWeeks`, `addMonths`, `addQuarters`, `addYears`, `addHours`, `addMinutes`, and `addSeconds`.


---

## Math functions

### Arabic

Converts a Roman numeral to Arabic.

```java
arabic("MCMXCIX") //result: 1999.0
```

### Roman

Converts an Arabic numeral to Roman.

```java
roman(1999) //result: "MCMXCIX"
```

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
average("[2,3]") //result: 2.5
```

> **Note:** Also achievable using the alias: `avg`


### Count

Returns the number of elements inside the given array. It also accepts JSON arrays, Java Collections and valid string representations of JSON arrays. 

If a regular object (i.e., not a collection) is passed to the function, the result will always be 1 (one); if a null or empty object is passed to this function, the result will always be 0 (zero).

```java
count("[0,1,2]") //result: 3.0
```

> **Note:** Also achievable using the alias `length`.

### Max

Returns the largest value in the given array. It also accepts JSON arrays, Java Collections, and valid string representations of JSON arrays.

```java
max("[9,10,8]") //result: 10
```

> **Note:** Supported data types: numbers, dates and string representations of date in RFC-3339 format.

### Min

Returns the smallest value in the given array or set. It also accepts JSON arrays, Java Collections, and valid string representations of JSON arrays.

```java
min("[9,10,8]") //result: 8
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

### Distinct

Returns a list consisting of the distinct elements of a given vector, list, array or JSON array.

```java
distinct(["say", "what", "you", "need", "to", "say"]) //result: ["say", "what", "you", "need", "to"]
```

### Get Environment Variable

Returns the value of an environment variable associated with the given key in the Operating System.

```java
getEnv("TEMP")
```

### Get System Property

Returns the value of system property associated with the given key.

```java
getSystemProperty("os.name")
```

### Is Decimal

Returns 1 (true) if the given parameter is a number (or string representing a number) containing decimals.

```java
isDecimal(10.333) //returns: 1.0
isDecimal(999.0)  //returns: 0.0
isDecimal("10.3") //returns: 1.0
isDecimal("1.0")  //returns: 0.0
```

### Is Empty

Returns 1 (true) if the given parameter is either a null object or an empty String, JSON or Collection.

If a given string can be parsed as JSON object or array, it will be first converted into JSON, then its structure will be evaluated for emptiness.

```java
isEmpty("")   //returns: 1.0
isEmpty("{}") //returns: 1.0
isEmpty("[]") //returns: 1.0
```

### Is Integer

Returns 1 (true) if the given parameter is a number (or string representing a number) is a whole number (which lacks decimals).

```java
isInteger(10.0)  //returns: 1.0
isInteger(9.90)  //returns: 0.0
isInteger("1.0") //returns: 1.0
isInteger("0.3") //returns: 0.0
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

> **Note:** Also achievable using the alias `class`.

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

### To Base64

Encodes the specified string using the Base64 encoding scheme.

```java
toBase64("myMessage") //result: "bXlNZXNzYWdl"
```

### From Base64

Decodes a Base64 encoded string.

```java
fromBase64("bXlNZXNzYWdl") //result: "myMessage"
```


---

## Web Services

### HTTP Get

Returns data from a Web Service or RESTful API, as string.

```java
httpGet("http://sampleservice.com/jep/v1/wheather")
```

> **Note:** This is similar to invoking `httpResponse(http("GET", "http://sampleservice.com/data/v1/wheather"))` 


### HTTP Get with custom headers

Returns data from a Web Service or RESTful API, as string, with custom HTTP headers in the request.

```java
httpGet("http://sampleservice.com/jep/v1/wheather", httpHeader("Accept=text/xml"))
```

> **Note:** Refer to the `httpHeaders` function for additional details.


### Invoke HTTP method

Invokes the requested method towards a Web Service or RESTful API, with a given request body, and returns a `WebServiceResponse`. 

```java
http("POST", "http://sampleservice.com/jep/v1/customer", requestBody)
```

> **Note:** A `WebServiceResponse` is an object that contains the HTTP status code and the response body/payload. These values can be retrieved by the functions `httpStatusCode()` and `httpResponse()`, respectively.


### Invoke HTTP method with custom headers

Invokes the requested method towards a Web Service or RESTful API, with a given request body and a custom HTTP headers, and returns a `WebServiceResponse`.

```java
http("POST", "http://sampleservice.com/jep/v1/customer", requestBody, httpHeader("Media-Type=text/xml"))
```

> **Note:** Refer to the `httpHeaders` function for additional details.


### HTTP header(s)

This function groups together a variable number of string arguments containing HTTP header entries for usage with the `http()` and `httpGet()` functions. Each entry is a string containing a key, a separator, and a value. A separator can be either an equal sign (`=`) or a colon (`:`). For example, an entry can be defined as `"Accept=text/xml"` or `"Accept:text/xml"`.

```java
httpHeader("Accept=text/xml")
httpHeaders("Accept=text/xml", "Authentication=Basic dXNlcjpwYXNz")
```


### Basic authorization header

Generates a basic authorization header from a given username and password. 

```java
basicAuthorizationHeader("Aladdin", "OpenSesame") //result: "Basic QWxhZGRpbjpPcGVuU2VzYW1l"
```


### HTTP status code getter

Returns the HTTP status code, given a `WebServiceResponse` object, returned by the `http()` function.

```java
httpStatusCode(webServiceResponse)
```


### HTTP response body getter

Returns the HTTP response body as string, given a `WebServiceResponse` object, returned by the `http()` function.

```java
httpResponse(webServiceResponse)
```


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
    <version>1.0.5</version>
</dependency>
```

If you use other dependency managers (such as Gradle, Grape, Ivy, etc.) click [here](https://maven-badges.herokuapp.com/maven-central/net.obvj/jep-data-extension).

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
