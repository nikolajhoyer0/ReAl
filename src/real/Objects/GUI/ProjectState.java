package real.Objects.GUI;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import real.Objects.Dataset;

public class ProjectState implements Serializable
{ 
    private ArrayList<Dataset> datasets;
    private List<Map.Entry<String,String>> worksheets;

    
    public ProjectState(ArrayList<TextQueryView> worksheets,
                          ArrayList<Dataset> datasets)
    {
        this.worksheets = new ArrayList<>();
        
        for(TextQueryView worksheet : worksheets)
        {
            Map.Entry<String,String> pair = new AbstractMap.SimpleEntry<>(worksheet.getName(), worksheet.getTextArea().getText());
            this.worksheets.add(pair);
        }

        this.datasets = datasets;
    }
    
    public ArrayList<Dataset> getDatasets()
    {
        return datasets;
    }
    
    public ArrayList<TextQueryView> getWorksheets()
    {
        ArrayList<TextQueryView> views = new ArrayList<>();
        
        for(Entry<String, String> worksheet : this.worksheets)
        {
            TextQueryView textQuery = new TextQueryView(worksheet.getKey());
            textQuery.getTextArea().setText(worksheet.getValue());
            views.add(textQuery);
        }
     
        return views;
    }
}
