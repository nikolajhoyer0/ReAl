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

/**
 *
 */
public class FullOuterJoin extends BinaryOperationBase {

    public FullOuterJoin(OperationBase operandA, OperationBase operandB)
    {
        super(operandA, operandB);
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset, InvalidParameters, InvalidEvaluation
    {
        Dataset resultA = this.operandA.execute();
        Dataset resultB = this.operandB.execute();
        
        ArrayList<Row> includeRows = new ArrayList<>();
        ArrayList<Column> includeColumns = new ArrayList<>();
        
        // Adds column names of A
        for(int i=0; i < resultA.getColumnCount(); i++) {
            includeColumns.add(resultA.getColumns().get(i));
        }
        
        // Add column names of B not in A
        for(int j=0; j < resultB.getColumnCount(); j++) {
            for (int k=0; k < resultA.getColumnCount(); k++) {
                if(! resultA.getColumnName(j).equals(resultB.getColumnName(k))) {
                    includeColumns.add(resultB.getColumns().get(k));
                }
            }
        }
        
        for(int m=0; m < resultA.getRowCount(); m++) {
            for (int n=0; n < resultB.getRowCount(); n++) {
                if(! resultA.getRows().get(m).equals(resultB.getRows().get(n))) {
                    includeRows.add(joinRows(resultA.getRows().get(m),
                                             resultB.getRows().get(n),
                                             includeColumns));
                }
            }
        }
        
        return new Dataset("", resultA.getColumns(), includeRows);
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
}
