package real.Objects.Comparators;

import java.util.Comparator;
import real.Objects.Row;


public class StringComparator implements Comparator<Row>
{
    private String columnName;
    
    public StringComparator(String columnName)
    {
        this.columnName = columnName;
    }
    
    @Override
    public int compare(Row o1, Row o2)
    {
        String str1 = o1.getValue(columnName);
        String str2 = o2.getValue(columnName);
        
        if(str1.isEmpty() && str2.isEmpty())
        {
            return 0;
        }
        
        else if(!str1.isEmpty() && str2.isEmpty())
        {
            return -1;
        }
        
        else if(str1.isEmpty() && !str2.isEmpty())
        {
            return 1;
        }
        
        
        
        
        return (str1.compareTo(str2) < 0 ? -1 : (str1.equals(str2) ? 0 : 1));
    }
    
}
