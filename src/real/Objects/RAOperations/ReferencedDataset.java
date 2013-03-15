package real.Objects.RAOperations;

import real.BaseClasses.OperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.Kernel;
import real.Objects.Services.DataManager;
import real.Objects.Services.LocalDataManager;

public class ReferencedDataset extends OperationBase
{
    private String name;

    public ReferencedDataset(String name)
    {
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
    public Dataset execute() throws NoSuchDataset
    {
        //always look for the local since it weighs more
        Dataset dataset = Kernel.GetService(LocalDataManager.class).findDataset(this.name);
        
        if(dataset != null)
        {
            return dataset;
        }
        
        else
        {
            return Kernel.GetService(DataManager.class).getDataset(this.name);
        }
    }

}
