
package real.Objects.Services;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import real.BaseClasses.ServiceBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidDataset;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.Row;

/**
 * The class that allows you to load in files and store them as datasets.
 */
public class DataManager extends ServiceBase
{

    private ArrayList<Dataset> datasets;

    public DataManager()
    {
        this.datasets = new ArrayList<>();
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

    public void setDataset(Dataset value)
    {
        this.datasets.remove(this.find(value.getName()));
        this.datasets.add(value);
    }

    public void LoadDataset(String filepath) throws InvalidDataset
    {
        Path path = Paths.get(filepath);
        try (Scanner scanner = new Scanner(path))
        {
            if (scanner.hasNextLine())
            {
                String datasetname = this.filename(filepath);
                String[] columns = scanner.nextLine().split(",");
                ArrayList<Row> rows = new ArrayList<>();
                while (scanner.hasNextLine())
                {
                    rows.add(new Row(scanner.nextLine(), columns));
                }
                scanner.close();
                this.setDataset(new Dataset(datasetname, columns, rows));
            }
        }
        catch (ArrayIndexOutOfBoundsException | IOException e)
        {
            throw new InvalidDataset();
        }
    }

    public ArrayList<Dataset> GetAllDatasets()
    {
        return this.datasets;
    }

    private Dataset find(String name)
    {
        if (name != null && !name.equals(""))
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

    private String filename(String filename)
    {
        int dot = filename.lastIndexOf(".");
        int sep = filename.lastIndexOf("/");
        return filename.substring(sep + 1, dot);
    }
}