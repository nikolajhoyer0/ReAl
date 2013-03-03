package real.Objects.Parser;

import real.Enumerations.OpTypes;

public class Token
{
    public Token(final String symbol, final int precedence, final OpTypes associativity)
    {
        this.symbol = symbol;
        this.precedence = precedence;
        this.associativity = associativity;
    }
    
    public Token(final String symbol, final int precedence, final OpTypes associativity, final int linePosition, final int wordPosition)
    {
        this.symbol = symbol;
        this.precedence = precedence;
        this.associativity = associativity;
        this.linePosition = linePosition;
        this.wordPosition = wordPosition;
    }

    public void setLinePosition(final int linePosition)
    {
        this.linePosition = linePosition;
    }

    public void setWordPosition(final int wordPosition)
    {
        this.wordPosition = wordPosition;
    }

    public int getLinePosition()
    {
        return linePosition;
    }

    public int getWordPosition()
    {
        return wordPosition;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public OpTypes getAssociativity()
    {
        return associativity;
    }

    public int getPrecedence()
    {
        return precedence;
    }
    
    public boolean isBinary()
    {
        return associativity == OpTypes.LEFT || associativity == OpTypes.RIGHT;
    }
    
    private String symbol;
    private OpTypes associativity;
    private int precedence;
    private int linePosition;
    private int wordPosition;
}
