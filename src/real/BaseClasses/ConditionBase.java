package real.BaseClasses;

import real.Enumerations.DataType;
import real.Objects.Column;
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
    
    public abstract String evaluateString(Row row, Column column);
    public abstract int evaluateNumber(Row row, Column column);
    public abstract boolean evaluateBoolean(Row row, Column column);
}