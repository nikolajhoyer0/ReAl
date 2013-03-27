package real.Objects.Exceptions;

public class InvalidParameters extends RealException
{
    public InvalidParameters(int linePosition, String message)
    {
        super(linePosition, message);
    }
}
