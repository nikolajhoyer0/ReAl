package real.Objects.ConditionOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.WrongType;
import real.Objects.Row;

public class Mult extends BinaryConditionBase
{
    public Mult(ConditionBase operandA, ConditionBase operandB) throws WrongType
    {
        super(operandA, operandB);     
        
        if(getType() == DataType.UNKNOWN)
        {
            throw new WrongType("can't multiply with two different types.");
        }
    }
    
    @Override
    public String evaluateString(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Float evaluateNumber(Row row) throws InvalidEvaluation
    {
        Float a = operandA.evaluateNumber(row);
        Float b = operandB.evaluateNumber(row);
        
        if(a == null || b == null)
        {
            return null;
        }
        
        return a * b;
    }

    @Override
    public Boolean evaluateBoolean(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }  
}
