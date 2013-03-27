
package real.Objects.Exceptions;

public class WrongType extends RealException
{
    public WrongType(int linePosition, String message)
    {
        super(linePosition, message);
    }
}
