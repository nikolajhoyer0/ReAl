
package real.Objects.RAOperations;

import java.util.ArrayList;
import real.BaseClasses.ConditionBase;
import real.BaseClasses.OperationBase;
import real.BaseClasses.UnaryOperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.Row;


public class Selection extends UnaryOperationBase
{
    private ConditionBase condition;
    
    public Selection(OperationBase operand, ConditionBase condition)
    {
        super(operand);
        this.condition = condition;
    }
    
    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset
    {      
        Dataset resultA = operand.execute();
        ArrayList<Row> rows = new ArrayList<>();
        
        for(int i = 0; i < resultA.getRows().size(); ++i)
        {
            if(condition.evaluateBoolean(resultA.getRows().get(i)))
            {
                rows.add(resultA.getRows().get(i));
            }
        }      
        
        return new Dataset("", resultA.getColumns(), rows);
    }
}
