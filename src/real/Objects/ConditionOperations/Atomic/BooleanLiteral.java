
package real.Objects.ConditionOperations.Atomic;

import real.BaseClasses.UnaryConditionBase;
import real.Enumerations.DataType;
import real.Objects.Row;

public class BooleanLiteral extends UnaryConditionBase 
{

    private boolean value;
    
    public BooleanLiteral(boolean value, int linePosition)
    {
        super(null, DataType.BOOLEAN, linePosition);
        this.value = value;
    }
    
    @Override
    public String evaluateString(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Float evaluateNumber(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean evaluateBoolean(Row row)
    {
        return value;
    }
    
    @Override
    public String toString()
    {
        return Boolean.toString(value);
    }
}
