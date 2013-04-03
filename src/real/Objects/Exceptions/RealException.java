
package real.Objects.Exceptions;


public class RealException extends Exception
{
    private String message;
    
    public RealException(int linePosition, String message)
    {
        if(linePosition != 0)
        {
            this.message = "Error in line " + linePosition + ": " + message;
        }
        
        else
        {
            this.message = "Error: " + message;
        }
    }
    
    public String getMessage()
    {
        return this.message;
    }
}
