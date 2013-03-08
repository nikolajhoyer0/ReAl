package real.Objects.RAOperations;

import java.util.ArrayList;
import real.BaseClasses.BinaryOperationBase;
import real.BaseClasses.OperationBase;
import real.Objects.Dataset;
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
    public Dataset execute() throws InvalidSchema, NoSuchDataset
    {
        Dataset resultA = this.operandA.execute();
        Dataset resultB = this.operandB.execute();

        if (resultA.equalsSchema(resultB))
        {
            ArrayList<Row> rows = new ArrayList<>();
            rows.addAll(resultA.getRows());
            for (Row row : resultB.getRows())
            {
                if (!rows.contains(row))
                {
                    rows.add(row);
                }
            }
            return new Dataset("", resultA.getColumns(), rows);
        }
        throw new InvalidSchema();
    }
}
