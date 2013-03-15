
package real.Objects.ConditionOperations.Atomic;

import real.BaseClasses.UnaryConditionBase;
import real.Enumerations.DataType;
import real.Objects.Row;

public class BooleanLiteral extends UnaryConditionBase 
{

    boolean value;
    
    public BooleanLiteral(boolean value)
    {
        super(null, DataType.BOOLEAN);
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
}
