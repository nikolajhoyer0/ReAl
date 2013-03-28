
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
import real.Objects.Exceptions.NoSuchAttribute;
import real.Objects.Row;

/*
 * Implements the Right Outer Join
 */
public class RightOuterJoin extends BinaryOperationBase
{
    public RightOuterJoin(OperationBase operandA, OperationBase operandB, int linePosition)
    {
        super(operandA, operandB, linePosition);
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation
    {
        Dataset resultA = this.operandA.execute().clone();
        Dataset resultB = this.operandB.execute().clone();

        ArrayList<Row> includeRows = new ArrayList<>();
        ArrayList<Column> includeColumns = new ArrayList<>();

        if (! hasPartlyMatchingSchemas(resultA, resultB)) {
            throw new InvalidSchema(getLinePosition(), resultA.getName() + " and " + resultB.getName() + " does not have matching schemas.");
        }

        addColumns(resultA, resultB, includeColumns);

        addRows(resultA, resultB, includeColumns, includeRows);

        return new Dataset("", includeColumns, includeRows);
    }

    private boolean hasPartlyMatchingSchemas(Dataset resultA, Dataset resultB)
    {
        for(int j=0; j < resultB.getColumnCount(); j++)
        {
            for (int k=0; k < resultA.getColumnCount(); k++)
            {
                if(resultA.getColumnName(k).equals(resultB.getColumnName(j)))
                {
                    return true;
                }
            }
        }
        return false;
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
            for (int k=0; k < resultA.getColumnCount(); k++)
            {
                if(! resultA.getColumnName(k).equals(resultB.getColumnName(j)))
                {
                    Column column = resultB.getColumns().get(j);
                    if(!includeColumns.contains(column))
                    {
                        includeColumns.add(column);
                    }
                }
            }
        }
    }

    private void addRows(Dataset resultA,
                         Dataset resultB,
                         ArrayList<Column> includeColumns,
                         ArrayList<Row> includeRows)
    {
        boolean hasMatch;
        for(int m=0; m < resultB.getRowCount(); m++)
        {
            hasMatch = false;
            for (int n=0; n < resultA.getRowCount(); n++)
            {
                if(hasMatchingAttribute(resultA.getRows().get(n),
                                        resultB.getRows().get(m),
                                        commonColumns(resultA, resultB)))
                {
                    includeRows.add(joinRows(resultA.getRows().get(n),
                                             resultB.getRows().get(m),
                                             includeColumns));
                    hasMatch = true;
                }
            }
            if (! hasMatch)
            {
                includeRows.add(resultB.getRows().get(m));
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

    private boolean hasMatchingAttribute(Row rowA, Row rowB, ArrayList<Column> columns)
    {
        for(Column c : columns)
        {
            if(!rowA.getValue(c.getName()).equals(rowB.getValue(c.getName())))
            {
                return false;
            }
        }
        return true;
    }
    
    private ArrayList<Column> commonColumns(Dataset resultA, Dataset resultB)
    {
        ArrayList<Column> columns = new ArrayList<>();
        for(int j=0; j < resultB.getColumnCount(); j++)
        {
            for (int k=0; k < resultA.getColumnCount(); k++)
            {
                if(resultA.getColumnName(k).equals(resultB.getColumnName(j)))
                {
                    columns.add(resultA.getColumn(resultA.getColumnName(k)));
                }
            }
        }
        return columns;
    }
    
    @Override
    public String toString()
    {
        return "⟖";
    }
}
