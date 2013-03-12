package real.Objects.Services;

import java.util.HashMap;
import real.BaseClasses.ServiceBase;
import real.Objects.Dataset;

//class that holds the localData for each tab
public class LocalDataManager extends ServiceBase
{
    private HashMap<String, Dataset> datasets;
   
    public LocalDataManager()
    {
        this.datasets = new HashMap<>();
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
    
    public Dataset findDataset(String name)
    {
        return this.datasets.get(name);
    }
    
    public Dataset[] getAllDatasets()
    {
        return datasets.values().toArray(new Dataset[0]);
    }
}
