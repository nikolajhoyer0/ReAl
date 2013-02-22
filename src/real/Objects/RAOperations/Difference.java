
package real.Objects.RAOperations;

import real.BaseClasses.BinaryOperationBase;
import real.BaseClasses.OperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;

public class Difference extends BinaryOperationBase
{

    public Difference(OperationBase operandA, OperationBase operandB)
    {
        super(operandA, operandB);
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
