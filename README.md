# jep-data-extension

**jep-data-extension** is a library that  extends JEP, a powerful mathematical expression parser and evaluator for Java, introducing data manipulation capabilities with custom data functions and operators.

> For details about JEP core functionality, click [here](http://www.singularsys.com/jep/doc/html/index.html). 

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

## String functions

### Concatenation

Concatenates the elements passed to as arguments into a string. The function supports concatenation of strings and numbers.

    concat(object1, object2, ...)

> **Note:** String concatenation can also be achieved using the Addition operator (+), provided that all arguments are string variables or literals. For example: "a" + "b"

---

## Arrays/collections functions

### Array/collections size

Returns the number of elements inside the given array. It also accepts JSON arrays and Java Collections.

If a regular object (i.e., not a collection) is passed to the function, the result will always be 1 (one); if a null or empty object is passed to this function, the result will always be 0 (zero).

This function can also handle string representations of valid JSON arrays.

    count(collection1)

---

## Data manipulation & filtering functions

## XPath

Returns the evaluation result of the given XPath expression for the specified XML object.

    xpath(xmlObject1, "//actor[@id<=3]")

> Note: Not yet implemented

## JSONPath

Returns the evaluation result of the given JSONPath expression for the specified JSON object.

    jsonpath(jsonObject1, "$.phoneNumbers[:1].type")

> Note: Not yet implemented
