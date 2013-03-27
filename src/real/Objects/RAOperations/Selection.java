
package real.Objects.RAOperations;

import java.util.ArrayList;
import real.BaseClasses.BinaryConditionBase;
import real.BaseClasses.ConditionBase;
import real.BaseClasses.OperationBase;
import real.BaseClasses.UnaryOperationBase;
import real.Enumerations.DataType;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchAttribute;
import real.Objects.Row;


public class Selection extends UnaryOperationBase
{
    private ConditionBase condition;
    
    public Selection(OperationBase operand, ConditionBase condition, int linePosition)
    {
        super(operand, linePosition);
        this.condition = condition;
    }
    
    @Override
    public Dataset execute() throws InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation
    {      
        Dataset resultA = operand.execute();
        ArrayList<Row> rows = new ArrayList<>();
        
        for(int i = 0; i < resultA.getRows().size(); ++i)
        {
            if(condition.getType() != DataType.BOOLEAN)
            {
                throw new InvalidParameters(getLinePosition(), "Selection parameters must evaluate to boolean.");
            }
            
            if(!(condition instanceof BinaryConditionBase))
            {
                throw new InvalidParameters(getLinePosition(), "Parameter is invalid.");
            }
            
            if(condition.evaluateBoolean(resultA.getRows().get(i)))
            {
                rows.add(resultA.getRows().get(i));
            }
        }      
        
        return new Dataset("", resultA.getColumns(), rows);
    }
}
