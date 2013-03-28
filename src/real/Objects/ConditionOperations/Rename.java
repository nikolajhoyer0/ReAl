package real.Objects.ConditionOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Objects.ConditionOperations.Atomic.AttributeLiteral;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Row;

//class not meant for evaluation, but for getting the left and right operand.
public class Rename extends BinaryConditionBase
{
    
    public Rename(ConditionBase operandA, ConditionBase operandB, int linePosition) throws InvalidParameters
    {
        super(operandA, operandB, linePosition);
        
        if(!(operandB instanceof AttributeLiteral))
        {
            throw new InvalidParameters(linePosition, "Can't have a none Attribute on the right side of renaming.");
        }
    }
    
    @Override
    public String evaluateString(Row row) throws InvalidEvaluation
    {
        throw new InvalidEvaluation(getLinePosition(), "can only use the rename operator in projection, renaming and grouping.");
    }

    @Override
    public Float evaluateNumber(Row row) throws InvalidEvaluation
    {
        throw new InvalidEvaluation(getLinePosition(), "can only use the rename operator in projection, renaming and grouping.");
    }

    @Override
    public Boolean evaluateBoolean(Row row) throws InvalidEvaluation
    {
        throw new InvalidEvaluation(getLinePosition(), "can only use the rename operator in projection, renaming and grouping.");
    }
    
    @Override
    public String toString()
    {
        return operandA.toString() + " -> " + operandB.toString();
    }
}
