
package real.Objects.ConditionOperations.BooleanOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Column;
import real.Objects.Exceptions.WrongType;
import real.Objects.Row;

public class Equal extends BinaryConditionBase
{

    public Equal(ConditionBase operandA, ConditionBase operandB) throws WrongType
    {
        super(operandA, operandB, DataType.BOOLEAN);
                
        if(getType() == DataType.UNKNOWN)
        {
            throw new WrongType("can't equal with two different types");
        }
        
    }
    
    @Override
    public String evaluateString(Row row)
    {
        throw new UnsupportedOperationException("only supports boolean");
    }

    @Override
    public int evaluateNumber(Row row)
    {
        throw new UnsupportedOperationException("only supports boolean");
    }

    @Override
    public boolean evaluateBoolean(Row row)
    {
        if(operandA.getType() == DataType.BOOLEAN)
        {
            return operandA.evaluate(row) == operandB.evaluate(row);
        }
        
        else if(operandA.getType() == DataType.NUMBER)
        {
            return operandA.evaluate(row) == operandB.evaluate(row);
        }
        
        else
        {
            String a = (String)operandA.evaluate(row);
            String b = (String)operandB.evaluate(row);
            return a.equals(b);
        }     
    }
    
}
