
package real.Objects.RAOperations;

import java.util.ArrayList;
import java.util.HashMap;
import real.BaseClasses.BinaryOperationBase;
import real.BaseClasses.OperationBase;
import real.Objects.Column;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.Row;

public class Product extends BinaryOperationBase
{


    public Product(OperationBase operandA, OperationBase operandB)
    {
        super(operandA, operandB);
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset, InvalidParameters, InvalidEvaluation
    {
        Dataset resultA = this.operandA.execute().clone();
        Dataset resultB = this.operandB.execute().clone();

        ArrayList<Row> includeRows = new ArrayList<>();
        ArrayList<Column> includeColumns = new ArrayList<>();

        replaceMatchingNames(resultA, resultB);

        addColumns(resultA, resultB, includeColumns);

        addRows(resultA, resultB, includeColumns, includeRows);

        return new Dataset("", includeColumns, includeRows);
    }
    private void addColumns(Dataset resultA,
                            Dataset resultB,
                            ArrayList<Column> includeColumns)
    {
        for(int i=0; i < resultA.getColumnCount(); i++)
        {
            includeColumns.add(resultA.getColumns().get(i));
        }

        for(int j=0; j < resultB.getColumnCount(); j++)
        {
            includeColumns.add(resultB.getColumns().get(j));
        }
    }
     private void addRows(Dataset resultA,
                         Dataset resultB,
                         ArrayList<Column> includeColumns,
                         ArrayList<Row> includeRows)
    {
        for(int m=0; m < resultA.getRowCount(); m++)
        {
            for (int n=0; n < resultB.getRowCount(); n++)
            {
                    includeRows.add(joinRows(resultA.getRows().get(m),
                                             resultB.getRows().get(n),
                                             includeColumns));
            }
        }
    }

    private Row joinRows(Row rowA, Row rowB, ArrayList<Column> columns)
    {
        HashMap combinedRow = new HashMap();
        for(Column column : columns)
        {
            if(! rowA.getValue(column.getName()).isEmpty())
            {
                combinedRow.put(column.getName(), rowA.getValue(column.getName()));
            }
            else
            {
                combinedRow.put(column.getName(), rowB.getValue(column.getName()));
            }
        }
        return new Row(combinedRow);
    }

    private void replaceMatchingNames(Dataset resultA, Dataset resultB)
    {
        for(int j=0; j < resultB.getColumnCount(); j++)
        {
            for (int k=0; k < resultA.getColumnCount(); k++)
            {
                if(resultA.getColumnName(k).equals(resultB.getColumnName(j)))
                {
                    resultB.setColumnName(resultB.getColumnName(j),
                                          resultB.getName() + "." + resultB.getColumnName(j));
                    resultA.setColumnName(resultA.getColumnName(k),
                                          resultA.getName() + "." + resultA.getColumnName(k));
                }
            }
        }
    }
}
