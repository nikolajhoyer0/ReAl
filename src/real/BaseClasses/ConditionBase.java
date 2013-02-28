package real.BaseClasses;

import java.util.ArrayList;
import real.Objects.Column;
import real.Objects.Row;

public abstract class ConditionBase
{

    protected String operandA;
    protected String operandB;

    public ConditionBase(String operandA, String operandB)
    {
        this.operandA = operandA;
        this.operandB = operandB;
    }

    public abstract boolean evaluate(ArrayList<Column> columns, Row rows);

    public boolean exists(ArrayList<Column> columns, String columnname)
    {
        for (Column column : columns)
        {
            if (column.getName().equals(columnname))
            {
                return true;
            }
        }

        return false;
    }
}
