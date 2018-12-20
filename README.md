# jep-data-extension

**jep-data-extension** is a library that  extends JEP, a powerful mathematical expression parser and evaluator for Java, introducing data manipulation capabilities with custom data functions and operators.

> For details about JEP core functionality, click [here](http://www.singularsys.com/jep/doc/html/index.html). 

---

## String functions

### Concatenation

Concatenates the elements passed to as arguments into a string. The function supports concatenation of strings and numbers.

```java
concat(object1, object2, ...)
```

> **Note:** String concatenation can also be achieved using the Addition operator (+), provided that all arguments are string variables or literals. For example: "a" + "b"

### Lower

Converts a text string to all lower-case letters.

```java
lower(string1)
```

### Replace

Returns a new string after replacing all occurrences of the search criteria within the original string with the replacement argument.

```java
replace(sourceString, searchString, replacementString)
```

### Trim

Removes leading and trailing spaces.

```java
trim(string1)
```

### Upper

Converts a text string to all upper-case letters.

```java
upper(string1)
```

---

## Date functions

### System current date

Returns the system's current date & time, with precision of milliseconds.

```java
 now()
```

### String to date conversion

Parses a string representing a date with user-specified pattern. 

```java
str2date(string1, "YYYY-MM-DD'T'HH:mm:ssZZ")
```

### Date to string formatting

Converts a date into string using the specified format

```java
date2str(date1, "YYYY-MM-DD'T'HH:mm:ssZZ")
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
xpath(xmlObject1, "//actor[@id<=3]")
```

> Note: Not yet implemented

### JSONPath

Returns the evaluation result of the given JSONPath expression for the specified JSON object.

```java
jsonpath(jsonObject1, "$.phoneNumbers[:1].type")
```

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
jsonArray[0]
```

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
