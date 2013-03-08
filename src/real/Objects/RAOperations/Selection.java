
package real.Objects.RAOperations;

import real.BaseClasses.OperationBase;
import real.BaseClasses.UnaryOperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;


public class Selection extends UnaryOperationBase
{
    public Selection(OperationBase operand)
    {
        super(operand);
    }
    
    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset
    {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
