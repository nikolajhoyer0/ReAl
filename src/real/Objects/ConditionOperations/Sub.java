
package real.Objects.ConditionOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Exceptions.WrongType;
import real.Objects.Row;

public class Sub extends BinaryConditionBase
{
    
    public Sub(ConditionBase operandA, ConditionBase operandB) throws WrongType
    {
        super(operandA, operandB);
        
        if(getType() == DataType.UNKNOWN)
        {
            throw new WrongType("can't divide with two different types.");
        }
    }

    @Override
    public String evaluateString(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float evaluateNumber(Row row)
    {
        return operandA.evaluateNumber(row) - operandB.evaluateNumber(row);
    }

    @Override
    public boolean evaluateBoolean(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
