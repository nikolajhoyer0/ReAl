package real.BaseClasses;

import real.Enumerations.DataType;

/*
 * for condtions that only use one operand
 */
public abstract class UnaryConditionBase extends ConditionBase
{
    protected ConditionBase operand;
    
    public UnaryConditionBase(ConditionBase operand, int linePosition)
    {
        super(operand.getType(), linePosition);
        this.operand = operand;
    }
    
    public UnaryConditionBase(ConditionBase operandA, DataType type, int linePosition)
    {
        super(type, linePosition);
        this.operand = operandA;
    }

    public ConditionBase getOperand()
    {
        return this.operand;
    }   
}