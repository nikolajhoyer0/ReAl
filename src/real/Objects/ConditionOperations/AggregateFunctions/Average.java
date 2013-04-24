package real.Objects.ConditionOperations.AggregateFunctions;

import java.util.ArrayList;
import real.BaseClasses.ConditionBase;
import real.Objects.ConditionOperations.Atomic.AttributeLiteral;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Row;

public class Average extends AggregateCondition
{
    
    private String columnName;
    
    public Average(ConditionBase operand, int linePosition) throws InvalidParameters
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
        float counter = 0;
        float values = 0;
        
        for(Row row : rows)
        {
            values += Float.parseFloat(row.getValue(columnName));
            ++counter;
        }
        
        return values / counter;      
    }

    @Override
    public Boolean aggregateBoolean(ArrayList<Row> rows) throws InvalidEvaluation
    { 
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String toString()
    {
        return "Average(" + operand.toString() + ")";
    }
}
