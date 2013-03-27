package real.Objects.Exceptions;


public class NoSuchAttribute extends RealException
{
    public NoSuchAttribute(int linePosition, String message)
    {
        super(linePosition, message);
    }
}
