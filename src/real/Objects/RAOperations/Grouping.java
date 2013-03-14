package real.Objects.RAOperations;

import java.util.ArrayList;
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
        
        ArrayList<Column> columns = new ArrayList<>();
        ArrayList<Row> includeRows = new ArrayList<>();
        Dataset dataset = operand.execute();
        
        if(!(groupBy instanceof AttributeLiteral))
        {
            throw new InvalidParameters("Order by attribute invalid.");
        }
        
        AttributeLiteral groupatt = (AttributeLiteral)groupBy;
        
        ArrayList<ArrayList<Row>> rowsOfRows = getRowsWithColumn(groupatt.getColumnName(), dataset);
        
        for(ConditionBase condition : conditions)
        {
            
            if(condition instanceof Rename)
            {
                Rename rename = (Rename)condition;
                
                if(!(rename.getOperandA() instanceof AttributeLiteral))
                {
                    throw new InvalidParameters("invalid grouping");
                }
    
                
                if(!(rename.getOperandB() instanceof AggregateCondition))
                {
                    throw new InvalidParameters("invalid grouping.");
                }
                
                AttributeLiteral att = (AttributeLiteral)rename.getOperandA();
                AggregateCondition agg = (AggregateCondition)rename.getOperandB();
                
                columns.add(new Column(att.getColumnName(), dataset.getColumn(att.getColumnName()).getDataType()));
                
                
                for(ArrayList<Row> rows : rowsOfRows)
                {
                    //includeRows.add()agg.aggregateEvaluate(rows);
                }
                
                
            }
            
            else
            {
                throw new InvalidParameters("Grouping needs aggregate functions.");
            }
        }
        
        return null;
    }
    
    ArrayList<ArrayList<Row>> getRowsWithColumn(String columnName, Dataset dataset)
    {
        ArrayList<ArrayList<Row>> rowOfRows = new ArrayList<>();
        ArrayList<Row> includeRows = new ArrayList<>();
        ArrayList<Row> rows = dataset.getRows();
        
        while (!rows.isEmpty())
        {
            Row check = null;
            
            for (Row row : rows)
            {
                if(check == null)
                {
                    check = row;
                }
                
                if(check.getValue(columnName).equals(row.getValue(columnName)))
                {
                    includeRows.add(row);
                    rows.remove(row);
                }  
            }
            
            rowOfRows.add(includeRows);
            includeRows.clear();
        }
        
        return rowOfRows;
    }
}
