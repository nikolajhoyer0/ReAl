package real;

import real.Objects.Kernel;
import real.Objects.Services.DataManager;
import real.Objects.Services.MainWindow;
import real.Objects.Services.TestService;
import real.Objects.Services.TestService2;

public class ReAl
{
    public static void main(String[] args)
    {
        Kernel.AddService(new DataManager());
        Kernel.AddService(new TestService());
        Kernel.AddService(new TestService2());
        Kernel.AddService(new MainWindow());
        Kernel.Run();
    }
}
