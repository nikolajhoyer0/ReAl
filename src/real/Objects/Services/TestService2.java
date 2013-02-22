
package real.Objects.Services;

import real.BaseClasses.ServiceBase;
import real.Interfaces.IStartEvent;
import real.Objects.Kernel;

public class TestService2 extends ServiceBase implements IStartEvent
{

    @Override
    public void Initialize()
    {
        // Setup event, so when TestService starts, a message will printed.
        
        TestService ts = Kernel.GetService(TestService.class);
        ts.addStartEventListener(this);
        
        //Setup event, so when TestService2 starts, a message will printed
        ts = Kernel.GetService(TestService.class);
        ts.addStartEventListener(new IStartEvent()
        {
            @Override
            public void OnStart()
            {
              //  System.out.print("TestService StartEvent fired-1\n");
            }
        });
        
    }

    @Override
    public void Start()
    {
        super.Start();
    }

    @Override
    public void Stop()
    {
    }

    @Override
    public void OnStart()
    {
        // System.out.print("TestService StartEvent fired-2\n");
    }
}
