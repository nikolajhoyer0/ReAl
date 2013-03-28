package real.Objects.RAOperations;

import real.BaseClasses.OperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchAttribute;


public class TupleList extends OperationBase
{

    OperationBase[] operations;
    
    public TupleList(OperationBase[] operations, int linePosition)
    {
        super(linePosition);
        this.operations = operations;
    }
    
    @Override
    public Dataset execute() throws InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation
    {
        /*
        for(OperationBase operation : operations)
        {
           // operation.
        }
        */
        return null;
    }
    
}
