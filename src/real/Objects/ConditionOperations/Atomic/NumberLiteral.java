
package real.Objects.ConditionOperations.Atomic;

import real.BaseClasses.UnaryConditionBase;
import real.Enumerations.DataType;
import real.Objects.Row;

public class NumberLiteral extends UnaryConditionBase
{

    private float value;
    
    public NumberLiteral(float value, int linePosition)
    {
        //the tree goes no further
        super(null, DataType.NUMBER, linePosition);
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
        return this.value;
    }

    @Override
    public Boolean evaluateBoolean(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String toString()
    {
        return Float.toString(value);
    }
}
