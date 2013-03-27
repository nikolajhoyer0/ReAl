package real.Objects.Services;

import java.util.Observable;
import real.Interfaces.IService;

public class ErrorSystem extends Observable implements IService
{
    public void print(Exception e)
    {      
        this.setChanged();
        this.notifyObservers(e.getMessage());        
    }
    
    public void print(String message)
    {      
        this.setChanged();
        this.notifyObservers(message);   
    }

    @Override
    public void Initialize()
    {
    }

    @Override
    public void Start()
    {
    }

    @Override
    public void Stop()
    {
    }
}
