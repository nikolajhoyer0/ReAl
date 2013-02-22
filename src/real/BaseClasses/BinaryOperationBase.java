package real.BaseClasses;

public abstract class BinaryOperationBase extends OperationBase
{
    protected OperationBase operandA;
    protected OperationBase operandB;
    
    public BinaryOperationBase(OperationBase operandA, OperationBase operandB)
    {
        this.operandA = operandA;
        this.operandB = operandB;
    }

    public OperationBase getOperandA()
    {
        return this.operandA;
    }
    
        public OperationBase getOperandB()
    {
        return this.operandB;
    }
}

