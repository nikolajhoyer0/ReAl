package real;

import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import javax.swing.UIManager;
import real.Objects.Kernel;
import real.Objects.Services.DataManager;
import real.Objects.Services.ErrorSystem;
import real.Objects.Services.LocalDataManager;
import real.Objects.Services.MainWindow;

//gruppe id: 3
public class ReAl
{
    public static void main(String[] args)
    {
        try
        {    
            Properties props = new Properties();
            props.put("logoString", "");
            
            System.setProperty("awt.useSystemAAFontSettings","on");
            System.setProperty("swing.aatext", "true");
            
            com.jtattoo.plaf.smart.SmartLookAndFeel.setCurrentTheme(props);
            UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");  
            Kernel.AddService(new ErrorSystem());
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
