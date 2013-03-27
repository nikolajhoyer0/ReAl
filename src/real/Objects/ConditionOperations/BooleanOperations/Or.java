package real.Objects.ConditionOperations.BooleanOperations;

import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.WrongType;
import real.Objects.Row;

public class Or extends BooleanOperator
{
    public Or(ConditionBase operandA, ConditionBase operandB, int linePosition) throws WrongType
    {
        super(operandA, operandB, linePosition);
        
        if(getType() == DataType.UNKNOWN)
        {
            throw new WrongType(linePosition, "Can't create an Or expression with non booleans.");
        }
    }
   
    @Override
    public Boolean evaluateBoolean(Row row) throws InvalidEvaluation
    {
        Boolean a = (Boolean) operandA.evaluate(row);
        Boolean b = (Boolean) operandB.evaluate(row);

        if(a == null && b == null)
        {
            return false;
        }

        else if(a == null)
        {
            return b;
        }
        
        else if(b == null)
        {
            return a;
        }
        
        return a || b;
    }  
}
