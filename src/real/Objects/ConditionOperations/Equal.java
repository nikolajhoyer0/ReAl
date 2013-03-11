
package real.Objects.ConditionOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Column;
import real.Objects.Row;

public class Equal extends BinaryConditionBase
{

    public Equal(ConditionBase operandA, ConditionBase operandB)
    {
        super(operandA, operandB, DataType.UNKNOWN);
    }
    
    @Override
    public String evaluateString(Row row, Column column)
    {
        throw new UnsupportedOperationException("Not supported yet.");
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
