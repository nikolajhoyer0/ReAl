
package real.Objects.RAOperations;

import real.BaseClasses.ConditionBase;
import real.BaseClasses.OperationBase;
import real.BaseClasses.UnaryOperationBase;
import real.Objects.Column;
import real.Objects.ConditionOperations.Atomic.AttributeLiteral;
import real.Objects.ConditionOperations.Rename;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchAttribute;
import real.Objects.Utility;

public class Renaming extends UnaryOperationBase
{
    private final ConditionBase[] conditions;
    
    public Renaming(OperationBase operand, ConditionBase[] conditions, int linePosition)
    {
        super(operand, linePosition);
        this.conditions = conditions;
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation
    {
        Dataset result = this.operand.execute();          
        Dataset dataset = result.clone();
        
        for(ConditionBase condition : conditions)
        {
            if(condition instanceof Rename)
            {
                Rename rename = (Rename)condition;
                
                if(rename.getOperandA() instanceof AttributeLiteral && rename.getOperandB() instanceof AttributeLiteral)
                {
                    AttributeLiteral at1 = (AttributeLiteral) rename.getOperandA();
                    AttributeLiteral at2 = (AttributeLiteral) rename.getOperandB();
                    
                    dataset.setColumnName(at1.getColumnName(), at2.getColumnName());
                }
                     
                else
                {
                    throw new InvalidEvaluation(getLinePosition(), "There must only be one attribute on each rename side.");
                }
            }
            
            else
            {
                throw new InvalidEvaluation(getLinePosition(), "Missing '->' operator");
            }
        }
        
        //check if the rename changes will be valid.
        //O(n^2) operation - shouldn't be a problem
        if(Utility.haveColumnDuplicates(dataset.getColumns().toArray(new Column[0])))
        {
            throw new InvalidParameters(getLinePosition(), "Can't have multiple columns with the same name.");
        }
              
        return dataset;
    }
}
