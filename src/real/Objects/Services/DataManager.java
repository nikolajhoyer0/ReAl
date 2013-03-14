
package real.Objects.Services;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import real.BaseClasses.ServiceBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.DatasetDuplicate;
import real.Objects.Exceptions.InvalidDataset;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.Row;

/**
 * The class that allows you to load in files and store them as datasets.
 */
public class DataManager extends ServiceBase
{

    private ArrayList<Dataset> datasets;
    private ArrayList<String> datasetNames;
    
    public DataManager()
    {
        this.datasets = new ArrayList<>();
        this.datasetNames = new ArrayList<>();
    }

    public Dataset getDataset(String name) throws NoSuchDataset
    {
        Dataset ds = this.find(name);
        if (ds != null)
        {
            return ds;
        }
        throw new NoSuchDataset();
    }
    
    public ArrayList<String> getAllNames()
    {
        return datasetNames;
    }

    public void setDataset(Dataset value)
    {
        this.datasets.remove(this.find(value.getName()));
        this.datasets.add(value);
    }

    public void LoadDataset(String filepath, String tableName) throws InvalidDataset, DatasetDuplicate
    {
        Path path = Paths.get(filepath);
        
        try (Scanner scanner = new Scanner(path))
        {
            if (scanner.hasNextLine())
            {
                String nextLine = scanner.nextLine();
                nextLine = nextLine.replaceAll("\\s+", "");
                String[] columns = nextLine.split(",");
                ArrayList<Row> rows = new ArrayList<>();
                while (scanner.hasNextLine())
                {
                    nextLine = scanner.nextLine();
                    rows.add(new Row(nextLine, columns));
                }
                scanner.close();
           
                if(this.find(tableName) != null)
                {
                    throw new DatasetDuplicate();
                }
                
                else
                {
                    this.datasets.add(new Dataset(tableName, columns, rows));
                    this.datasetNames.add(tableName);
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException | IOException e)
        {
            throw new InvalidDataset();
        }
    }

    public void removeDataset(String remove) throws NoSuchDataset
    {
        Dataset dataset = this.find(remove);
        
        if(dataset == null)
        {
            throw new NoSuchDataset();
        }
        
        else
        {
            this.datasets.remove(dataset);
            this.datasetNames.remove(remove);
        }
    }
    public ArrayList<Dataset> GetAllDatasets()
    {
        return this.datasets;
    }

    private Dataset find(String name)
    {
        if (name != null && !name.isEmpty())
        {
            for (Dataset dataset : this.datasets)
            {
                if (dataset.getName().equals(name))
                {
                    return dataset;
                }
            }
        }
        return null;
    } 
}