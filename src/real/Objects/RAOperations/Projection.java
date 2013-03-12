package real.Objects.RAOperations;

import real.BaseClasses.ConditionBase;
import real.BaseClasses.OperationBase;
import real.BaseClasses.UnaryOperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;

public class Projection extends UnaryOperationBase
{
    private String[] columns;
    
    public Projection(OperationBase operand, ConditionBase[] conditions)
    {
        super(operand);
        this.columns = columns;
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset
    {
        Dataset result = this.operand.execute();

        
        return null;
    }
}
