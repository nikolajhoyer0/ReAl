package real.Objects.Parser;

import java.util.Scanner;
import real.Enumerations.OpTypes;
import real.Objects.Exceptions.InvalidParsing;

public class TokenStream
{
    public TokenStream(TokenOpManager manager)
    {
        opManager = manager;
    }
    
    public void read(String str)
    {
        scanner = new Scanner(str);
    }
    
    public Token next() throws InvalidParsing
    {
        if(full)
        {
            return buffer;
        }
                    
        else if(scanner.hasNext())
        {
            String word = scanner.next();            
            Token token = opManager.getOp(word);
                               
            if(token != null)
            {
                buffer = token;
            }
            
            else
            {
                buffer = new Token(word, 0, OpTypes.NONE);
            }
            
            full = true;
            return buffer;
        }
        
        else
        {
            throw new InvalidParsing();
        }
        
    }
    
    public void consume() throws InvalidParsing
    {
        if(full)
        {
            full = false;
        }
        
        else
        {
            throw new InvalidParsing("can't consume with buffer not full.");
        }
    }
        
    private boolean full;
    private Token buffer;   
    private Scanner scanner;
    private TokenOpManager opManager;
}
