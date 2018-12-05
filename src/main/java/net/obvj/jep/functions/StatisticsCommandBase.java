package net.obvj.jep.functions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.DateUtils;
import net.obvj.jep.util.NumberUtils;

/**
 * This class implements common methods to be used by statistics function.
 *
 * @author oswaldo.bapvic.jr
 */
public class StatisticsCommandBase extends PostfixMathCommand
{
    /**
     * Creates a map where the key is each element of the Iterable, parsed for statistics
     * operation and the value is the original element without conversion. This map supports
     * objects that can be parsed into Dates or Doubles.
     *
     * @param iterable has the elements to create the map
     * @return map that contains the parsed object as key and the original element as value
     */
    public Map<Object, Object> createMapOfParsedObjects(Iterable<?> iterable)
    {
        Map<Object, Object> parsedObjectsMap = new HashMap<>();
        Iterator<?> iterator = iterable.iterator();
        while (iterator.hasNext())
        {
            Object element = iterator.next();
            if (DateUtils.isParsable(element))
            {
                parsedObjectsMap.put(DateUtils.parseDate(element), element);
            }
            else if (NumberUtils.isNumber(element))
            {
                parsedObjectsMap.put(Double.valueOf(NumberUtils.parseDouble(element)), element);
            }
        }
        return parsedObjectsMap;
    }
}
