package real.BaseClasses;

import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;

/**
 * The base class for all relational operations.
 */
public abstract class OperationBase
{
    public OperationBase()
    {
    }

    public abstract Dataset execute() throws InvalidSchema, NoSuchDataset, InvalidParameters, InvalidEvaluation;
}
