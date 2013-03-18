package real.Objects.Services;

import java.util.HashMap;
import real.BaseClasses.OperationBase;
import real.BaseClasses.ServiceBase;
import real.Objects.Dataset;

//class that holds the localData for each tab
public class LocalDataManager extends ServiceBase
{
    private HashMap<String, Dataset> datasets;
    //store the condition for treeviewing.
    private HashMap<String, OperationBase> operations;
    
    public LocalDataManager()
    {
        this.datasets = new HashMap<>();
        this.operations = new HashMap<>();
    }
    
    public void clearLocal()
    {
        this.datasets.clear();
    }
    
    public void LoadDataset(Dataset dataset)
    {
        this.datasets.remove(dataset.getName());
        this.datasets.put(dataset.getName(), dataset);
    }
    
    public void LoadOperation(String name, OperationBase operation)
    {
        this.operations.remove(name);
        this.operations.put(name, operation);
    }
    
    public Dataset findDataset(String name)
    {
        return this.datasets.get(name);
    }
    
    public OperationBase findOperation(String name)
    {
        return this.operations.get(name);
    }
    
    public String[] getAllKeys()
    {
        return this.datasets.keySet().toArray(new String[0]);
    }
    
    public Dataset[] getAllDatasets()
    {
        return datasets.values().toArray(new Dataset[0]);
    }
}
