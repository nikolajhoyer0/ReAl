package real.Objects.RAOperations;

import real.BaseClasses.OperationBase;
import real.Enumerations.DataType;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchAttribute;

public class Tuple extends OperationBase
{
    private String[] values;
    private DataType[] types;
    
    public Tuple(String[] values, DataType[] types, int linePosition) throws InvalidParameters
    {
        super(linePosition);
        
        this.values = values;
        this.types = types;
        
        if(this.values.length != this.types.length)
        {
            //developer error.
            throw new InvalidParameters(getLinePosition(), "not the same amount of types and values.");
        }
    }
    
    public String[] getTupleValues()
    {
        return this.values;
    }
    
    public DataType[] getTupleTypes()
    {
        return this.types;
    }

    @Override
    public String toString()
    {
        String str = "";
        
        for(String value : values)
        {
            if(str.isEmpty())
            {
                str = "(" + value;
            }
            
            else
            {
                str = str + "," + value;
            }
        }
        
        return str + ")";
    }
    
    //should not be thrown but use getTupleValues and getTupleTypes - insures that only operation that use them support it.
    @Override
    public Dataset execute() throws InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation
    {
        throw new InvalidEvaluation(getLinePosition(), "Tuple is only supported in union for creation of data");
    }
}
