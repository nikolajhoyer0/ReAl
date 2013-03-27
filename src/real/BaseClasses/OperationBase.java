package real.BaseClasses;

import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchAttribute;
import real.Objects.Exceptions.NoSuchDataset;

/**
 * The base class for all relational operations.
 */
public abstract class OperationBase
{
    int linePosition;
    
    public OperationBase(int linePosition)
    {
        this.linePosition = linePosition;
    }
    
    public int getLinePosition()
    {
        return this.linePosition;
    }

    public abstract Dataset execute() throws InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation;
}
