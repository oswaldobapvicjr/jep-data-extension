# jep-data-extension

**jep-data-extension** is a library that  extends JEP, a powerful mathematical expression parser and evaluator for Java, introducing data manipulation capabilities with custom data functions and operators.

> For details about JEP core functionality, click [here](http://www.singularsys.com/jep/doc/html/index.html). 

----

## Custom functions

### String concatenation
Concatenates the elements passed to as arguments into a string. The function supports concatenation of strings and numbers.

    concat(object1, object2, ...)

> **Note:** String concatenation can also be achieved using the Addition operator (+), provided that all arguments are string variables or literals. For example: "a" + "b"

### Array/collections size

Returns the number of elements inside the given array. It also accepts JSON arrays and Java Collections.

If a regular object (i.e., not a collection) is passed to the function, the result will always be 1 (one); if a null or empty object is passed to this function, the result will always be 0 (zero).

This function can also handle string representations of valid JSON arrays.

     count(collection1)

### System's current date

Returns the system’s current date & time, with precision of milliseconds.

     now()
