package real.BaseClasses;

import java.util.*;
import real.Interfaces.IService;
import real.Interfaces.IStartEvent;
import real.Interfaces.IStopEvent;

public abstract class ServiceBase implements IService
{
    private List<IStartEvent> startEventListeners;
    private List<IStopEvent> stopEventListeners;

    public ServiceBase()
    {
        this.startEventListeners = new ArrayList<>();
        this.stopEventListeners = new ArrayList<>();
    }

    public void addStopEventListener(IStopEvent listener)
    {
        stopEventListeners.add(listener);
    }

    public void addStartEventListener(IStartEvent listener)
    {
        startEventListeners.add(listener);
    }

    @Override
    public void Initialize()
    {
    }

    @Override
    public void Start()
    {
        for (IStartEvent listener : this.startEventListeners)
        {
            listener.OnStart();
        }
    }

    @Override
    public void Stop()
    {
        for (IStopEvent listener : this.stopEventListeners)
        {
            listener.OnStop();
        }
    }
}
