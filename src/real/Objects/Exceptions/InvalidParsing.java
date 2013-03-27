package real.Objects.Exceptions;

public class InvalidParsing extends RealException
{ 
    public InvalidParsing(int linePosition, String message)
    {
        super(linePosition, message);
    }
}
