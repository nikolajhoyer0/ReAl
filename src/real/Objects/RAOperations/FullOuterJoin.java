
package real.Objects.RAOperations;

import real.BaseClasses.BinaryOperationBase;
import real.BaseClasses.OperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchAttribute;

/*
 * Implements Full Outer Join
 */
public class FullOuterJoin extends BinaryOperationBase
{
    public FullOuterJoin(OperationBase operandA, OperationBase operandB, int linePosition)
    {
        super(operandA, operandB, linePosition);
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation
    {
        LeftOuterJoin leftJoin = new LeftOuterJoin(operandA, operandB, getLinePosition());
        RightOuterJoin rightJoin = new RightOuterJoin(operandA, operandB, getLinePosition());  
        
        Union union = new Union(leftJoin, rightJoin, getLinePosition());
        
        return union.execute();
    }
}