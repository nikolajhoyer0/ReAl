package real.BaseClasses;

public abstract class UnaryOperationBase extends OperationBase
{
    protected OperationBase operand;
    
    public UnaryOperationBase(OperationBase operand, int linePosition)
    {
        super(linePosition);
        this.operand = operand;
    }

    public OperationBase getOperand()
    {
        return this.operand;
    }
}
