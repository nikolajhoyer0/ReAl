package real.Objects.RAOperations;

import java.util.ArrayList;
import real.BaseClasses.OperationBase;
import real.BaseClasses.UnaryOperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.Row;

public class Projection extends UnaryOperationBase
{
    private String[] columns;
    
    public Projection(OperationBase operand, String[] columns)
    {
        super(operand);
        this.columns = columns;
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset
    {
        Dataset result = this.operand.execute();

        ArrayList<Integer> columnNrList = new ArrayList<>();
        ArrayList<Row> restOfRows = new ArrayList<>();
        
        // TODO find ud af hvordan Row og Column spiller sammen i Dataset
        // for du ved det tydelig vis ikke Tobias. Især hvordan man piller ud af en Row.
        
        for(int i = 0; i <= result.getColumnCount(); i++)
        {
            if(result.getColumnName(i).equals(this.columns[i]))
            {
                columnNrList.add(i);
            }    
        }
        for(int i = 0; i <= result.getRowCount(); i++)
        {
            
        }
        
        // return new Dataset("", result.getColumns(this.columns), restOfRows);
        
        throw new UnsupportedOperationException("Not supported yet.");
        
    }
}
