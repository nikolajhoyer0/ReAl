package real.Objects.Exceptions;

public class InvalidRenaming  extends RealException
{  
    public InvalidRenaming(int linePosition, String message)
    {       
        super(linePosition, message);
    } 
}
