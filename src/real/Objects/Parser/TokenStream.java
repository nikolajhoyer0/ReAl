package real.Objects.Parser;

import java.util.EnumSet;
import java.util.LinkedList;
import real.Enumerations.OpTypes;
import real.Objects.Exceptions.InvalidParsing;
import real.Objects.Utility;

/**
 * Class that creates all the tokens and handles the stream.
 */
public class TokenStream
{
    public TokenStream(TokenOpManager manager)
    {
        opManager = manager;
        opManager.addOp(new Token("(", 0, EnumSet.of(OpTypes.BASE)));
        opManager.addOp(new Token(")", 0, EnumSet.of(OpTypes.BASE)));
        opManager.addOp(new Token(",", 0, EnumSet.of(OpTypes.BASE)));
        opManager.addOp(new Token("'", 0, EnumSet.of(OpTypes.BASE)));
        opManager.addOp(new Token("{", 0, EnumSet.of(OpTypes.BASE)));
        opManager.addOp(new Token("}", 0, EnumSet.of(OpTypes.BASE)));
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
            if(str.equals(tokenSymbol))
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
            throw new InvalidParsing(0, "end of stream.");
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
            throw new InvalidParsing(0, "can't consume with buffer not full.");
        }
    }
 
    private Token[] collectAllTokens(final String str) throws InvalidParsing
    {
        //fields for string literal, making sure that they give the right line and word positions.
        boolean isStringLiteral = false;
        boolean goNextLine = false;
        
        int length = str.length();
        int linePosition = 1;
        int wordPosition = 1;
        String operatorBuffer = "";
        String wordBuffer = "";
 
        LinkedList<Character> wordList = new LinkedList<>();   
        LinkedList<Token> tokenList = new LinkedList<>();
        
        //for handling spaces
        boolean isSpace = false;
        
        
        if (length > 0)
        {
            char chr;
            int index = 0;

            while (index < length)
            {
                chr = str.charAt(index);
                 
                //must be start of string or end.
                if(chr == '\'')
                {
                    //to be sure that we consider the whole string literal as one word
                    if(isStringLiteral)
                    {
                        isStringLiteral = false;
                    }
                    
                    else
                    {
                        //set the word and line position to the start of the string
                        isStringLiteral = true;
                        //the word position must go back because it meets operator '
                        wordPosition--;
                    }
                    
                    
                    if(!wordList.isEmpty())
                    {
                        wordBuffer = Utility.getStringRepresentation(wordList);
                        wordList.clear();
                    }
                    
                    operatorBuffer = Character.toString(chr);
                }
                
                else if(str.length() > index+2 && opManager.getNonLetterOp(new String(new char[] {chr, str.charAt(index+1), str.charAt(index+2)})) != null)
                {
                    if(!wordList.isEmpty())
                    {
                        wordBuffer = Utility.getStringRepresentation(wordList);
                        wordList.clear();
                    }
                    
                    operatorBuffer = new String(new char[] {chr, str.charAt(index+1), str.charAt(index+2)});
                    index+=2;
                }
                
                else if(str.length() > index+1 && opManager.getNonLetterOp(new String(new char[] {chr, str.charAt(index+1)})) != null)
                {
                    if(!wordList.isEmpty())
                    {
                        wordBuffer = Utility.getStringRepresentation(wordList);
                        wordList.clear();
                    }
                    
                    operatorBuffer = new String(new char[] {chr, str.charAt(index+1)});
                    index++;
                }
                
                else if (opManager.getNonLetterOp(Character.toString(chr)) != null)
                {
                     
                    if(!wordList.isEmpty())
                    {
                        wordBuffer = Utility.getStringRepresentation(wordList);
                        wordList.clear();
                    }
                    
                    operatorBuffer = Character.toString(chr);
                }
                
                else if (chr == ' ')
                {

                    if (!isStringLiteral)
                    {
                        if (!wordList.isEmpty())
                        {
                            wordBuffer = Utility.getStringRepresentation(wordList);
                            wordList.clear();
                        }
                        
                        isSpace = true;
                    }
                    
                    else
                    {
                        wordList.add(chr);
                    }
                }
                
                else if(chr == '\n')
                {
                    if(!wordList.isEmpty())
                    {
                        wordBuffer = Utility.getStringRepresentation(wordList);
                        wordList.clear();
                    }
                    
                    goNextLine = true;
                    wordPosition = 1;
                }
                
                //must be number or string
                else
                {
                    wordList.add(chr);
                }
                       
                //word is guarenteed to be before operator
                if (!wordBuffer.isEmpty())
                {

                    Token token = opManager.getOp(wordBuffer);
                    
                    if (token != null)
                    {
                        token.setLinePosition(linePosition);
                        token.setWordPosition(wordPosition);
                        tokenList.add(token);
                        
                        if (!isStringLiteral)
                        {
                            wordPosition++;
                        }
                        
                        wordBuffer = "";
                    }
                    
                    else
                    {

                        tokenList.add(new Token(wordBuffer, 0, EnumSet.of(OpTypes.NONE), linePosition, wordPosition));

                        if (!isStringLiteral)
                        {
                            wordPosition++;
                        }

                        wordBuffer = "";
                    }
                }
                
                if(!operatorBuffer.isEmpty())
                {
                    Token token = opManager.getOp(operatorBuffer);
                    
                    if(token == null)
                    {
                        throw new InvalidParsing(linePosition, "No such operator is defined: " + operatorBuffer);
                    }
                    
                    else
                    {
                        token.setLinePosition(linePosition);
                        token.setWordPosition(wordPosition);
                        tokenList.add(token);                                  
                        operatorBuffer = "";
                        
                        if(!isStringLiteral)
                        {
                            wordPosition++;
                        }
                    }
                }
                
                if(isSpace)
                {                    
                    tokenList.add(new Token(" ", 0, EnumSet.of(OpTypes.NONE)));
                    isSpace = false;                   
                }
                
                if(goNextLine)
                {
                    linePosition++;
                    goNextLine = false;
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
