package real.BaseClasses;

import real.Enumerations.DataType;

/**
 * Base class for binary operations
 */
public abstract class BinaryConditionBase extends ConditionBase
{
    protected ConditionBase operandA;
    protected ConditionBase operandB;
    
    
    public BinaryConditionBase(ConditionBase operandA, ConditionBase operandB, int linePosition)
    {
        super((operandA.getType() == operandB.getType()) ? operandB.getType() : DataType.UNKNOWN, linePosition);
        this.operandA = operandA;
        this.operandB = operandB;
    }
    
    public BinaryConditionBase(ConditionBase operandA, ConditionBase operandB, DataType type, int linePosition)
    {
        super((operandA.getType() == operandB.getType()) ? type : DataType.UNKNOWN, linePosition);
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
