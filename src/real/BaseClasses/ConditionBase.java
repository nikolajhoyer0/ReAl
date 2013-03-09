package real.BaseClasses;

import real.Objects.Column;
import real.Objects.Row;

/**
 * The base class for all condition operators - equals, greater than, etc
 */
public abstract class ConditionBase
{
    public ConditionBase()
    {
    }

    public abstract String evaluateString(Row row, Column column);
    public abstract int evaluateNumber(Row row, Column column);
    public abstract boolean evaluateBoolean(Row row, Column column);
}