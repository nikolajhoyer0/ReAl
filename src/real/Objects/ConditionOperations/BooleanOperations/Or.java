package real.Objects.ConditionOperations.BooleanOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.WrongType;
import real.Objects.Row;

public class Or extends BinaryConditionBase
{
    public Or(ConditionBase operandA, ConditionBase operandB) throws WrongType
    {
        super(operandA, operandB, DataType.BOOLEAN);
        
        if(getType() == DataType.UNKNOWN)
        {
            throw new WrongType("Can't create an Or expression with non booleans.");
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
