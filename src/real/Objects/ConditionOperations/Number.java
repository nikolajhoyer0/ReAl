
package real.Objects.ConditionOperations;

import real.BaseClasses.UnaryConditionBase;
import real.Objects.Column;
import real.Objects.Row;

public class Number extends UnaryConditionBase
{

    private int value;
    
    public Number(int value)
    {
        //the tree goes no further
        super(null);
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
