package real.BaseClasses;

public abstract class BinaryConditionBase extends ConditionBase
{
    protected ConditionBase operandA;
    protected ConditionBase operandB;
    
    public BinaryConditionBase(ConditionBase operandA, ConditionBase operandB)
    {
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
