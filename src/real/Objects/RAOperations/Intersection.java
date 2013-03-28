
package real.Objects.RAOperations;

import java.util.ArrayList;
import real.BaseClasses.BinaryOperationBase;
import real.BaseClasses.OperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchAttribute;
import real.Objects.Row;

/**
 * Implements the intersection operation on bags.
 */
public class Intersection extends BinaryOperationBase
{

    public Intersection(OperationBase operandA, OperationBase operandB, int linePosition)
    {
        super(operandA, operandB, linePosition);
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation
    {
        Dataset resultA = this.operandA.execute().clone();
        Dataset resultB = this.operandB.execute().clone();
                
        ArrayList<Row> includeRows = new ArrayList<>();
        
        if (resultA.equalsSchema(resultB))
        {
            for (Row row : resultA.getRows())
            {
                if (resultB.getRows().contains(row))
                {
                    includeRows.add(row);
                    // remove rows that have been used
                    resultB.getRows().remove(row);
                }
            }
            
            return new Dataset("", resultA.getColumns(), includeRows);
        }
        
        throw new InvalidSchema(getLinePosition(), resultA.getName() + " and " + resultB.getName() + " does not have matching schemas.");      
    }
    
    @Override
    public String toString()
    {
        return "∩";
    }
}
