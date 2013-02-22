
package real.Objects.RAOperations;

import real.BaseClasses.BinaryOperationBase;
import real.BaseClasses.OperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;

//daniel: det burde være ret nemt at implementere den. se hvordan jeg gjorte det i
//intersection for hvordan man henter data.
//spørg mig hvis du har problemer.
public class Product extends BinaryOperationBase
{

    
    public Product(OperationBase operandA, OperationBase operandB)
    {
        super(operandA, operandB);
    }

    @Override
    public Dataset execute() throws InvalidSchema, NoSuchDataset
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
