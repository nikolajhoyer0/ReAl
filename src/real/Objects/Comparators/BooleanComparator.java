package real.Objects.Comparators;

import java.util.Comparator;
import real.Objects.Row;

public class BooleanComparator implements Comparator<Row>
{

    private String columnName;
    
    public BooleanComparator(String columnName)
    {
        this.columnName = columnName;
    }
    
    
    @Override
    public int compare(Row o1, Row o2)
    {
        boolean b1 = Boolean.parseBoolean(o1.getValue(columnName));
        boolean b2 = Boolean.parseBoolean(o2.getValue(columnName));      
        return (b1 ^ b2) ? ((b1 ^ true) ? 1 : -1) : 0;
    }
    
}
