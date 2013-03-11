
package real.Objects.ConditionOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Objects.Row;

public class Sub extends BinaryConditionBase
{
    
    public Sub(ConditionBase operandA, ConditionBase operandB)
    {
        super(operandA, operandB);
    }

    @Override
    public String evaluateString(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int evaluateNumber(Row row)
    {
        return operandA.evaluateNumber(row) - operandB.evaluateNumber(row);
    }

    @Override
    public boolean evaluateBoolean(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
