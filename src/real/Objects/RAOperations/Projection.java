package real.Objects.RAOperations;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.BaseClasses.OperationBase;
import real.BaseClasses.UnaryConditionBase;
import real.BaseClasses.UnaryOperationBase;
import real.Enumerations.DataType;
import real.Objects.Column;
import real.Objects.ConditionOperations.Atomic.AttributeLiteral;
import real.Objects.ConditionOperations.Rename;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.Row;
import real.Objects.Utility;

public class Projection extends UnaryOperationBase
{
    private ConditionBase[] conditions;
    
    public Projection(OperationBase operand, ConditionBase[] conditions)
    {
        super(operand);
        this.conditions = conditions;
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset, InvalidParameters, InvalidEvaluation
    {
        Dataset result = this.operand.execute();
        ArrayList<Row> rows = new ArrayList<>();
        ArrayList<Column> columns = new ArrayList<>();
        
        //allocate the hashmaps
        for(int i = 0; i < result.getRows().size(); ++i)
        {
            rows.add(new Row(new HashMap<String, String>()));
        }
        
        
        //there must only be one attribute if there are no renaming operators.
        //if there is one renaming operator there must only exist two attributes
        for(int i = 0; i < conditions.length; ++i)
        {
            int renameAmount = amountRename(conditions[i]); 
                        
            if(renameAmount == 1)
            {
                if(conditions[i] instanceof Rename)
                {

                    Rename rename = (Rename) conditions[i];
                    
                    //find the attribute that is used to find which column we need to change                 
                    if(amountCondition(rename.getOperandA()) != 1 && rename.getOperandB() instanceof AttributeLiteral)
                    {
                        throw new InvalidParameters("Only one attribute on each side.");
                    }
                               
                    AttributeLiteral newAttribute = (AttributeLiteral)rename.getOperandB();
                    AttributeLiteral attribute = findAttribute(rename.getOperandA());
  
                    Column column = new Column(newAttribute.getColumnName(), result.getColumn(attribute.getColumnName()).getDataType());
                    columns.add(column);
                    
                    for(int r = 0; r < result.getRows().size(); ++r)
                    {
                        Object value = rename.getOperandA().evaluate(result.getRows().get(r));
                        DataType type = rename.getOperandA().getType();
                        
                        if (type == DataType.STRING)
                        {
                            rows.get(r).setValue(column.getName(), (String) value);
                        }
                        
                        else if (type == DataType.BOOLEAN)
                        {
                            String str = String.valueOf((boolean) value);
                            rows.get(r).setValue(column.getName(), str);
                        }
                        
                        else if (type == DataType.NUMBER)
                        {
                            String str = String.valueOf((float) value);
                            
                            rows.get(r).setValue(column.getName(), Utility.trimTrailingZeros(str));
                        }
                    }
                }
            }
            else if(renameAmount == 0)
            {
                
                if(amountCondition(conditions[i]) != 1)
                {
                    throw new InvalidParameters("Only one attribute allowed in expression.");
                }
                
                AttributeLiteral attribute = findAttribute(conditions[i]);
                System.out.println(attribute);
                Column column = new Column(attribute.getColumnName(), result.getColumn(attribute.getColumnName()).getDataType());
                columns.add(column);

                for (int r = 0; r < result.getRows().size(); ++r)
                {
                    Object value = conditions[i].evaluate(result.getRows().get(r));
                    DataType type = conditions[i].getType();
                    
                    if (type == DataType.STRING)
                    {
                        rows.get(r).setValue(column.getName(), (String) value);
                    }
                    
                    else if(type == DataType.BOOLEAN)
                    {
                        String str = String.valueOf((boolean)value);
                        rows.get(r).setValue(column.getName(), str);
                    }
                    
                    else if(type == DataType.NUMBER)
                    {
                        String str = String.valueOf((float)value);
                        rows.get(r).setValue(column.getName(), Utility.trimTrailingZeros(str));
                    }
                }
            }
            
            else if(renameAmount > 1)
            {
                throw new InvalidParameters("There can only be one rename operator in each expression.");
            }
            
            else
            {
                throw new InvalidParameters("Invalid Projection.");
            }
            
        }
 
        if(Utility.haveColumnDuplicates(columns.toArray(new Column[0])))
        {
            throw new InvalidParameters("Can't have multiple columns with the same name.");
        }
        
        return new Dataset("", columns, rows);
    }
    
    private int amountCondition(ConditionBase base)
    {

        if (base != null)
        {
            if (base instanceof BinaryConditionBase)
            {
                BinaryConditionBase bin = (BinaryConditionBase) base;
                return amountCondition(bin.getOperandA()) + amountCondition(bin.getOperandB());
            }
            else
            {
                if (base instanceof AttributeLiteral)
                {
                    return amountCondition(null) + 1;
                }
                else
                {
                    UnaryConditionBase unary = (UnaryConditionBase)base;
                    return amountCondition(unary.getOperand());
                }
            }
        }
        
        return 0;
    }
    
    private int amountRename(ConditionBase base)
    {

        if (base != null)
        {
            if (base instanceof BinaryConditionBase)
            {
                BinaryConditionBase bin = (BinaryConditionBase) base;
                
                if (base instanceof Rename)
                {
                     return amountRename(bin.getOperandA()) + amountRename(bin.getOperandB()) + 1;
                }
                
                else
                {
                    return amountRename(bin.getOperandA()) + amountRename(bin.getOperandB());
                }

            }
            else
            {
                UnaryConditionBase unary = (UnaryConditionBase) base;
                return amountRename(unary.getOperand());
            }
        }
        
        return 0;
    }
    
    private AttributeLiteral findAttribute(ConditionBase base)
    {      
        if (base != null)
        {
            if (base instanceof BinaryConditionBase)
            {
                BinaryConditionBase bin = (BinaryConditionBase) base;
                
                AttributeLiteral att = findAttribute(bin.getOperandA());
                
                if(att != null)
                {
                    return att;
                }
                
                findAttribute(bin.getOperandB());
            }
            else
            {
                UnaryConditionBase unary = (UnaryConditionBase) base;            
                findAttribute(unary.getOperand());      
            }
        }
      
        if (base instanceof AttributeLiteral)
        {
            return (AttributeLiteral) base;
        }

        return null;
    }
}
