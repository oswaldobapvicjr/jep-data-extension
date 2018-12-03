# jep-data-extension

**jep-data-extension** is a library that  extends JEP, a powerful mathematical expression parser and evaluator for Java, introducing data manipulation capabilities with custom data functions and operators.

> For details about JEP core functionality, click [here](http://www.singularsys.com/jep/doc/html/index.html). 

---

## String functions

### Concatenation

Concatenates the elements passed to as arguments into a string. The function supports concatenation of strings and numbers.

    concat(object1, object2, ...)

> **Note:** String concatenation can also be achieved using the Addition operator (+), provided that all arguments are string variables or literals. For example: "a" + "b"

### Lower case

Converts a text string to all lower-case letters.

    lower(string1)

### Upper case

Converts a text string to all upper-case letters.

    upper(string1)

### Trim

Removes leading and trailing spaces.

    trim(string1)

---

## Date functions

### System current date

Returns the system's current date & time, with precision of milliseconds.

     now()

### String to date conversion

Parses a string representing a date by trying a variety of known patterns.

    str2date(string)

> Note: Not yet implemented

### String to date conversion with user-defined pattern

Parses a string representing a date with user-specified pattern. 

    str2date(string1, "YYYY-MM-DD'T'HH:mm:ssZZ")

> Note: Not yet implemented

### Date to string formatting

Converts a date into string using the specified format

    str2date(date1, "YYYY-MM-DD'T'HH:mm:ssZZ")

> Note: Not yet implemented

---

## Database functions

### Database Lookup

Executes an SQL query onto a relation data-source to retrieve data (only select statements allowed).

    dblookup("dataSource1", "SELECT b.id FROM book b WHERE b.name = 'Psalms'")

> Note: Not yet implemented

---

## Data manipulation & filtering functions

### XPath

Returns the evaluation result of the given XPath expression for the specified XML object.

    xpath(xmlObject1, "//actor[@id<=3]")

> Note: Not yet implemented

### JSONPath

Returns the evaluation result of the given JSONPath expression for the specified JSON object.

    jsonpath(jsonObject1, "$.phoneNumbers[:1].type")

---

## Statistical functions

### Average

Returns the average (arithmetic mean) of the elements inside the given array. It also accepts JSON arrays, Java Collections and valid string representations of JSON arrays, provided that the collection contains only number elements. 

    average(collection1)

> Note: Not yet implemented

### Count

Returns the number of elements inside the given array. It also accepts JSON arrays, Java Collections and valid string representations of JSON arrays. 

If a regular object (i.e., not a collection) is passed to the function, the result will always be 1 (one); if a null or empty object is passed to this function, the result will always be 0 (zero).

    count(collection1)

### Max

Returns the largest value in the given array. It also accepts JSON arrays, Java Collections, and valid string representations of JSON arrays.

    max(collection1)

> Note: Not yet implemented

### Min

Returns the smallest value in the given array or set. It also accepts JSON arrays, Java Collections, and valid string representations of JSON arrays.

    min(array1)

> Note: Not yet implemented

---

## Random numbers

### UUID

Produces a randomly generated type 4 UUID (Universally-unique Identifier) string. E.g.: "247dc019-7d5a-465a-956d-7cada045ceb3"

    uuid()

---

## Utility functions


### Get Environment Variable

Returns the value of an environment variable associated with the given key in the Operating System.

    getEnv("TEMP")

### Get Property

Returns the value of property associated with the given key in the given file name.

    getProperty("integration.properties", "database-host")
    
> Note: Not yet implemented

### Get System Property

Returns the value of system property associated with the given key.

    getSystemProperty("os.name")
