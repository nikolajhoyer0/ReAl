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
}
