package real.Objects.Parser;

import java.util.LinkedList;
import java.util.Scanner;
import real.Enumerations.OpTypes;
import real.Objects.Exceptions.InvalidParsing;
import real.Objects.Utility;

public class TokenStream
{
    public TokenStream(TokenOpManager manager)
    {
        opManager = manager;
    }
    
    public void read(String str)
    {
        consumed = 0;
        words = collectAllWords(str + " end");    
        int a = 0;
    }
    
    public Token next() throws InvalidParsing
    {
        if(full)
        {
            return buffer;
        }
                    
        else if(consumed < words.length)
        {
            String word = words[consumed];            
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
            consumed++;
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
 
    private String[] collectAllWords(final String str)
    {
        int length = str.length();
        LinkedList<String> stringList = new LinkedList<>();
        LinkedList<Character> wordList = new LinkedList<>();
        
        if (length > 0)
        {
            char chr;
            int index = 0;

            while (index < length)
            {
                chr = str.charAt(index);
                
                //will only read 3 length operator chars.
                if (opManager.getOp(Character.toString(chr)) != null)
                {
                     
                    if(!wordList.isEmpty())
                    {
                        stringList.add(Utility.getStringRepresentation(wordList));
                        wordList.clear();
                    }
                    
                    if(opManager.getOp(Character.toString(str.charAt(index+1))) != null)
                    {      
                        if(opManager.getOp(Character.toString(str.charAt(index+2))) != null)
                        {
                            stringList.add(new String(new char[] {chr, str.charAt(index+1), str.charAt(index+2)}));
                            index++;
                            index++;
                        }
                        
                        else
                        {
                            stringList.add(new String(new char[] {chr, str.charAt(index+1)}));
                            index++;
                        }
                    }
                    
                    else
                    {
                        stringList.add(Character.toString(chr));
                    }            
                }
                
                else if(chr == '(' || chr == ')' || chr == ',')
                {                 
                    if(!wordList.isEmpty())
                    {
                        stringList.add(Utility.getStringRepresentation(wordList));
                        wordList.clear();
                    }
                    
                    stringList.add(Character.toString(chr));
                }
                
                else if(chr == ' ')
                {
                    if(!wordList.isEmpty())
                    {
                        stringList.add(Utility.getStringRepresentation(wordList));
                        wordList.clear();
                    }
                }
                
                //must be number or string
                else
                {
                    wordList.add(chr);
                }
                                         
                index++;
            }
        }
        
        return stringList.toArray(new String[0]);
    }

    private boolean full;
    private Token buffer;   
    private String[] words;
    private int consumed;
    private TokenOpManager opManager;
}
