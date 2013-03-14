package real.Objects.RAOperations;

import java.util.ArrayList;
import real.BaseClasses.OperationBase;
import real.BaseClasses.UnaryOperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.Row;


public class DuplicateElimination extends UnaryOperationBase
{

    public DuplicateElimination(OperationBase operand)
    {
        super(operand);
    }
    
    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset, InvalidParameters, InvalidEvaluation
    {
        
        boolean duplicates = false;
        Dataset result = operand.execute().clone();
        ArrayList<Row> includeRows = new ArrayList<>();
        ArrayList<Row> checkRows = new ArrayList<>(result.getRows());
        
        
        for (int j = 0; j < checkRows.size(); j++)
        {
            for (int k = 0; k < includeRows.size(); k++)
            {
               if(checkRows.get(j).equals(includeRows.get(k)))
               {
                   duplicates = true;
               }
            }
            
            if(!duplicates)
            {
                includeRows.add(checkRows.get(j));
                duplicates = false;
            }
        }

        
        
        return new Dataset("", result.getColumns(), includeRows);
    }
    
}
