
package real.Objects.RAOperations;

import real.BaseClasses.BinaryOperationBase;
import real.BaseClasses.OperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.Row;

/**
 * Implements the difference operation on bags.
 */
public class Difference extends BinaryOperationBase
{

    public Difference(OperationBase operandA, OperationBase operandB)
    {
        super(operandA, operandB);
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset, InvalidParameters
    {
        Dataset resultA = this.operandA.execute().clone();
        Dataset resultB = this.operandB.execute().clone();
        
        if (resultA.equalsSchema(resultB))
        {
            for (Row row : resultA.getRows())
            {
                if (resultB.getRows().contains(row))
                {
                    resultA.getRows().remove(row);
                    resultB.getRows().remove(row);
                }
            }
            return new Dataset("", resultA.getColumns(), resultA.getRows());
        }
        
        throw new InvalidSchema(); 
    }
}
