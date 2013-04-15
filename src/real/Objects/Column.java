
package real.Objects;

import java.io.Serializable;
import java.util.Objects;
import real.Enumerations.DataType;

/**
 * Class that allows you to store column names and their datatype.
 */

public class Column implements Serializable
{

    private String name;
    private DataType dataType;

    public Column(String name, DataType dataType)
    {
        this.name = name;
        this.dataType = dataType;
    }

    public String getName()
    {
        return this.name;
    }

    public DataType getDataType()
    {
        return this.dataType;
    }

    @Override
    public Column clone()
    {
        return new Column(this.name, this.dataType);
    }

    @Override
    public boolean equals(Object instance)
    {
        if (this == instance)
        {
            return true;
        }
        if (!(instance instanceof Column))
        {
            return false;
        }
        Column other = (Column) instance;
        return this.name.equals(other.name) && this.dataType.equals(other.dataType);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.name);
        hash = 13 * hash + (this.dataType != null ? this.dataType.hashCode() : 0);
        return hash;
    }
}
