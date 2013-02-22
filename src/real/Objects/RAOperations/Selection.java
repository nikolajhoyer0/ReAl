
package real.Objects.RAOperations;

import real.BaseClasses.OperationBase;
import real.BaseClasses.UnaryOperationBase;
import real.Objects.Condition;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;

public class Selection extends UnaryOperationBase
{

    public Selection(OperationBase operand, Condition conditions)
    {
        super(operand);
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset
    {
        //implement all operations that use condition for last.
        //we need to discuss/implement how to use conditions.
        /* PSEUDO CODE
        DataSet operandResult  = operand.Execute();
        DataSet Result = new DataSet("", new String[]{});

        List<Row> rows = new List<>();
        foreach(Row row in operandResult.getRows())
        {
            if(condition.evaluate(columns, row))
            {
                rows.add(row)
            } 
        }

        Result.load(rows);*/

        throw new UnsupportedOperationException("Not supported yet.");
    }
}
