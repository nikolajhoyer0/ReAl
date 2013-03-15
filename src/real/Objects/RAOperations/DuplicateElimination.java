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
        Dataset result = operand.execute().clone();
        
        ArrayList<Row> includeRows = new ArrayList<>();
        ArrayList<Row> checkRows = new ArrayList<>(result.getRows());
        
        includeRows.add(checkRows.get(0));
        checkRows.remove(0);
        for(int i=0; i < checkRows.size(); i++) {
            if(! includeRows.contains(checkRows.get(i))) {
                includeRows.add(checkRows.get(i));
            }
        }
        
        return new Dataset("", result.getColumns(), includeRows);
    }

}
