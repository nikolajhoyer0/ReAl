package real.Objects.ConditionOperations.AggregateFunctions;

import java.util.ArrayList;
import real.BaseClasses.ConditionBase;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Row;

public class Average extends AggregateCondition
{
    public Average(ConditionBase operand)
    {
        super(operand);
    }
    
    @Override
    public String aggregateString(ArrayList<Row> rows) throws InvalidEvaluation
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float aggregateNumber(ArrayList<Row> rows) throws InvalidEvaluation
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean aggregateBoolean(ArrayList<Row> rows) throws InvalidEvaluation
    { 
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
