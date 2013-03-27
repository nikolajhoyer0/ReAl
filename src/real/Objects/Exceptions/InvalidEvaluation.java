package real.Objects.Exceptions;


public class InvalidEvaluation extends RealException
{
    public InvalidEvaluation(int linePosition, String message)
    {
        super(linePosition, message);
    }
}
