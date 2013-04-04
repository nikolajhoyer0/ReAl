package real.Objects.RAOperations;

import java.util.ArrayList;
import java.util.HashMap;
import real.BaseClasses.BinaryOperationBase;
import real.BaseClasses.OperationBase;
import real.Enumerations.DataType;
import real.Objects.Column;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchAttribute;
import real.Objects.Row;

public class Union extends BinaryOperationBase
{

    public Union(OperationBase operandA, OperationBase operandB, int linePosition)
    {
        super(operandA,operandB, linePosition);
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation
    {
        Dataset resultA = null;
        Dataset resultB = null;
        
        //start by checking if datasets are tupleLists.
        if(this.operandA instanceof TupleList && this.operandB instanceof TupleList)
        {
            throw new InvalidParameters(getLinePosition(), "Can't union with two tuples.");
        }
        else if(this.operandA instanceof TupleList)
        {
            TupleList tupleList = (TupleList) this.operandA;
            resultB = this.operandB.execute().clone();
                    
            resultA = TupleList.createDataset(tupleList, resultB);
            tupleList.setDependency(this.operandB);
            
            if(resultA == null)
            {
                throw new InvalidSchema(getLinePosition(), 
                        this.operandA.toString() + " and " + this.operandB.toString() + " does not have matching schemas.");
            }
        }
        
        
        else if(this.operandB instanceof TupleList)
        {
            TupleList tupleList = (TupleList) this.operandB;
            resultA = this.operandA.execute().clone();
                    
                         
            resultB = TupleList.createDataset(tupleList, resultA);
            tupleList.setDependency(this.operandA);
            
            if(resultB == null)
            {
                throw new InvalidSchema(getLinePosition(), 
                        this.operandA.toString() + " and " + this.operandB.toString() + " does not have matching schemas.");
            }
        }
        
        else
        {
            resultA = this.operandA.execute().clone();
            resultB = this.operandB.execute().clone();
        }
 
        ArrayList<Row> includeRows = new ArrayList<>();

        if (resultA.equalsSchema(resultB))
        {
            Dataset largestTable;
            Dataset smallestTable;
            
            if (resultA.getRowCount() > resultB.getRowCount()) {
                largestTable = resultA;
                smallestTable = resultB;
            }
            else {
                largestTable = resultB;
                smallestTable = resultA;
            }
            
            int lowestRowCount = smallestTable.getRowCount();
            int highestRowCount = largestTable.getRowCount();
            for (int i = 0; i < lowestRowCount; i++) {
                for (int k = 0; k < highestRowCount; k++) {
                    if (smallestTable.getRows().get(i).equals(largestTable.getRows().get(k))) {
                        largestTable.getRows().remove(k);
                        k--;
                        highestRowCount = largestTable.getRowCount();
                    }
                }
            }
            
            for (Row row : largestTable.getRows())
            {
                includeRows.add(row);
            }
            for (Row row : smallestTable.getRows()) {
                includeRows.add(row);
            }
            return new Dataset("", resultA.getColumns(), includeRows);
        }
        
        throw new InvalidSchema(getLinePosition(), resultA.getName() + " and " + resultB.getName() + " does not have matching schemas.");
    }
   
    @Override
    public String toString()
    {
        return "∪";
    }
}
