package real.Objects.ConditionOperations.BooleanOperations;

import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.WrongType;
import real.Objects.Row;

public class GreaterEqual extends BooleanOperator
{
    public GreaterEqual(ConditionBase operandA, ConditionBase operandB) throws WrongType
    {
        super(operandA, operandB);
        
        if(getType() == DataType.UNKNOWN)
        {
            throw new WrongType("Can't compare two different types.");
        }
    }

    @Override
    public Boolean evaluateBoolean(Row row) throws InvalidEvaluation
    {
        if (operandA.getType() == DataType.NUMBER)
        {
            Float a = (Float) operandA.evaluate(row);
            Float b = (Float) operandB.evaluate(row);
            
            if(a == null || b == null)
            {
                return false;
            }
            
            return a >= b;
        }
        else
        {
            throw new UnsupportedOperationException("can only compare number");
        }
    }
}
