
package real.Objects.RAOperations;

import real.BaseClasses.OperationBase;
import real.BaseClasses.UnaryOperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;

public class Renaming extends UnaryOperationBase
{
    private final String[] columnsToChange;
    private final String[] changeColumns;
    
    public Renaming(OperationBase operand, String[] columns, String[] change)
    {
        super(operand);
        this.columnsToChange = columns;
        this.changeColumns = change;
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset
    {
        Dataset result = this.operand.execute();          
        Dataset dataset = result.clone();
        
        //atm it doesn't check if the name you are changing it to is used  
        if(columnsToChange.length == changeColumns.length) 
        {
            for(int i = 0; i < changeColumns.length; ++i)
            {
                if(!dataset.setColumnName(this.columnsToChange[i], this.changeColumns[i]))
                {
                    //must likely change the exception
                    throw new InvalidSchema();
                }
            }
        }
         
        return dataset;
    }
}
