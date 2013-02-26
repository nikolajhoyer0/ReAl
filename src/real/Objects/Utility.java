package real.Objects;

import java.util.List;
import real.Enumerations.DataType;

public class Utility
{

    public static DataType checkType(DataType type, String value)
    {
        DataType t = type;
        switch (t)
        {
            case UNKNOWN:
                if (isBoolean(value))
                {
                    return checkType(DataType.BOOLEAN, value);
                }
                return checkType(DataType.NUMBER, value);

            case NUMBER:
                if (!isNumber(value))
                {
                    return checkType(DataType.STRING, value);
                }
                break;

            case BOOLEAN:
                if (!isBoolean(value))
                {
                    return checkType(DataType.STRING, value);
                }
                break;
            case STRING:
                return DataType.STRING;

        }
        return t;
    }

    public static boolean isNumber(String input)
    {
        try
        {
            Float.parseFloat(input);
            return true;
        }
        catch (Exception e)
        {
        }
        return false;
    }

    public static boolean isBoolean(String input)
    {
        return input.equals("false") || input.equals("true");
    }
    
    public static String concatStringsWSep(List<String> strings, String separator)
    {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (String s : strings)
        {
            sb.append(sep).append(s);
            sep = separator;
        }
        return sb.toString();
    }
}
