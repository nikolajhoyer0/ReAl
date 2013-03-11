
package real.Objects.RAOperations;

import java.util.ArrayList;
import real.BaseClasses.BinaryOperationBase;
import real.BaseClasses.OperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.Row;

public class Intersection extends BinaryOperationBase
{

    public Intersection(OperationBase operandA, OperationBase operandB)
    {
        super(operandA, operandB);
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset
    {
        Dataset resultA = this.operandA.execute();
        Dataset resultB = this.operandB.execute().clone();
                
        ArrayList<Row> includeRows = new ArrayList<>();
        
        if (resultA.equalsSchema(resultB))
        {
            for (Row row : resultA.getRows())
            {
                if (resultB.getRows().contains(row))
                {
                    includeRows.add(row);
                    resultB.getRows().remove(row);
                }
            }
            
            return new Dataset("", resultA.getColumns(), includeRows);
        }
        
        throw new InvalidSchema();      
    }
}
