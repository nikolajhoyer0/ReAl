
package real.Objects.Parser;

import java.util.HashMap;

public class TokenOpManager
{
    public TokenOpManager()
    {
       opTable = new HashMap<>(); 
    }
    
    public void addOp(Token op)
    {
        opTable.put(op.getSymbol(), op);
    }
    
    public Token getOp(String symbol)
    {
        return opTable.get(symbol);
    }
    
    private HashMap<String, Token> opTable;
}
