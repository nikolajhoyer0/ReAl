package real;

import javax.swing.UIManager;
import real.Objects.Kernel;
import real.Objects.Services.DataManager;
import real.Objects.Services.LocalDataManager;
import real.Objects.Services.MainWindow;



//gruppe id: 3
public class ReAl
{
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
            Kernel.AddService(new DataManager());
            Kernel.AddService(new MainWindow());
            Kernel.AddService(new LocalDataManager());
            Kernel.Run();       
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
        }        
    }
}
