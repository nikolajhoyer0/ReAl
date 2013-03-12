package real.Objects.ConditionOperations.BooleanOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Exceptions.WrongType;
import real.Objects.Row;

public class And extends BinaryConditionBase
{

    public And(ConditionBase operandA, ConditionBase operandB) throws WrongType
    {
        super(operandA, operandB);
        
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
    public float evaluateNumber(Row row)
    {
        throw new UnsupportedOperationException("must be a boolean expression");
    }

    @Override
    public boolean evaluateBoolean(Row row)
    {
        boolean a = (boolean)operandA.evaluate(row);
        boolean b = (boolean)operandB.evaluate(row);      
        return a && b;
    }  
}
