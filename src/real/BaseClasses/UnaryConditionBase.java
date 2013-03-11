package real.BaseClasses;

import real.Enumerations.DataType;

/*
 * for condtions that only use one operand
 */
public abstract class UnaryConditionBase extends ConditionBase
{
    protected ConditionBase operand;
    
    public UnaryConditionBase(ConditionBase operand)
    {
        super(operand.getType());
        this.operand = operand;
    }
    
    public UnaryConditionBase(ConditionBase operandA, DataType type)
    {
        super(type);
        this.operand = operandA;
    }

    public ConditionBase getOperand()
    {
        return this.operand;
    }   
}