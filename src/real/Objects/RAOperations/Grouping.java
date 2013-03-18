package real.Objects.RAOperations;

import java.util.ArrayList;
import java.util.HashMap;
import real.BaseClasses.ConditionBase;
import real.BaseClasses.OperationBase;
import real.BaseClasses.UnaryOperationBase;
import real.Objects.Column;
import real.Objects.ConditionOperations.AggregateFunctions.AggregateCondition;
import real.Objects.ConditionOperations.Atomic.AttributeLiteral;
import real.Objects.ConditionOperations.Rename;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.Row;
import real.Objects.Utility;

public class Grouping extends UnaryOperationBase
{

    private ConditionBase[] conditions;
    private ConditionBase groupBy;
    
    public Grouping(OperationBase operand, ConditionBase groupBy, ConditionBase[] conditions)
    {
        super(operand);
        this.conditions = conditions;
        this.groupBy = groupBy;
    }
    
    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset, InvalidParameters, InvalidEvaluation
    {           
        ArrayList<Row> includeRows = new ArrayList<>();
        Dataset dataset = operand.execute().clone();
        ArrayList<Column> columns = getColumnsAndCheck(conditions, dataset);
        
        if(!(groupBy instanceof AttributeLiteral))
        {
            throw new InvalidParameters("Invalid group");
        }
        
        AttributeLiteral groupatt = (AttributeLiteral)groupBy;
        
        columns.add(0, new Column(groupatt.getColumnName(), dataset.getColumn(groupatt.getColumnName()).getDataType()));
        
        ArrayList<ArrayList<Row>> rowsOfRows = getRowsWithColumn(groupatt.getColumnName(), dataset);

        for (ArrayList<Row> rows : rowsOfRows)
        {
            Row addRow = new Row(new HashMap<String, String>());
            
            //set the group by value
            addRow.setValue(groupatt.getColumnName(), rows.get(0).getValue(groupatt.getColumnName()));
            
            for (ConditionBase condition : conditions)
            {

                if (condition instanceof Rename)
                {
                    Rename rename = (Rename) condition;
                    AttributeLiteral att = (AttributeLiteral) rename.getOperandB();
                    AggregateCondition agg = (AggregateCondition) rename.getOperandA();
                    
                    //set the aggreation value
                    addRow.setValue(att.getColumnName(), Utility.trimTrailingZeros(agg.aggregateEvaluate(rows)));                    
                }
                else
                {
                    throw new InvalidParameters("invalid grouping");
                }
                
            }
            
            includeRows.add(addRow);
        }
      
        return new Dataset("", columns, includeRows);
    }
    
    private ArrayList<ArrayList<Row>> getRowsWithColumn(String columnName, Dataset dataset)
    {
        ArrayList<ArrayList<Row>> rowOfRows = new ArrayList<>();       
        ArrayList<Row> rows = dataset.getRows();
        
        

        while (!rows.isEmpty())
        {
            Row check = null;
            ArrayList<Row> includeRows = new ArrayList<>();
            
            for (int i = 0; i < rows.size(); ++i)
            {
                if(check == null)
                {
                    check = rows.get(i);
                    includeRows.add(check);
                    rows.remove(i);
                    i--;
                }
                          
                else if(check.getValue(columnName).equals(rows.get(i).getValue(columnName)))
                {
                    includeRows.add(rows.get(i));
                    rows.remove(i);
                    i--;
                }  
            }
            
            rowOfRows.add(includeRows);
        }
        
        return rowOfRows;
    }
    
  
    
    
    //gets the columns and checks if the conditions are valid
    private ArrayList<Column> getColumnsAndCheck(ConditionBase[] conditions, Dataset dataset) throws InvalidParameters
    {
        ArrayList<Column> columns = new ArrayList<>();
        
        for (ConditionBase condition : conditions)
        {
            if (condition instanceof Rename)
            {
                Rename rename = (Rename) condition;
                
                if (!(rename.getOperandA() instanceof AggregateCondition))
                {
                    throw new InvalidParameters("invalid grouping");
                }


                if (!(rename.getOperandB() instanceof AttributeLiteral))
                {
                    throw new InvalidParameters("invalid grouping.");
                }
                
                AttributeLiteral att = (AttributeLiteral) rename.getOperandB();
                AggregateCondition agg = (AggregateCondition) rename.getOperandA();
                AttributeLiteral att2 = (AttributeLiteral) agg.getOperand();
                
                columns.add(new Column(att.getColumnName(), dataset.getColumn(att2.getColumnName()).getDataType()));
            }
            
            
        }
        
        return columns;
    }
}
