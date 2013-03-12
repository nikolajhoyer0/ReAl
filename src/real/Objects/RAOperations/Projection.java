package real.Objects.RAOperations;

import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.BaseClasses.OperationBase;
import real.BaseClasses.UnaryConditionBase;
import real.BaseClasses.UnaryOperationBase;
import real.Objects.ConditionOperations.Atomic.AttributeLiteral;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;

public class Projection extends UnaryOperationBase
{
    private ConditionBase[] conditions;
    
    public Projection(OperationBase operand, ConditionBase[] conditions)
    {
        super(operand);
        this.conditions = conditions;
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset, InvalidParameters, InvalidEvaluation
    {
        Dataset result = this.operand.execute();

        
        //using the classes static for storing amount, since java not support static vals in functions.
        for(int i = 0; i < conditions.length; ++i)
        {
            int val = amountCondition(conditions[i]);
            
             System.out.println(val);
            
            if(val >= 2)
            {
                throw new InvalidParameters("Invalid Projection.");
            }
        }
 
        return null;
    }
    
    public int amountCondition(ConditionBase base)
    {

        if (base != null)
        {
            if (base instanceof BinaryConditionBase)
            {
                if (base instanceof AttributeLiteral)
                {
                    return amountCondition(null)+1;
                }
                else
                {
                    BinaryConditionBase bin = (BinaryConditionBase) base;
                    return amountCondition(bin.getOperandA()) + amountCondition(bin.getOperandB());
                }
            }
            else
            {
                if (base instanceof AttributeLiteral)
                {
                    return amountCondition(null) + 1;
                }
                else
                {
                    UnaryConditionBase unary = (UnaryConditionBase)base;
                    return amountCondition(unary.getOperand());
                }
            }
        }
        
        return 0;
    }
    
    public int amountRename(ConditionBase base)
    {

        if (base != null)
        {
            if (base instanceof BinaryConditionBase)
            {
                if (base instanceof AttributeLiteral)
                {
                    return amountCondition(null)+1;
                }
                else
                {
                    BinaryConditionBase bin = (BinaryConditionBase) base;
                    return amountCondition(bin.getOperandA()) + amountCondition(bin.getOperandB());
                }
            }
            else
            {
                if (base instanceof AttributeLiteral)
                {
                    return amountCondition(null) + 1;
                }
                else
                {
                    UnaryConditionBase unary = (UnaryConditionBase)base;
                    return amountCondition(unary.getOperand());
                }
            }
        }
        
        return 0;
    }
}
