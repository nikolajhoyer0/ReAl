
package real.Objects.ConditionOperations.Atomic;

import real.BaseClasses.UnaryConditionBase;
import real.Enumerations.DataType;
import real.Objects.Column;
import real.Objects.Row;

public class NumberLiteral extends UnaryConditionBase
{

    private int value;
    
    public NumberLiteral(int value)
    {
        //the tree goes no further
        super(null, DataType.NUMBER);
        this.value = value;
    }
    
    @Override
    public String evaluateString(Row row, Column column)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int evaluateNumber(Row row, Column column)
    {
        return this.value;
    }

    @Override
    public boolean evaluateBoolean(Row row, Column column)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
