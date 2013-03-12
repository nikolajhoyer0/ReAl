
package real.Objects.ConditionOperations.Atomic;

import real.BaseClasses.UnaryConditionBase;
import real.Enumerations.DataType;
import real.Objects.Row;


public class AttributeLiteral extends UnaryConditionBase
{
    
    String attributeName;
    
    public AttributeLiteral(String value, DataType type)
    {
        super(null, type);
        attributeName = value;
    }

    @Override
    public String evaluateString(Row row)
    {
        return row.getValue(attributeName);
    }

    @Override
    public float evaluateNumber(Row row)
    {
        return Float.parseFloat(row.getValue(attributeName));
    }

    @Override
    public boolean evaluateBoolean(Row row)
    {
        return Boolean.parseBoolean(row.getValue(attributeName));
    }
    
}
