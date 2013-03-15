/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package real.Objects.ConditionOperations.AggregateFunctions;

import java.util.ArrayList;
import real.BaseClasses.ConditionBase;
import real.BaseClasses.UnaryConditionBase;
import real.Enumerations.DataType;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Row;

//the base class for the grouping operators aggregate functions
//the evaluators do nothing, but insures that the aggregate functions are only used in grouping operator
public abstract class AggregateCondition extends UnaryConditionBase
{
    public AggregateCondition(ConditionBase operand)
    {
        super(operand);
    }
    
    public String aggregateEvaluate(ArrayList<Row> rows) throws InvalidEvaluation
    {
        if(getType() == DataType.BOOLEAN)
        {
            return String.valueOf(aggregateBoolean(rows));
        }
        
        else if(getType() == DataType.NUMBER)
        {
            return String.valueOf(aggregateNumber(rows));
        }
        
        else if(getType() == DataType.STRING)
        {
            return aggregateString(rows);
        }
        
        else
        {
            throw new InvalidEvaluation("booleans, numbers and strings are only supported.");
        }
    }
    
    abstract public String aggregateString(ArrayList<Row> rows) throws InvalidEvaluation;
    abstract public Float aggregateNumber(ArrayList<Row> rows) throws InvalidEvaluation;
    abstract public Boolean aggregateBoolean(ArrayList<Row> rows) throws InvalidEvaluation;
    
    @Override
    public String evaluateString(Row row) throws InvalidEvaluation
    {
        throw new InvalidEvaluation("Aggregate functions are only used in grouping operator.");
    }

    @Override
    public Float evaluateNumber(Row row) throws InvalidEvaluation
    {
        throw new InvalidEvaluation("Aggregate functions are only used in grouping operator.");
    }

    @Override
    public Boolean evaluateBoolean(Row row) throws InvalidEvaluation
    {
        throw new InvalidEvaluation("Aggregate functions are only used in grouping operator.");
    }  
}
