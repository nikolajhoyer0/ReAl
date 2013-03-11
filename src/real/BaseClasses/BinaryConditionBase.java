package real.BaseClasses;

import real.Enumerations.DataType;

/**
 * Base class for binary operations
 */
public abstract class BinaryConditionBase extends ConditionBase
{
    protected ConditionBase operandA;
    protected ConditionBase operandB;
    
    
    public BinaryConditionBase(ConditionBase operandA, ConditionBase operandB)
    {
        super((operandA.getType() == operandB.getType()) ? operandB.getType() : DataType.UNKNOWN);
        this.operandA = operandA;
        this.operandB = operandB;
    }

    public ConditionBase getOperandA()
    {
        return this.operandA;
    }
    
        public ConditionBase getOperandB()
    {
        return this.operandB;
    }
}
