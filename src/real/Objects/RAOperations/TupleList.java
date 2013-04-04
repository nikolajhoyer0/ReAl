package real.Objects.RAOperations;

import java.util.ArrayList;
import java.util.HashMap;
import real.BaseClasses.OperationBase;
import real.Enumerations.DataType;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchAttribute;
import real.Objects.Row;


public class TupleList extends OperationBase
{

    private Tuple[] tuples;
    private OperationBase dependency;
    
    public TupleList(OperationBase[] tuples, int linePosition) throws InvalidParameters
    {
        super(linePosition);
        
        //check if all the operationbases are tuples.      
        for(int i = 0; i < tuples.length; ++i)
        {
            if(!(tuples[i] instanceof Tuple))
            {
                throw new InvalidParameters(getLinePosition(), "Invalid tuple creation");
            }
        }
        
        this.tuples = (Tuple[])tuples;
        
         
        //make sure that all the same length
        //take the first tuple as reference
        int len = this.tuples[0].getTupleTypes().length;
        for(int i = 1; i < this.tuples.length; ++i)
        {
            if(len != this.tuples[i].getTupleTypes().length)
            {
                throw new InvalidParameters(getLinePosition(), "Tuples do not have the same amount of cells");
            }
        }
        
        //check if all the types are the same in each tuple.
        DataType referenceType;
    
        for (int i = 0; i < this.tuples[0].getTupleValues().length; ++i)
        {
            referenceType = DataType.NONE;

            for (int j = 0; j < this.tuples.length; ++j)
            {
                //first value we create the reference which all the values must equal.
                if(referenceType == DataType.NONE)
                {
                    referenceType = this.tuples[j].getTupleTypes()[i];
                }
                
                else if(referenceType != this.tuples[j].getTupleTypes()[i])
                {
                    throw new InvalidParameters(getLinePosition(), "Tuples do not share the same types");
                }
            }            
        }       
    }
    
    public Tuple[] getTuples()
    {
        return tuples;
    }
    
    //sets the operationbase that tuplelist infers its column names.
    public void setDependency(OperationBase base)
    {
        this.dependency = base;
    }
    
    public OperationBase getDependency()
    {
        return this.dependency;
    }
    
    //create the rows using the other dataset's columns.
    //returns null if invalid
    static public Dataset createDataset(TupleList tupleList, Dataset dataset)
    {
        ArrayList<Row> rows = new ArrayList<>();
        
        if(!checkValidTable(tupleList, dataset))
        {
            return null;
        }
        
        for(Tuple tuple : tupleList.getTuples())
        {
            HashMap<String, String> hashRow = new HashMap<>();
            
            for(int i = 0; i < tuple.getTupleValues().length; ++i)
            {
                hashRow.put(dataset.getColumns().get(i).getName(), tuple.getTupleValues()[i]);
            }
            
            rows.add(new Row(hashRow));
        }
        
        return new Dataset("", dataset.getColumns(), rows);
    }
    
    //checks to see if the types are the same as the dataset.
    static public boolean checkValidTable(TupleList tupleList, Dataset dataset)
    {
        //only need to check once since the tuplelist has already made sure that all tuples have the same types.
        Tuple[] tuples = tupleList.getTuples();
        DataType[] types = tuples[0].getTupleTypes();
        
        if(types.length != dataset.getColumns().size())
        {
            return false;
        }
        
        for(int i = 0; i < types.length; ++i)
        {
            if(!types[i].equals(dataset.getColumns().get(i).getDataType()))
            {
                return false;
            }
        }
        
        return true;
    }
    
    //should not be thrown but use getTupleValues and getTupleTypes, unsures that only operation that use them support it.
    @Override
    public Dataset execute() throws InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation
    {
        throw new InvalidEvaluation(getLinePosition(), "Tuples are only supported in union for creation of data");
    }
    
    @Override
    
    public String toString()
    {
        String str = "";
        
        for(Tuple tuple : tuples)
        {
            if(str.isEmpty())
            {
                str = "{" + tuple.toString();
            }
            
            else
            {
                str = str + "," + tuple.toString();
            }
        }
        
        return str + "}";
    }
}
