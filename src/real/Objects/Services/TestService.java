
package real.Objects.Services;

import real.BaseClasses.ServiceBase;
import real.Objects.Column;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidDataset;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.Kernel;
import real.Objects.RAOperations.Intersection;
import real.Objects.RAOperations.ReferencedDataset;
import real.Objects.RAOperations.Renaming;
import real.Objects.RAOperations.Union;
import real.Objects.Row;

public class TestService extends ServiceBase
{

    @Override
    public void Start()
    {
        try
        {
            super.Start();
            Kernel.GetService(DataManager.class).LoadDataset("test.csv");
            Kernel.GetService(DataManager.class).LoadDataset("test2.csv");
            Kernel.GetService(DataManager.class).LoadDataset("test3.csv");
        }
        catch (InvalidDataset ex)
        {
            System.out.print("InvalidDataset");
        }
    }

    public void Test()
    {
        try
        {
            // Create: ((test) U ((test) U (test)))
            //Union union = new Union(new Union(new ReferencedDataset("test"), new ReferencedDataset("test2")), new ReferencedDataset("test"));
            //Union union = new Union(new ReferencedDataset("test2"), new ReferencedDataset("test3"));
            //union = new Union(union, new ReferencedDataset("test"));
            //Dataset set = union.execute();
            
            //Intersection intersection = new Intersection(new ReferencedDataset("test"), new ReferencedDataset("test"));
            //Intersection intersection = new Intersection(new ReferencedDataset("test2"), new ReferencedDataset("test3"));
            //Dataset set = intersection.execute();
                            
            String[] columns = {"middlename", "lastname", "sirname"};
            String[] change = {"middle", "last", "first"};
            
            Renaming rename = new Renaming(new ReferencedDataset("test2"), columns, change);  
            
                               
            Dataset set = rename.execute();
            

                                   
            Kernel.GetService(MainWindow.class).setup(set);      
        }
        catch (NoSuchDataset ex)
        {
            System.out.print("NoSuchDataset");
        }
        catch (InvalidSchema ex)
        {
            System.out.print("InvalidSchemaException");
        }
    }
}
