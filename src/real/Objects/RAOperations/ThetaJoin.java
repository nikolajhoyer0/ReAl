package real.Objects.RAOperations;

import real.BaseClasses.BinaryOperationBase;
import real.BaseClasses.ConditionBase;
import real.BaseClasses.OperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchAttribute;
/**
 * First iteration of theta join. TODO needs testing, decision wether or not to implement button, parsing etc.
 */
public class ThetaJoin extends BinaryOperationBase {

    private ConditionBase condition;

    public ThetaJoin(OperationBase operandA, OperationBase operandB, ConditionBase condition, int linePosition)
    {
        super(operandA, operandB, linePosition);
        this.condition = condition;
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation
    {
        Product product = new Product(operandA, operandB, getLinePosition());
        Selection selection = new Selection(product, condition, getLinePosition());
        
        return selection.execute();
    }
    /*
     * TODO: find out how to create subscript theta or find something more fitting for Theta join
     * page 45 DBW-book "Historically the theta refers to an arbitrary condition"
     */
    @Override
    public String toString()
    {
        return "⋈ θ " + this.condition.toString();
    }

    
}
