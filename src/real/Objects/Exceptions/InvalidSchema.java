package real.Objects.Exceptions;

public class InvalidSchema extends RealException
{
    public InvalidSchema(int linePosition, String message)
    {
        super(linePosition, message);
    }
}
