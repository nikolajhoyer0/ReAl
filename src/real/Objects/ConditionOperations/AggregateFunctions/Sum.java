package real.Objects.ConditionOperations.AggregateFunctions;

import java.util.ArrayList;
import real.BaseClasses.ConditionBase;
import real.Objects.ConditionOperations.Atomic.AttributeLiteral;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Row;

public class Sum extends AggregateCondition
{
    String columnName;
    
    public Sum(ConditionBase operand) throws InvalidParameters
    {
        super(operand);
        
        if(!(operand instanceof AttributeLiteral))
        {
            throw new InvalidParameters("Aggregate functions can only use one attribute.");
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
    public float aggregateNumber(ArrayList<Row> rows) throws InvalidEvaluation
    {
        float value = 0;
        
        for(Row row : rows)
        {
            float f = Float.parseFloat(row.getValue(columnName));
          
            value += f;
        }
        
        return value;
    }

    @Override
    public boolean aggregateBoolean(ArrayList<Row> rows) throws InvalidEvaluation
    { 
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}