package real.Objects.RAOperations;

import java.util.ArrayList;
import real.BaseClasses.BinaryOperationBase;
import real.BaseClasses.OperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.Row;

public class Union extends BinaryOperationBase
{

    public Union(OperationBase operandA, OperationBase operandB)
    {
        super(operandA,operandB);
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset, InvalidParameters
    {
        Dataset resultA = this.operandA.execute();
        Dataset resultB = this.operandB.execute();
        
        ArrayList<Row> includeRows = new ArrayList<>();

        if (resultA.equalsSchema(resultB))
        {
            includeRows.addAll(resultA.getRows());
            
            // Testing
            // Difference difference = new Difference(operandA, operandB);
            ArrayList<Row> tempRowA = resultA.getRows();
            ArrayList<Row> tempRowB = resultB.getRows();
            for (Row row : tempRowA)
            {
                if (tempRowB.contains(row))
                {
                    tempRowA.remove(row);
                    tempRowB.remove(row);
                }
            }
            
            //
            
            for (Row row : tempRowA) //difference.execute().getRows())
            {
                includeRows.add(row);
            }
            return new Dataset("", resultA.getColumns(), includeRows);
        }
        
        throw new InvalidSchema();
    }
}
