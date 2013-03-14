/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package real.Objects.Comparators;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ChainedComparator<T> implements Comparator<T>
{

    private List<Comparator<T>> listComparators;

    public ChainedComparator(List<Comparator<T>> listComparators)
    {
        this.listComparators = listComparators;
    }

    @Override
    public int compare(T o1, T o2)
    {
        for (Comparator<T> comparator : listComparators)
        {
            int result = comparator.compare(o1, o2);
            if (result != 0)
            {
                return result;
            }
        }
        return 0;
    }
}