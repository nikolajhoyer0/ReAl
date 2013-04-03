package real.Objects;

import java.util.List;
import real.Enumerations.DataType;

/**
 * A utility class that contain different useful functions.
 */
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
    
    public static String concatReverseStringsWSep(List<String> strings, String separator)
    {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (String s : strings)
        {
            sb.append(sep).append(s);
            sep = separator;
        }
        return sb.reverse().toString();
    }
    
    public static String getStringRepresentation(List<Character> list)
    {
        StringBuilder builder = new StringBuilder(list.size());
        for (Character ch : list)
        {
            builder.append(ch);
        }
        return builder.toString();
    }

    public static String trimTrailingZeros(String number)
    {
        if (!number.contains("."))
        {
            return number;
        }

        return number.replaceAll("\\.?0*$", "");
    }

    //O(n^2) but since it only will handle 1-16 columns the bad scale won't really matter.
    public static boolean haveColumnDuplicates(final Column[] columns)
    {
        boolean duplicates = false;
        for (int j = 0; j < columns.length; j++)
        {
            for (int k = j + 1; k < columns.length; k++)
            {
                if (k != j && columns[k].getName().equals(columns[j].getName()))
                {
                    duplicates = true;
                }
            }
        }

        return duplicates;
    }
    
    public static boolean haveDuplicates(final String[] strs)
    {
        boolean duplicates = false;
        for (int j = 0; j < strs.length; j++)
        {
            for (int k = j + 1; k < strs.length; k++)
            {
                if (k != j && strs[k].equals(strs[j]))
                {
                    duplicates = true;
                }
            }
        }

        return duplicates;
    }
    
    public static String filename(String filename)
    {
        int dot = filename.lastIndexOf(".");
        int sep = filename.lastIndexOf("/");
        return filename.substring(sep + 1, dot);
    }
    
    public static String addExtension(String filepath, String extension)
    { 
        if (filepath.lastIndexOf('.') == -1)
        {
            filepath += extension;
        }

        return filepath;
    }
}
