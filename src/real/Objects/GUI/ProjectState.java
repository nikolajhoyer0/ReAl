package real.Objects.GUI;

import java.io.Serializable;
import java.util.ArrayList;
import real.Objects.Dataset;

public class ProjectState implements Serializable
{ 
    private ArrayList<Dataset> datasets;
    private ArrayList<TextQueryView> worksheets;
    
    public ProjectState(ArrayList<TextQueryView> worksheets,
                          ArrayList<Dataset> datasets)
    {
        this.datasets = datasets;
        this.worksheets = worksheets;
    }
    
    public ArrayList<Dataset> getDatasets()
    {
        return datasets;
    }
    
    public ArrayList<TextQueryView> getWorksheets()
    {
        return worksheets;
    }
}
