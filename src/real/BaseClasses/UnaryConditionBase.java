package real.BaseClasses;

public abstract class UnaryConditionBase extends ConditionBase
{
    protected ConditionBase operand;
    
    public UnaryConditionBase(ConditionBase operand)
    {
        this.operand = operand;
    }

    public ConditionBase getOperand()
    {
        return this.operand;
    }   
}