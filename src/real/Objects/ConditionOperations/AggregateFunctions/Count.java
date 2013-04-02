package real.Objects.ConditionOperations.AggregateFunctions;

import java.util.ArrayList;
import real.BaseClasses.ConditionBase;
import real.Objects.ConditionOperations.Atomic.AttributeLiteral;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Row;

public class Count extends AggregateCondition
{
    
    private String columnName;
    
    public Count(ConditionBase operand, int linePosition) throws InvalidParameters
    {
        super(operand, linePosition);
        
        if(!(operand instanceof AttributeLiteral))
        {
            throw new InvalidParameters(linePosition, "Aggregate functions can only use one attribute.");
        }
        
        AttributeLiteral att = (AttributeLiteral)operand;
        columnName = att.getColumnName();
    }
    
    @Override
    public String aggregateString(ArrayList<Row> rows) throws InvalidEvaluation
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Float aggregateNumber(ArrayList<Row> rows) throws InvalidEvaluation
    {
        Float counter = new Float(0);

        for (Row row : rows)
        {
            if (row.getValue(columnName) != null)
            {
                counter++;
            }
        }

        return counter;
    }

    @Override
    public Boolean aggregateBoolean(ArrayList<Row> rows) throws InvalidEvaluation
    { 
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String toString()
    {
        return "Count(" + operand.toString() + ")";
    }
}
