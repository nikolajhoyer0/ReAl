
package real.Objects.ConditionOperations.Atomic;

import real.BaseClasses.UnaryConditionBase;
import real.Enumerations.DataType;
import real.Objects.Row;

public class StringLiteral extends UnaryConditionBase
{
    String value;
    
    public StringLiteral(String value)
    {
        super(null, DataType.STRING);
        this.value = value;
    }

    @Override
    public String evaluateString(Row row)
    {
        return value;
    }

    @Override
    public float evaluateNumber(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean evaluateBoolean(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
