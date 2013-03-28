package real.Objects.ConditionOperations.BooleanOperations;

import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.WrongType;
import real.Objects.Row;

public class LessEqual extends BooleanOperator
{
    public LessEqual(ConditionBase operandA, ConditionBase operandB, int linePosition) throws WrongType
    {
        super(operandA, operandB, linePosition);
        
        if(getType() == DataType.UNKNOWN)
        {
            throw new WrongType(linePosition, "Can't compare two different types.");
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
            
            return a <= b;
        }
        else
        {
            throw new UnsupportedOperationException("can only compare number");
        }
    }
    
    @Override
    public String toString()
    {
        return operandA.toString() + " <= " + operandB.toString();
    }
}
