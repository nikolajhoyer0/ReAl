
package real.Objects.ConditionOperations;

import real.BaseClasses.ConditionBase;
import real.BaseClasses.UnaryConditionBase;
import real.Enumerations.DataType;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.WrongType;
import real.Objects.Row;


public class Neg extends UnaryConditionBase
{
    public Neg(ConditionBase operand) throws WrongType
    {
        super(operand);
        
        if(getType() == DataType.UNKNOWN)
        {
            throw new WrongType("Can only negate numbers.");
        }
    }

    @Override
    public String evaluateString(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Float evaluateNumber(Row row) throws InvalidEvaluation
    {
        Float a = operand.evaluateNumber(row);
        
        if(a == null)
        {
            return null;
        }
        
        return -a;
    }

    @Override
    public Boolean evaluateBoolean(Row row)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
