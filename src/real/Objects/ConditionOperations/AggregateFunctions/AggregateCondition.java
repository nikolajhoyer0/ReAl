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
    
    public Object aggregateEvaluate(ArrayList<Row> rows) throws InvalidEvaluation
    {
        if(getType() == DataType.BOOLEAN)
        {
            return aggregateBoolean(rows);
        }
        
        else if(getType() == DataType.NUMBER)
        {
            return aggregateNumber(rows);
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
    abstract public float aggregateNumber(ArrayList<Row> rows) throws InvalidEvaluation;
    abstract public boolean aggregateBoolean(ArrayList<Row> rows) throws InvalidEvaluation;
    
    @Override
    public String evaluateString(Row row) throws InvalidEvaluation
    {
        throw new InvalidEvaluation("Aggregate functions are only used in grouping operator.");
    }

    @Override
    public float evaluateNumber(Row row) throws InvalidEvaluation
    {
        throw new InvalidEvaluation("Aggregate functions are only used in grouping operator.");
    }

    @Override
    public boolean evaluateBoolean(Row row) throws InvalidEvaluation
    {
        throw new InvalidEvaluation("Aggregate functions are only used in grouping operator.");
    }  
}
