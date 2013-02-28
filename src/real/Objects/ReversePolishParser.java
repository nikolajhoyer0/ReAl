package real.Objects;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import real.Enumerations.DataType;
import real.Objects.Utility;

public class ReversePolishParser
{
    public ReversePolishParser()
    {
        
    }

    public String parse(final String str)
    {
        String processedWords = this.spreadWords(str);
        processedWords = this.priorityFunctions(processedWords);   
        Scanner scanner = new Scanner(this.spreadWords(processedWords));
        this.priorityFunctions(processedWords);
        ArrayList<String> stack = new ArrayList<>();
        ArrayList<String> queue = new ArrayList<>();
        String word;
                     
        while (scanner.hasNext())
        {
            word = scanner.next();

            if (this.isIdentifier(word))
            {
                queue.add(word);
            }
            
            else if(this.isFunction(word))
            {
                stack.add(word);
            }
            
            else if (this.isOperator(word))
            {
                while (stack.size() > 0)
                {
                    String check = stack.get(stack.size() - 1);

                    if (this.isOperator(check) && this.leftAssociative(word)
                            && (this.getPrecedence(word) <= this.getPrecedence(check))
                            || (this.getPrecedence(word) < this.getPrecedence(check)))
                    {
                        queue.add(check);
                        stack.remove(stack.size() - 1);
                    }
                    
                    else
                    {
                        break;
                    }
                }

                stack.add(word);
            }
            
            else if (word.equals(","))
            {
                boolean found = false;
                String check = "";
                
                while(stack.size() > 0)
                {
                    check = stack.get(stack.size()-1);
                    
                    if(check.equals("("))
                    {
                        found = true;
                        break;
                    }
                    
                    else 
                    {
                        queue.add(check);
                        stack.remove(stack.size()-1);                        
                    }                                                     
                }
                
                if(!found)
                {
                    System.out.println("Error: separator or parentheses mismatched");
                }
            }
            
            else if (word.equals("("))
            {
                stack.add(word);
            }
            
            else if (word.equals(")"))
            {
                boolean found = false;
                
                while (stack.size() > 0)
                {
                    String check = stack.get(stack.size() - 1);

                    if (check.equals("("))
                    {
                        found = true;
                        break;
                    }
                    else
                    {
                        queue.add(check);
                        stack.remove(stack.size() - 1);
                    }
                }

                if (!found)
                {
                    System.out.println("Not end parentheses!");
                    return null;
                }

                stack.remove(stack.size() - 1);

                if (stack.size() > 0)
                {
                    //function
                }
            }
            
            else  {
                System.out.println("Unknown word");
                return null;
            }
        }
        
        while(stack.size() > 0)
        {
            String check = stack.get(stack.size()-1);
            
            if(check.equals("(") || check.equals(")"))
            {
                System.out.println("parentheses do not belong there");            
            }
        
            queue.add(check); 
            stack.remove(stack.size()-1);
        }
        
        String full = Utility.concatStringsWSep(queue, " ");
            
        return full;
    }
    
    private int getPrecedence(final String word)
    {
        switch (word)
        {
            case "*":
            case "/":
            case "%":
                return 4;
            case "+":
            case "-":
                return 3;
            case "=":   
            case "AND":
            case "and":    
            case ">":
            case "<":
            case "<=":
            case ">=":
            case "<>": 
                return 2;
        }

        return 0;
    }
 
    private boolean isFunction(final String word)
    {   
        switch (word)
        {
            case "projection":
            case "selection":
                return true;
        }
        
        return false;
    }
    
    private boolean leftAssociative(final String word)
    {
        switch (word)
        {
            case "*":
            case "/":
            case "%":
            case "+":
            case "-":  
            case "AND":
            case "and":    
                return true;
            case "=":
            case "!":                        
            case ">":
            case "<":
            case "<=":
            case ">=":             
            case "<>":       
                return false;
        }

        return false;
    }

    private int argCount(final String word)
    {
        switch (word)
        {
            case "*":
            case "/":
            case "%":
            case "+":
            case "-":
            case "=":
            case "AND":
            case "and":
            case ">":
            case "<":
            case "<=":
            case ">=":
            case "<>":
                return 2;
            case "!":
                return 1;
            default:
                return 1;
        }
    }
    
    private boolean isOperator(final String word)
    {
        switch (word)
        {
            case "*":
            case "/":
            case "%":
            case "+":
            case "-":
            case "=":
            case "!":
            case ">":
            case "<":
            case "<=":
            case ">=":
            case "<>":
            case "AND":
            case "and":
                return true;
        }
        
        return false;
    }
    
    private boolean isIdentifier(final String word)
    {
        return (!this.isFunction(word) && !this.isOperator(word)
                    && !word.equals(")") && !word.equals("(")) && !word.equals(",");
    }
    
    
    public String priorityFunctions(final String str)
    {
        LinkedList<String> storage = new LinkedList<>();
        String[] words = str.split("[ ]+");
        String word = "";
        int index = 0;
        
        while(index < words.length)
        {
            word = words[index];
                      
            if(word.equals("selection"))
            { 
                boolean wasOperator = true;
                storage.addLast("(");           
                storage.addLast(word);
                storage.addLast("(");
                index++;
                
                while(index < words.length)
                {
                    word = words[index];                 
                    if(this.isOperator(word))
                    {
                        wasOperator = true;
                        storage.addLast(word); 
                    }
                    
                    else if(this.isIdentifier(word))
                    {
                        storage.addLast(word);
                        
                        if(wasOperator)
                        {
                            wasOperator = false;
                        }
                        
                        else
                        {       
                            storage.add(storage.size()-2, ")");
                            storage.addLast(")");
                            index++;
                            break;
                        }
                    }
                    
                    else 
                    {
                       storage.addLast(word); 
                    }
                                
                    index++;
                }
            }
            
            else if(word.equals("projection"))
            { 
                boolean wasComma = true;
                storage.addLast("(");           
                storage.addLast(word);
                storage.addLast("(");
                index++;
                
                while(index < words.length)
                {
                    word = words[index];                 
                    if(word.equals(","))
                    {
                        wasComma = true;
                        storage.addLast(word); 
                    }
                    
                    else if(this.isIdentifier(word))
                    {
                        storage.addLast(word);
                        
                        if(wasComma)
                        {
                            wasComma = false;
                        }
                        
                        else
                        {       
                            storage.add(storage.size()-2, ")");
                            storage.addLast(")");
                            index++;
                            break;
                        }
                    }
                    
                    else 
                    {
                       storage.addLast(word); 
                    }
                                
                    index++;
                }
            }
            
           // else if(word.equals("rename"))
           // {
                
           // }
            
            else
            {
                storage.addLast(word);
                index++;
            }                           
        }
        
       
        return Utility.concatStringsWSep(storage, " ");
    }
    
    //todo perhaps check if a space is needed, instead of just doing it without checking. 
    //we could probably get some more performance, but it might not be that necessary. 
    public String spreadWords(final String str)
    {
        String createStr = "";
        int current = 0;
        int lastSub = 0;
        char chr = 0;
        boolean startedString = false;
       
        while(current < str.length())
        {
            chr = str.charAt(current);
            
            if(chr == '\'')
            {
                if (startedString)
                {                 
                    //slowest function evah
                    startedString = false;
                    
                    createStr = createStr.concat(str.substring(lastSub, current) +  str.substring(current, current + 1) + " ");

                    lastSub = current+1;
                }

                else
                {
                    startedString = true;  
                    
                    createStr = createStr.concat(str.substring(lastSub, current) + " " + str.substring(current, current+1));
                    
                    lastSub = current+1;
                }
                
            }
            
            else if (chr == '(')
            {
                createStr = createStr.concat(str.substring(lastSub, current) + " " + str.substring(current, current+1)) + "  ";               
                lastSub = current+1;
            }
            
            else if (chr == ',')
            {
                createStr = createStr.concat(str.substring(lastSub, current) + " " + str.substring(current, current+1)) + "  ";               
                lastSub = current+1;
            }
            
            else if (chr == ')')
            {
                createStr = createStr.concat(str.substring(lastSub, current) + " " + str.substring(current, current+1)) + " ";               
                lastSub = current+1;
            }
            
            else if (chr == '<' && str.charAt(current+1) == '=')
            {
                createStr = createStr.concat(str.substring(lastSub, current) + " " + str.substring(current, current+2)) + " ";               
                lastSub = current+2;
                current++;
            }
            
            else if (chr == '>' && str.charAt(current+1) == '=')
            {
                createStr = createStr.concat(str.substring(lastSub, current) + " " + str.substring(current, current+2)) + " ";               
                lastSub = current+2;
                current++;
            }
            
            else if (chr == '<' && str.charAt(current+1) == '>')
            {
                createStr = createStr.concat(str.substring(lastSub, current) + " " + str.substring(current, current+2)) + " ";               
                lastSub = current+2;
                current++;
            }
            
            else if (this.isOperator("" + chr))
            {             
                createStr = createStr.concat(str.substring(lastSub, current) + " " + str.substring(current, current+1) + " ");
                    
                lastSub = current+1;
            }

            current++;
        }       
        
        return createStr + str.substring(lastSub, str.length());
    }
}
