
package real.Objects.RAOperations;

import real.BaseClasses.BinaryOperationBase;
import real.BaseClasses.OperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;

/*
 * Implements Full Outer Join
 */
public class FullOuterJoin extends BinaryOperationBase
{
    public FullOuterJoin(OperationBase operandA, OperationBase operandB)
    {
        super(operandA, operandB);
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset, InvalidParameters, InvalidEvaluation
    {
        LeftOuterJoin leftJoin = new LeftOuterJoin(operandA, operandB);
        RightOuterJoin rightJoin = new RightOuterJoin(operandA, operandB);  
        
        Union union = new Union(leftJoin, rightJoin);
        
        return union.execute();
    }
}