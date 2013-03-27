package real.Objects.RAOperations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import real.BaseClasses.ConditionBase;
import real.BaseClasses.OperationBase;
import real.BaseClasses.UnaryOperationBase;
import real.Enumerations.DataType;
import real.Objects.Comparators.BooleanComparator;
import real.Objects.Comparators.ChainedComparator;
import real.Objects.Comparators.NumberComparator;
import real.Objects.Comparators.StringComparator;
import real.Objects.ConditionOperations.Atomic.AttributeLiteral;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchAttribute;
import real.Objects.Utility;


public class Sorting extends UnaryOperationBase
{
    private ConditionBase[] conditions;
    
    public Sorting(OperationBase operand, ConditionBase[] conditions, int linePosition)
    {
        super(operand, linePosition);
        this.conditions = conditions;
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation
    {

        ArrayList<Comparator> comparators = new ArrayList<>();
        ArrayList<String> attributeNames = new ArrayList<>();
        Dataset result = this.operand.execute().clone();
        
        for (ConditionBase condition : conditions)
        {

            if (condition instanceof AttributeLiteral)
            {
                AttributeLiteral att = (AttributeLiteral)condition;
                
                attributeNames.add(att.getColumnName());
                
                if(att.getType() == DataType.BOOLEAN)
                {
                    comparators.add(new BooleanComparator(att.getColumnName()));
                }
                
                else if(att.getType() == DataType.NUMBER)
                {
                    comparators.add(new NumberComparator(att.getColumnName()));
                }
                
                else if(att.getType() == DataType.STRING)
                {
                    comparators.add(new StringComparator(att.getColumnName()));
                }
                
            }
            
            else
            {
                throw new InvalidParameters(getLinePosition(), "Sort must only contain one attribute in each comma seperation.");
            }
        }
        
        if(Utility.haveDuplicates(attributeNames.toArray(new String[0])) == true)
        {
            throw new InvalidParameters(getLinePosition(), "Can't have sorting keys that are the same.");
        }
        
        Collections.sort(result.getRows(), new ChainedComparator(comparators));
        
        return result;
    }
}
