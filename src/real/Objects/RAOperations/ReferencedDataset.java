package real.Objects.RAOperations;

import real.BaseClasses.OperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.NoSuchAttribute;
import real.Objects.Kernel;
import real.Objects.Services.DataManager;
import real.Objects.Services.LocalDataManager;

public class ReferencedDataset extends OperationBase
{
    private String name;

    public ReferencedDataset(String name, int linePosition)
    {
        super(linePosition);
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
    
    public OperationBase getOperand()
    {
        return Kernel.GetService(LocalDataManager.class).findOperation(name);
    }
    
    @Override
    public Dataset execute() throws NoSuchAttribute
    {
        //always look for the local since it weighs more
        Dataset dataset = Kernel.GetService(LocalDataManager.class).findDataset(this.name);
        
        if(dataset != null)
        {
            return dataset;
        }
        
        else
        {
            dataset = Kernel.GetService(DataManager.class).getDataset(this.name);
            
            if(dataset != null)
            {
                return dataset;
            }
            
            else
            {
                throw new NoSuchAttribute(getLinePosition(), "Attribute " + this.name + " does not exist.");
            }
        }
    }

}
