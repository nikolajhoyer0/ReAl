package real.Objects.ConditionOperations.BooleanOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.WrongType;
import real.Objects.Row;

public class And extends BinaryConditionBase
{

    public And(ConditionBase operandA, ConditionBase operandB) throws WrongType
    {
        super(operandA, operandB, DataType.BOOLEAN);
        
        if(getType() == DataType.UNKNOWN)
        {
            throw new WrongType("And expressions must contain to boolean values.");
        }
        
    }
   
    @Override
    public String evaluateString(Row row)
    {
        throw new UnsupportedOperationException("must be a boolean expression");
    }

    @Override
    public Float evaluateNumber(Row row)
    {
        throw new UnsupportedOperationException("must be a boolean expression");
    }

    @Override
    public Boolean evaluateBoolean(Row row) throws InvalidEvaluation
    {
        Boolean a = (Boolean)operandA.evaluate(row);
        Boolean b = (Boolean)operandB.evaluate(row);     
        
        if(a == null || b == null)
        {
            return false;
        }
        
        return a && b;
    }  
}
