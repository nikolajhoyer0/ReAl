
package real.Objects.ConditionOperations.Atomic;

import real.BaseClasses.UnaryConditionBase;
import real.Enumerations.DataType;
import real.Objects.Column;
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
    public String evaluateString(Row row, Column column)
    {
        return value;
    }

    @Override
    public int evaluateNumber(Row row, Column column)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean evaluateBoolean(Row row, Column column)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
