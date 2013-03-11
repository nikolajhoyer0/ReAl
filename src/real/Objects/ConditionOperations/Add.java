
package real.Objects.ConditionOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Column;
import real.Objects.Exceptions.WrongType;
import real.Objects.Row;

public class Add extends BinaryConditionBase 
{

    public Add(ConditionBase operandA, ConditionBase operandB) throws WrongType
    {
        super(operandA, operandB);
        
        if(getType() == DataType.UNKNOWN)
        {
            throw new WrongType("can't add with two different types.");
        }
    }
    
    @Override
    public String evaluateString(Row row, Column column)
    {
        throw new UnsupportedOperationException("Cannot concat strings.");
    }

    @Override
    public int evaluateNumber(Row row, Column column)
    {     
        return operandA.evaluateNumber(row, column) + operandB.evaluateNumber(row, column);
    }

    @Override
    public boolean evaluateBoolean(Row row, Column column)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
