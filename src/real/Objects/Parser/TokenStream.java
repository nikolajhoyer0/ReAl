package real.Objects.Parser;

import java.util.EnumSet;
import java.util.LinkedList;
import real.Enumerations.OpTypes;
import real.Objects.Exceptions.InvalidParsing;
import real.Objects.Utility;

public class TokenStream
{
    public TokenStream(TokenOpManager manager)
    {
        opManager = manager;
        opManager.addOp(new Token("(", 0, EnumSet.of(OpTypes.NONE)));
        opManager.addOp(new Token(")", 0, EnumSet.of(OpTypes.NONE)));
        opManager.addOp(new Token(",", 0, EnumSet.of(OpTypes.NONE)));
        opManager.addOp(new Token("'", 0, EnumSet.of(OpTypes.NONE)));
        opManager.addOp(new Token(" ", 0, EnumSet.of(OpTypes.NONE)));
        ignoreList = new LinkedList<>();
    }
    
    public void read(String str) throws InvalidParsing
    {
        consumed = 0;
        full = false;
        tokens = collectAllTokens(str + " end");
    }
    
    public void ignoreNextToken(String ignore)
    {
        ignoreList.add(ignore);
    }
    
    private boolean isValidToken(String tokenSymbol)
    {
        for(String str : ignoreList)
        {
            if(!str.equals(tokenSymbol))
            {
                return false;
            }
        }
        
        return true;
    }
    
    public void clearIgnore()
    {
        ignoreList.clear();
    }
    
    public Token next() throws InvalidParsing
    {
        if(full)
        {
            if(!isValidToken(buffer.getSymbol()))
            {
                full = false;
                return next();
            }
            
            return buffer;
        }
                    
        else if(consumed < tokens.length)
        {           
            Token token = tokens[consumed];    
            
            if(!isValidToken(token.getSymbol()))
            {
                consumed++;
                return next();
            }
            
            
            buffer = token;
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
 
    private Token[] collectAllTokens(final String str) throws InvalidParsing
    {
        int length = str.length();
        int linePosition = 1;
        int wordPosition = 1;
        String operatorBuffer = "";
        String wordBuffer = "";
        LinkedList<Character> wordList = new LinkedList<>();   
        LinkedList<Token> tokenList = new LinkedList<>();
        
        
        if (length > 0)
        {
            char chr;
            int index = 0;

            while (index < length)
            {
                chr = str.charAt(index);
                
                if(str.length() > index+2 && opManager.getOp(new String(new char[] {chr, str.charAt(index+1), str.charAt(index+2)})) != null)
                {
                    if(!wordList.isEmpty())
                    {
                        wordBuffer = Utility.getStringRepresentation(wordList);
                        wordList.clear();
                    }
                    
                    operatorBuffer = new String(new char[] {chr, str.charAt(index+1), str.charAt(index+2)});
                    index+=2;
                }
                
                else if(str.length() > index+1 && opManager.getOp(new String(new char[] {chr, str.charAt(index+1)})) != null)
                {
                    if(!wordList.isEmpty())
                    {
                        wordBuffer = Utility.getStringRepresentation(wordList);
                        wordList.clear();
                    }
                    
                    operatorBuffer = new String(new char[] {chr, str.charAt(index+1)});
                    index++;
                }
                
                else if (opManager.getOp(Character.toString(chr)) != null)
                {
                     
                    if(!wordList.isEmpty())
                    {
                        wordBuffer = Utility.getStringRepresentation(wordList);
                        wordList.clear();
                    }
                    
                    operatorBuffer = Character.toString(chr);
                }
                /*
                else if(chr == ' ')
                {
                    if(!wordList.isEmpty())
                    {
                        wordBuffer = Utility.getStringRepresentation(wordList);
                        wordList.clear();
                    }
                }
                */
                else if(chr == '\n')
                {
                    if(!wordList.isEmpty())
                    {
                        wordBuffer = Utility.getStringRepresentation(wordList);
                        wordList.clear();
                    }
                    
                    linePosition++;
                    wordPosition = 1;
                }
                
                //must be number or string
                else
                {
                    wordList.add(chr);
                }
                       
                //word is guarenteed to be before operator
                if(!wordBuffer.isEmpty())
                {
                    tokenList.add(new Token(wordBuffer, 0, EnumSet.of(OpTypes.NONE), linePosition, wordPosition));
                    wordPosition++;
                    wordBuffer = "";
                }
                
                if(!operatorBuffer.isEmpty())
                {
                    Token token = opManager.getOp(operatorBuffer);
                    
                    if(token == null)
                    {
                        throw new InvalidParsing("No such operator is defined: " + operatorBuffer);
                    }
                    
                    else
                    {
                        token.setLinePosition(linePosition);
                        token.setWordPosition(wordPosition);
                        tokenList.add(token);                                  
                        operatorBuffer = "";
                        wordPosition++;
                    }
                }
                
                index++;     
            } 
        }    
        
        return tokenList.toArray(new Token[0]);
    }

    private boolean full;
    private Token buffer;   
    private Token[] tokens;
    private int consumed;
    private TokenOpManager opManager;
    private LinkedList<String> ignoreList;
}
