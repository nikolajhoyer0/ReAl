package real.BaseClasses;

import real.Enumerations.DataType;

/*
 * for condtions that only use one operand
 */
public abstract class UnaryConditionBase extends ConditionBase
{
    protected ConditionBase operand;
    
    public UnaryConditionBase(ConditionBase operand, DataType type)
    {
        super(type);
        this.operand = operand;
    }

    public ConditionBase getOperand()
    {
        return this.operand;
    }   
}