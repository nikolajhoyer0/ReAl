
package real.Objects.Exceptions;


public class RealException extends Exception
{
    private String message;
    
    public RealException(int linePosition, String message)
    {
        this.message = "Error in line " + linePosition + ": " + message;
    }
    
    public String getMessage()
    {
        return this.message;
    }
}
