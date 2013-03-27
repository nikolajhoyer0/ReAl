package real.BaseClasses;

import real.Enumerations.DataType;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Row;

/**
 * The base class for all condition operators - equals, greater than, etc
 */
public abstract class ConditionBase
{
    private DataType type;
    private int linePosition;
    
    public ConditionBase(DataType type, int linePosition)
    {
        this.type = type;
        this.linePosition = linePosition;
    }

    public DataType getType()
    {
        return type;
    }
    
    public int getLinePosition()
    {
        return this.linePosition;
    }
    
    public Object evaluate(Row row) throws InvalidEvaluation
    {
        if(type == DataType.BOOLEAN)
        {
            return evaluateBoolean(row);
        }
        
        else if(type == DataType.NUMBER)
        {
            return evaluateNumber(row);
        }
        
        else if(type == DataType.STRING)
        {
            return evaluateString(row);
        }
        
        else
        {
            throw new UnsupportedOperationException("only exist type: number, boolean, attribute, string");
        }
    }
    
    public abstract String evaluateString(Row row) throws InvalidEvaluation;
    public abstract Float evaluateNumber(Row row) throws InvalidEvaluation;;
    public abstract Boolean evaluateBoolean(Row row) throws InvalidEvaluation;;
}