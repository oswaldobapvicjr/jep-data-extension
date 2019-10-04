package net.obvj.jep.functions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that an object is a custom function, and assigns names/aliases to it.
 * 
 * @author oswaldo.bapvic.jr
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface Function
{
    /**
     * @return an array of the the names/aliases associated with a function
     */
    String[] value();
}
