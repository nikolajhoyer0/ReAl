package real.BaseClasses;

import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;

public abstract class OperationBase
{
    public OperationBase()
    {
    }

    public abstract Dataset execute() throws InvalidSchema, NoSuchDataset; 
}
