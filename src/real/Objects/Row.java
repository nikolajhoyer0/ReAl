
package real.Objects;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import real.Objects.Exceptions.InvalidDataset;


/**
 * This class is for storing tuples. Each value is saved based upon the column
 * name. 
 */
public class Row implements Serializable
{
    private Map<String, String> values;

    public Row(String value, String[] columns) throws InvalidDataset
    {
        this.values = new HashMap<>();
        this.Load(value, columns);
    }
    
    public Row(HashMap<String, String> values)
    {
       this.values = values; 
    }

    public String getValue(String column)
    {
        return this.values.containsKey(column) ? this.values.get(column) : "";
    }

    public void setValue(String column, String value)
    {
        this.values.put(column, value);
    }

    public void changeMapping(String current, String change)
    {
        String value = this.values.get(current);
        this.values.remove(current);
        this.values.put(change, value);
    }
    
    @Override
    public boolean equals(Object instance)
    {
        if (this == instance)
        {
            return true;
        }
        if (!(instance instanceof Row))
        {
            return false;
        }
        Row other = (Row) instance;
        return this.compare(this, other) && this.compare(other, this);
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.values);
        return hash;
    }

    @Override
    public Row clone()
    {
        HashMap<String, String> newValues = new HashMap<>(values);
        return new Row(newValues);
    }
    
    private boolean compare(Row row1, Row row2)
    {
        for (String column : row1.values.keySet())
        {
            if (!row2.values.containsKey(column) || !row1.values.get(column).equals(row2.values.get(column)))
            {
                return false;
            }
        }
        return true;
    }

    private void Load(String value, String[] columns) throws InvalidDataset
    {   
        int idx = 0;
        String[] values = value.split(",");
        
        if(columns.length != values.length)
        {
            throw new InvalidDataset("more values then columns in: " + values.toString());
        }
        
        for (String column : columns)
        {
            if (idx < values.length)
            {               
                int leftIndex;
                int rightIndex;
                
                //find where the first letter starts.
                for(leftIndex = 0; leftIndex < values[idx].length(); ++leftIndex)
                {
                    if(Character.isLetterOrDigit(values[idx].charAt(leftIndex)))
                    {
                        break;
                    }
                }
                
                //find where the last letter ends
                for(rightIndex = values[idx].length()-1; rightIndex >= 0; --rightIndex)
                {
                    if(Character.isLetterOrDigit(values[idx].charAt(rightIndex)))
                    {
                        break;
                    }
                }
                
                
                this.values.put(column, values[idx++].substring(leftIndex, rightIndex+1));
            }
            else
            {
                this.values.put(column, "");
            }
        }
    }
}
