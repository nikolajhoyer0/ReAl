
package real.Objects.Parser;

import java.util.HashMap;

/**
 * Class for storing the different operators - plus, minus etc
 */
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
