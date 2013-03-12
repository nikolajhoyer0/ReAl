
package real.Objects.ConditionOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.WrongType;
import real.Objects.Row;

public class Add extends BinaryConditionBase 
{

    public Add(ConditionBase operandA, ConditionBase operandB) throws WrongType
    {
        super(operandA, operandB);
        
        if(getType() == DataType.UNKNOWN)
        {
            throw new WrongType("can't add with two different types.");
        }
    }
    
    @Override
    public String evaluateString(Row row)
    {
        throw new UnsupportedOperationException("Cannot concat strings.");
    }

    @Override
    public float evaluateNumber(Row row) throws InvalidEvaluation
    {     
        return operandA.evaluateNumber(row) + operandB.evaluateNumber(row);
    }

    @Override
    public boolean evaluateBoolean(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
