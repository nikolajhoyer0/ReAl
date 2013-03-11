
package real.Objects.RAOperations;

import java.util.ArrayList;
import java.util.HashMap;
import real.BaseClasses.BinaryOperationBase;
import real.BaseClasses.OperationBase;
import real.Objects.Column;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.Row;

/*
 * Implements the Natural Join
 */
public class NaturalJoin extends BinaryOperationBase
{

    public NaturalJoin(OperationBase operandA, OperationBase operandB)
    {
        super(operandA, operandB);
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset
    {
        Dataset resultA = this.operandA.execute();
        Dataset resultB = this.operandB.execute();
        
        ArrayList<Row> includeRows = new ArrayList<>();
        ArrayList<Column> includeColumns = new ArrayList<>();
        
        // Checks if the schemas match in any way and throws an exception otherwise
        if (! hasMatchingSchemas(resultA, resultB)) {
            throw new InvalidSchema();
        }
        
        // Adds column names of A
        for(int i=0; i < resultA.getColumnCount(); i++) {
            includeColumns.add(resultA.getColumns().get(i));
        }
        
        // Add column names of B not in A
        for(int j=0; j < resultB.getColumnCount(); j++) {
            for (int k=0; k < resultA.getColumnCount(); k++) {
                if(! resultA.getColumnName(k).equals(resultB.getColumnName(j))) {
                    Column column = resultB.getColumns().get(j);
                    if(!includeColumns.contains(column)) {
                        includeColumns.add(column);
                    }
                }
            }
        }
        
        // Add rows
        for(int m=0; m < resultA.getRowCount(); m++) {
            for (int n=0; n < resultB.getRowCount(); n++) {
                if(hasMatchingAttribute(resultA.getRows().get(m),
                                        resultB.getRows().get(n),
                                        includeColumns)) {
                    includeRows.add(joinRows(resultA.getRows().get(m),
                                             resultB.getRows().get(n),
                                             includeColumns));
                }
            }
        }
        
        return new Dataset("", resultA.getColumns(), includeRows);
    }
    
    private boolean hasMatchingSchemas(Dataset resultA, Dataset resultB) {
        for(int j=0; j < resultB.getColumnCount(); j++) {
            for (int k=0; k < resultA.getColumnCount(); k++) {
                if(resultA.getColumnName(j).equals(resultB.getColumnName(k))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private Row joinRows(Row rowA, Row rowB, ArrayList<Column> columns) {
        HashMap temp = new HashMap();
        
        for(Column c : columns) {
            if(rowA.getValue(c.getName()) != null) {
                temp.put(c.getName(), rowA.getValue(c.getName()));
            }
            else {
                temp.put(c.getName(), rowB.getValue(c.getName()));
            }
        }
        
        return new Row(temp);
    }
    
    private boolean hasMatchingAttribute(Row rowA, Row rowB, ArrayList<Column> columns) {
        for(Column c : columns) {
            if(rowA.getValue(c.getName()).equals(rowB.getValue(c.getName()))) {
                return true;
            }
            
        }
        return false;
    }
}
