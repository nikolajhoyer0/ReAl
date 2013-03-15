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
        String v1 = o1.getValue(columnName);
        String v2 = o2.getValue(columnName);
        
        if(v1.isEmpty() && v2.isEmpty())
        {
            return 0;
        }
        
        else if(!v1.isEmpty() && v2.isEmpty())
        {
            return -1;
        }
        
        else if(v1.isEmpty() && !v2.isEmpty())
        {
            return 1;
        }
        
        boolean b1 = Boolean.parseBoolean(v1);
        boolean b2 = Boolean.parseBoolean(v2);  
        
        
        return (b1 ^ b2) ? ((b1 ^ true) ? 1 : -1) : 0;
    }
    
}
