
package real.Objects.ConditionOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.WrongType;
import real.Objects.Row;

public class Add extends BinaryConditionBase 
{

    public Add(ConditionBase operandA, ConditionBase operandB, int linePosition) throws WrongType
    {
        super(operandA, operandB, linePosition);
        
        if(getType() == DataType.UNKNOWN)
        {
            throw new WrongType(linePosition, "can't add with two different types.");
        }
    }
    
    @Override
    public String evaluateString(Row row)
    {
        throw new UnsupportedOperationException("Cannot concat strings.");
    }

    @Override
    public Float evaluateNumber(Row row) throws InvalidEvaluation
    {     
        Float a = operandA.evaluateNumber(row);
        Float b = operandB.evaluateNumber(row);
        
        if(a == null | b == null)
        {
            return null;
        }
        
        return a + b;
    }

    @Override
    public Boolean evaluateBoolean(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String toString()
    {
        return operandA.toString() + " + " + operandB.toString();
    }
    
}
