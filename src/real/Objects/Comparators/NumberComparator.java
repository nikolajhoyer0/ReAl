
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
        
        float f1 = Float.parseFloat(v1);
        float f2 = Float.parseFloat(v2);
        
        
        
        return (f1<f2 ? 1 : (f1==f2 ? 0 : -1));
    }
    
}
