package real.Objects.ConditionOperations.BooleanOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.WrongType;
import real.Objects.Row;

public class And extends BooleanOperator
{

    public And(ConditionBase operandA, ConditionBase operandB, int linePosition) throws WrongType
    {
        super(operandA, operandB, linePosition);
        
        if(getType() == DataType.UNKNOWN)
        {
            throw new WrongType(linePosition, "And expressions must contain to boolean values.");
        }
        
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
    
    @Override
    public String toString()
    {
        return operandA.toString() + " AND " + operandB.toString();
    }
}
