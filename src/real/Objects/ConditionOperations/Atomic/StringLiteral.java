
package real.Objects.ConditionOperations.Atomic;

import real.BaseClasses.UnaryConditionBase;
import real.Enumerations.DataType;
import real.Objects.Row;

public class StringLiteral extends UnaryConditionBase
{
    private String value;
    
    public StringLiteral(String value, int linePosition)
    {
        super(null, DataType.STRING, linePosition);
        this.value = value;
    }

    @Override
    public String evaluateString(Row row)
    {
        return value;
    }

    @Override
    public Float evaluateNumber(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean evaluateBoolean(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String toString()
    {
        return "'" + value + "'";
    }
}
