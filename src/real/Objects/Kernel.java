package real.Objects;

import java.util.ArrayList;
import java.util.List;
import real.Interfaces.IService;

// Implementation of the "service locator"-pattern.
// The purpose of the kernel is to provide access between services, along with tiered initialization.
public class Kernel
{
    private static List<IService> services = new ArrayList<>();

    public static <T> T GetService(Class<T> type)
    {
        for (IService service : services)
        {
            if (type.isInstance(service))
            {
                return (T) service;
            }
        }
        return null;
    }
    
    public static void AddService(IService service)
    {
        if (services != null)
        {
            services.add(service);
        }
    }

    public static void Run()
    {
        // Initialize all services
        for (IService service : services)
        {
            service.Initialize();
        }
        
        // Start all services
        for (IService service : services)
        {
            service.Start();
        }
    }

   public static void Stop()
    {
        // Stop all services
        for (IService service : services)
        {
            service.Stop();
        }
    }
}
