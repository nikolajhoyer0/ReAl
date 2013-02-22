package real.BaseClasses;

public abstract class UnaryOperationBase extends OperationBase
{
    protected OperationBase operand;
    
    public UnaryOperationBase(OperationBase operand)
    {
        this.operand = operand;
    }

    public OperationBase getOperand()
    {
        return this.operand;
    }
}
