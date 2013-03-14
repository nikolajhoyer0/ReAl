
package real.Objects.ConditionOperations.BooleanOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Exceptions.InvalidEvaluation;
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
    public float evaluateNumber(Row row)
    {
        throw new UnsupportedOperationException("only supports boolean");
    }

    @Override
    public boolean evaluateBoolean(Row row) throws InvalidEvaluation
    {
        if(operandA.getType() == DataType.BOOLEAN)
        {
            boolean a = (boolean)operandA.evaluate(row);
            boolean b = (boolean)operandB.evaluate(row);          
            return a == b;
        }
        
        else if(operandA.getType() == DataType.NUMBER)
        {
            float a = (float)operandA.evaluate(row);
            float b = (float)operandB.evaluate(row);            
            return a == b;
        }
        
        else
        {
            String a = (String)operandA.evaluate(row);
            String b = (String)operandB.evaluate(row);
            return a.equals(b);
        }     
    }
    
}
