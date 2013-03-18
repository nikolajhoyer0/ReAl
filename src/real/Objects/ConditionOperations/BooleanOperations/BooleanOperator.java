package real.Objects.ConditionOperations.BooleanOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Row;

public abstract class BooleanOperator extends BinaryConditionBase
{

    public BooleanOperator(ConditionBase operandA, ConditionBase operandB)
    {
        super(operandA, operandB, DataType.BOOLEAN);
    }
    
    @Override
    public String evaluateString(Row row) throws InvalidEvaluation
    {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public Float evaluateNumber(Row row) throws InvalidEvaluation
    {
        throw new UnsupportedOperationException("Not supported.");
    }   
}
