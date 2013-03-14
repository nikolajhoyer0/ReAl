
package real.Objects.Comparators;

import java.util.Comparator;
import real.Objects.Row;


public class NumberComparator implements Comparator<Row>
{

    private String columnName;
    
    public NumberComparator(String columnName)
    {
        this.columnName = columnName;
    }
    
    @Override
    public int compare(Row o1, Row o2)
    {
        float f1 = Float.parseFloat(o1.getValue(columnName));
        float f2 = Float.parseFloat(o2.getValue(columnName));
        
        return (f1<f2 ? -1 : (f1==f2 ? 0 : 1));
    }
    
}
