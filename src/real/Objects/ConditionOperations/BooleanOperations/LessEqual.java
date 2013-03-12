package real.Objects.ConditionOperations.BooleanOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.WrongType;
import real.Objects.Row;

public class LessEqual extends BinaryConditionBase
{
    public LessEqual(ConditionBase operandA, ConditionBase operandB) throws WrongType
    {
        super(operandA, operandB, DataType.BOOLEAN);
        
        if(getType() == DataType.UNKNOWN)
        {
            throw new WrongType("Can't compare two different types.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean evaluateBoolean(Row row) throws InvalidEvaluation
    {
        if (operandA.getType() == DataType.NUMBER)
        {
            float a = (float) operandA.evaluate(row);
            float b = (float) operandB.evaluate(row);
            return a <= b;
        }
        else
        {
            throw new UnsupportedOperationException("can only compare number");
        }
    }
}
