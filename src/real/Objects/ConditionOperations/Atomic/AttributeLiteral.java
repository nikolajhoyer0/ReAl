
package real.Objects.ConditionOperations.Atomic;

import real.BaseClasses.UnaryConditionBase;
import real.Enumerations.DataType;
import real.Objects.Row;


public class AttributeLiteral extends UnaryConditionBase
{
    
    private String attributeName;
    
    public AttributeLiteral(String value, DataType type, int linePosition)
    {
        super(null, type, linePosition);
        attributeName = value;
    }
    
    public String getColumnName()
    {
        return attributeName;
    }

    @Override
    public String evaluateString(Row row)
    {
        String value = row.getValue(attributeName);
        
        if(value.isEmpty())
        {
            return null;
        }
        
        return value;
    }

    @Override
    public Float evaluateNumber(Row row)
    {
        String value = row.getValue(attributeName);
        
        if(value.isEmpty())
        {
            return null;
        }
        
        return Float.parseFloat(value);
    }

    @Override
    public Boolean evaluateBoolean(Row row)
    {
        String value = row.getValue(attributeName);
        
        if(value.isEmpty())
        {
            return null;
        }
        
        return Boolean.parseBoolean(value);   
    }
    
}
