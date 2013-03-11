
package real.Objects.ConditionOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.Enumerations.DataType;
import real.Objects.Column;
import real.Objects.Row;

public class Add extends BinaryConditionBase 
{

    public Add(ConditionBase operandA, ConditionBase operandB)
    {
        super(operandA, operandB, DataType.UNKNOWN);
        
        if(operandA.getType() != operandA.getType())
        {
           // throw new 
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