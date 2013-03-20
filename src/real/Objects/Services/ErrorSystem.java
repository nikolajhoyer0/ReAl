package real.Objects.Services;

import java.util.Observable;

public class ErrorSystem extends Observable
{
    public void print(Exception e)
    {
        
    }
    
    public void print(String message)
    {
        this.notifyObservers(message);
    }
}
