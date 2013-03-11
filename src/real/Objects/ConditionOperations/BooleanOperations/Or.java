package real.Objects.ConditionOperations.BooleanOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Objects.Row;

public class Or extends BinaryConditionBase
{
    public Or(ConditionBase operandA, ConditionBase operandB)
    {
        super(operandA, operandB);
    }
   
    @Override
    public String evaluateString(Row row)
    {
        throw new UnsupportedOperationException("must be a boolean expression");
    }

    @Override
    public int evaluateNumber(Row row)
    {
        throw new UnsupportedOperationException("must be a boolean expression");
    }

    @Override
    public boolean evaluateBoolean(Row row)
    {
        boolean a = (boolean)operandA.evaluate(row);
        boolean b = (boolean)operandB.evaluate(row);      
        return a || b;
    }  
}
