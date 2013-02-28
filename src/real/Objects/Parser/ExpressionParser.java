package real.Objects.Parser;

import java.util.LinkedList;
import real.Enumerations.OpTypes;
import real.Objects.Exceptions.InvalidParsing;


public class ExpressionParser
{
    public ExpressionParser(TokenStream tokenStream)
    {
        this.tokenStream = tokenStream;
    }
    
    public TokenTree parse(final String str) throws InvalidParsing
    {
        tokenStream.read(str + " end");
        return expression(0);
    }
    
    private TokenTree primary() throws InvalidParsing
    {
        Token token = tokenStream.next();
        
        if(token.getAssociativity() == OpTypes.UNARY)
        {
            tokenStream.consume();
            TokenTree[] tree = {expression(token.getPrecedence())};
            return new TokenTree(tree, token);
        }
        
        else if(token.getSymbol().equals("("))
        {
            tokenStream.consume();
            TokenTree tree = expression(0);
                             
            if(tokenStream.next().getSymbol().equals(")"))
            {
                tokenStream.consume();             
            }
            
            else
            {
                throw new InvalidParsing("Expecting end paranthese");
            }
              
            return tree;
        }
        
        else if (token.getSymbol().equals("selection"))
        {
            tokenStream.consume();
            TokenTree[] tree = {expression(0), primary()};
            return new TokenTree(tree, token);           
        }
        
        else if (token.getSymbol().equals("projection"))
        {
            tokenStream.consume();
            LinkedList<TokenTree> list = new LinkedList<>();
            list.add(expression(0));
            while(true)
            {            
                if(tokenStream.next().getSymbol().equals(","))
                {
                    tokenStream.consume();
                    list.add(expression(0));
                }
                
                else
                {
                    list.add(primary());
                    break;
                }
            }
            
            TokenTree[] trees = (TokenTree[])list.toArray(new TokenTree[0]);
            return new TokenTree(trees, token);
        }
        
        else if (token.getSymbol().equals("MAX"))
        {
            tokenStream.consume();
            TokenTree[] tree = {expression(0)};
            return new TokenTree(tree, token);
        }
        
        else
        {
            tokenStream.consume();
            return new TokenTree(null, token);
        }
    }

    private TokenTree expression(final int precedence) throws InvalidParsing
    {
        TokenTree tree = primary();
        
        while((tokenStream.next().isBinary() && tokenStream.next().getPrecedence() >= precedence)
                && tokenStream.next().getAssociativity() != OpTypes.NONE)
        {
            Token token = tokenStream.next();
            tokenStream.consume();
            
            int newPrecedence = 0;
            
            if(token.getAssociativity() == OpTypes.LEFT)
            {
                newPrecedence = token.getPrecedence() + 1;
            }
            
            else
            {
                newPrecedence = token.getPrecedence();
            }
            
            TokenTree[] fulltree = {expression(newPrecedence), tree};
            tree = new TokenTree(fulltree, token);
        }
        
        return tree;
    }
    
    private TokenStream tokenStream;
}
