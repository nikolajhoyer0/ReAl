package real.Objects.RAOperations;

import real.BaseClasses.OperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.Kernel;
import real.Objects.Services.DataManager;

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
    
    @Override
    public Dataset execute() throws NoSuchDataset
    {
        return Kernel.GetService(DataManager.class).getDataset(this.name);
    }

}
