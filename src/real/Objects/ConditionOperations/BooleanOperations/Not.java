package real.Objects.ConditionOperations.BooleanOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.WrongType;
import real.Objects.Row;

public class Not extends BooleanOperator
{
    public Not(ConditionBase operandA, ConditionBase operandB) throws WrongType
    {
        super(operandA, operandB);
                
        if(getType() == DataType.UNKNOWN)
        {
            throw new WrongType("can't compare with two different types");
        }            
    }
    
    @Override
    public Boolean evaluateBoolean(Row row) throws InvalidEvaluation
    {
        if(operandA.getType() != DataType.BOOLEAN)
        {
            Boolean a = (Boolean)operandA.evaluate(row);
            Boolean b = (Boolean)operandB.evaluate(row);          
            
            if(a == null || b == null)
            {
                return false;
            }
            
            return Boolean.compare(a, b) != 0;
        }
        
        else if(operandA.getType() != DataType.NUMBER)
        {
            Float a = (Float)operandA.evaluate(row);
            Float b = (Float)operandB.evaluate(row); 
            
            if(a == null || b == null)
            {
                return false;
            }
            
            return Float.compare(a, b) != 0;
        }
        
        else
        {
            String a = (String)operandA.evaluate(row);
            String b = (String)operandB.evaluate(row);
            
            if(a == null || b == null)
            {
                return false;
            }
            
            return a.equals(b);
        }     
    }
}
