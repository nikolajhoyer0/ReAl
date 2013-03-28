/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package real.Objects.ConditionOperations.AggregateFunctions;

import java.util.ArrayList;
import real.BaseClasses.ConditionBase;
import real.Objects.ConditionOperations.Atomic.AttributeLiteral;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Row;

public class Max extends AggregateCondition
{
    private String columnName;
    
    public Max(ConditionBase operand, int linePosition) throws InvalidParameters
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
        float value = 0;
        
        for(Row row : rows)
        {
            String rowValue = row.getValue(columnName);
            
            if(rowValue.isEmpty())
            {
                throw new InvalidEvaluation(getLinePosition(), "Can't Aggregate a column that have null values.");
            }
            
            float f = Float.parseFloat(rowValue);
          
            if(value < f)
            {
                value = f;
            }
        }
        
        return value;
    }

    @Override
    public Boolean aggregateBoolean(ArrayList<Row> rows) throws InvalidEvaluation
    { 
        throw new UnsupportedOperationException("Not supported yet.");
    }    
    
    @Override
    public String toString()
    {
        return "Max(" + operand.toString() + ")";
    }
}
