
package real.Objects.ConditionOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Objects.Column;
import real.Objects.Row;

public class Sub extends BinaryConditionBase
{
    
    public Sub(ConditionBase operandA, ConditionBase operandB)
    {
        super(operandA, operandB);
    }

    @Override
    public String evaluateString(Row row, Column column)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int evaluateNumber(Row row, Column column)
    {
        return operandA.evaluateNumber(row, column) - operandB.evaluateNumber(row, column);
    }

    @Override
    public boolean evaluateBoolean(Row row, Column column)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
