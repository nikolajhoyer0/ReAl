package real.Objects.ConditionOperations.BooleanOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Row;

public class LessEqual extends BinaryConditionBase
{
    public LessEqual(ConditionBase operandA, ConditionBase operandB)
    {
        super(operandA, operandB, DataType.BOOLEAN);
    }

    @Override
    public String evaluateString(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int evaluateNumber(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean evaluateBoolean(Row row)
    {
        if (operandA.getType() == DataType.NUMBER)
        {
            int a = (int) operandA.evaluate(row);
            int b = (int) operandB.evaluate(row);
            return a <= b;
        }
        else
        {
            throw new UnsupportedOperationException("can only compare number");
        }
    }
}
