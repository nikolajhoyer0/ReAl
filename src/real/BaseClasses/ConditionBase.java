package real.BaseClasses;

import real.Enumerations.DataType;
import real.Objects.Row;

/**
 * The base class for all condition operators - equals, greater than, etc
 */
public abstract class ConditionBase
{
    private DataType type;
    
    public ConditionBase(DataType type)
    {
        this.type = type;
    }

    public DataType getType()
    {
        return type;
    }
    
    public Object evaluate(Row row)
    {
        if(type == DataType.BOOLEAN)
        {
            return evaluateBoolean(row);
        }
        
        else if(type == DataType.NUMBER)
        {
            return evaluateNumber(row);
        }
        
        else if(type == DataType.STRING)
        {
            return evaluateString(row);
        }
        
        else
        {
            throw new UnsupportedOperationException("only exist type: number, boolean, attribute, string");
        }
    }
    
    public abstract String evaluateString(Row row);
    public abstract float evaluateNumber(Row row);
    public abstract boolean evaluateBoolean(Row row);
}