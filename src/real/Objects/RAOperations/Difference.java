
package real.Objects.RAOperations;

import java.util.ArrayList;
import java.util.Iterator;
import real.BaseClasses.BinaryOperationBase;
import real.BaseClasses.OperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchAttribute;
import real.Objects.Row;

/**
 * Implements the difference operation on bags.
 */
public class Difference extends BinaryOperationBase
{

    public Difference(OperationBase operandA, OperationBase operandB, int linePosition)
    {
        super(operandA, operandB, linePosition);
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation
    {
        Dataset resultA = this.operandA.execute().clone();
        Dataset resultB = this.operandB.execute().clone();
        
        if (resultA.equalsSchema(resultB))
        {                           
            Iterator it = resultA.getRows().iterator();
            
            while(it.hasNext())
            {
                Row row = (Row)it.next();
                
                if (resultB.getRows().contains(row))
                {
                    it.remove();
                    resultB.getRows().remove(row);
                }   
            }
       
            return new Dataset("", resultA.getColumns(), resultA.getRows());
        }
        
        throw new InvalidSchema(getLinePosition(), resultA.getName() + " and " + resultB.getName() + " does not have matching schemas."); 
    }
    
    @Override
    public String toString()
    {
        return "‒";
    }
}
