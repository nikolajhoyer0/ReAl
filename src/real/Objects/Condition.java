
package real.Objects;

import java.util.ArrayList;
import real.BaseClasses.ConditionBase;

public class Condition extends ConditionBase
{

    public Condition(String operandA, String operandB)
    {
        super(operandA, operandB);
    }

    @Override
    public boolean evaluate(ArrayList<Column> columns, Row row)
    {
        if (this.exists(columns, this.operandA))
        {
            this.operandA = row.getValue(this.operandA);
        }

        if (this.exists(columns, this.operandB))
        {
            this.operandB = row.getValue(this.operandB);
        }

        
        
        return true;
    }
}
