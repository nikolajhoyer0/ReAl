package real.Objects.Parser;

import java.util.EnumSet;
import java.util.LinkedList;
import real.Enumerations.OpTypes;
import real.Objects.Exceptions.InvalidParsing;
import real.Objects.Utility;

/**
 * Class that parses relationel expressions.
 */
public class ExpressionParser
{
    public ExpressionParser(TokenStream tokenStream)
    {
        this.tokenStream = tokenStream;
        this.errorMessage = "";
    }
    
    public LinkedList<TokenTree> parse(final String str) throws InvalidParsing
    {
        LinkedList<TokenTree> treeList = new LinkedList<>();
        tokenStream.read(str + " end");
        tokenStream.ignoreNextToken(" ");
        while(!tokenStream.next().getSymbol().equals("end"))
        {
            treeList.add(expression(0));
        }
        
        return treeList;
    }
    
    private TokenTree primary() throws InvalidParsing
    {
        Token token = tokenStream.next();
        
        
        if(token.isBinary())
        {
            if (errorMessage.isEmpty())
            {
                throw new InvalidParsing(tokenStream.next().getLinePosition(), "Two binary operators in row.");
            }
            else
            {
                throw new InvalidParsing(tokenStream.next().getLinePosition(), errorMessage);
            }
        }
        
        if(token.getAssociativity().contains(OpTypes.UNARY))
        {
            tokenStream.consume();
            TokenTree[] tree = {expression(token.getPrecedence()+1)};
            return new TokenTree(tree, token);
        }
        
        else if (tokenStream.next().getSymbol().equals(")"))
        {
            if (errorMessage.isEmpty())
            {
                throw new InvalidParsing(tokenStream.next().getLinePosition(), "Unexpected ')'");
            }
            else
            {
                throw new InvalidParsing(tokenStream.next().getLinePosition(), errorMessage);
            }
        }
        
        
        else if(token.getSymbol().equals("("))
        {
            tokenStream.consume();
            TokenTree tree = expression(0);
                    
            //if it ends op reading the ")"
            if(tree.getChildren()[0].getToken().getSymbol().equals(")"))
            {
                if(errorMessage.isEmpty())
                {
                    throw new InvalidParsing(tree.getChildren()[0].getToken().getLinePosition(), "No expression in parathense.");
                }
                
                else
                {
                    throw new InvalidParsing(tree.getChildren()[0].getToken().getLinePosition(), errorMessage);
                }
            }
            
            if(tokenStream.next().getSymbol().equals(")"))
            {
                tokenStream.consume();             
            }
            
            else
            {              
                if(errorMessage.isEmpty())
                {
                    throw new InvalidParsing(tokenStream.next().getLinePosition(), "Expecting end paranthese");
                }
                
                else
                {
                    throw new InvalidParsing(tokenStream.next().getLinePosition(), errorMessage);
                }
            }
              
            return tree;
        }
        
        //handle string literal
        else if (token.getSymbol().equals("'"))
        {
            //we will not ignore space anymore
            tokenStream.clearIgnore();
            
            String concatStr = "";
            
            //making sure that we consider the whole string literal as one word.
            int wordPosition = token.getWordPosition();
            int linePosition = token.getLinePosition();
            
            tokenStream.consume();
            
            while(!tokenStream.next().getSymbol().equals("'"))
            {               
                concatStr = concatStr.concat(tokenStream.next().getSymbol());
                tokenStream.consume();
            }
            
            tokenStream.consume();
            Token tk = new Token(concatStr, 0, EnumSet.of(OpTypes.NONE), linePosition, wordPosition);
            TokenTree[] tree = {new TokenTree(null, tk)};
            
            //start ignoring space.
            tokenStream.ignoreNextToken(" ");
            return new TokenTree(tree, new Token("String", 0, EnumSet.of(OpTypes.NONE), linePosition, wordPosition));
        }
        
        //must be tuple generation
        //syntax {(value1, value2, ...), (tuble) ... }
        else if (token.getSymbol().equals("{"))
        {
            LinkedList<TokenTree> children = new LinkedList<>();
            tokenStream.consume();
            children.add(readTuple());
            //first parse all the tubles       
            while (true)
            {
                if (tokenStream.next().getSymbol().equals(","))
                {
                    tokenStream.consume();
                    children.add(readTuple());
                }
                
                else
                {
                    break;
                }
            }
            
            if (!tokenStream.next().getSymbol().equals("}"))
            {
                if (errorMessage.isEmpty())
                {
                    throw new InvalidParsing(tokenStream.next().getLinePosition(), "Expecting '}' for table creation");
                }
                
                else
                {
                    throw new InvalidParsing(tokenStream.next().getLinePosition(), errorMessage);
                }
            }

            else
            {
                tokenStream.consume();
            }
            
            return new TokenTree(children.toArray(new TokenTree[0]), new Token("Table", 0, EnumSet.of(OpTypes.NONE), token.getLinePosition(), token.getWordPosition()));
        }
       
        else if(token.getSymbol().equals("δ"))
        {
            errorMessage = "Parsing error in " + token.getSymbol();
            tokenStream.consume();
            TokenTree[] tree = {primary()};
            errorMessage = "";
            return new TokenTree(tree, token);           
        }
        
        else if (token.getSymbol().equals("σ"))
        {
            errorMessage = "Parsing error in " + token.getSymbol();
            tokenStream.consume();
            TokenTree[] tree = {expression(0), primary()};
            errorMessage = "";
            return new TokenTree(tree, token);           
        }
        
        else if (token.getSymbol().equals("ρ") || token.getSymbol().equals("π") || token.getSymbol().equals("τ") || token.getSymbol().equals("γ"))
        {     
            errorMessage = "Parsing error in " + token.getSymbol();
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
            errorMessage = "";
            return new TokenTree(trees, token);
        }
        
        else if (token.getSymbol().equals("Max") || token.getSymbol().equals("Sum") || 
                    token.getSymbol().equals("Average") || token.getSymbol().equals("Count") || token.getSymbol().equals("Min"))
        {
            errorMessage = "Parsing error in " + token.getSymbol();
            tokenStream.consume();
            TokenTree[] tree = {primary()};
            errorMessage = "";
            return new TokenTree(tree, token);
        }
        
        //numbers/attributes/booleans
        else
        {
            tokenStream.consume();
            
            if(Utility.isNumber(token.getSymbol()))
            {
                Token tk = new Token("Number", 0, EnumSet.of(OpTypes.NONE), token.getLinePosition(), token.getWordPosition());
                TokenTree[] tree = {new TokenTree(null, token)};
                return new TokenTree(tree, tk);
            }
            
            //boolean
            else if(token.getSymbol().equals("true") || token.getSymbol().equals("false"))
            {
                Token tk = new Token("Boolean", 0, EnumSet.of(OpTypes.NONE), token.getLinePosition(), token.getWordPosition());
                TokenTree[] tree = {new TokenTree(null, token)};
                return new TokenTree(tree, tk);
            }
            
            else
            {
                Token tk = new Token("Attribute", 0, EnumSet.of(OpTypes.NONE), token.getLinePosition(), token.getWordPosition());
                TokenTree[] tree = {new TokenTree(null, token)};
                return new TokenTree(tree, tk);
            }                     
        }
    }

    private TokenTree expression(final int precedence) throws InvalidParsing
    {
        TokenTree tree = primary();
        
        while((tokenStream.next().isBinary() && tokenStream.next().getPrecedence() >= precedence)
                && !tokenStream.next().getAssociativity().contains(OpTypes.NONE))
        {
            Token token = tokenStream.next();
            tokenStream.consume();
            
            int newPrecedence = 0;
            
            if(token.getAssociativity().contains(OpTypes.LEFT))
            {
                newPrecedence = token.getPrecedence() + 1;
            }
            
            else
            {
                newPrecedence = token.getPrecedence();
            }
            
            TokenTree[] fulltree = {tree, expression(newPrecedence)};
            tree = new TokenTree(fulltree, token);
        }
        
        return tree;
    }
    
    private TokenTree readTuple() throws InvalidParsing
    {
        if (!tokenStream.next().getSymbol().equals("("))
        {
            if (errorMessage.isEmpty())
            {
                throw new InvalidParsing(tokenStream.next().getLinePosition(), "Expecting start paranthese");
            }
            else
            {
                throw new InvalidParsing(tokenStream.next().getLinePosition(), errorMessage);
            }
        }
        
        Token token = tokenStream.next();
        tokenStream.consume();
        
        LinkedList<TokenTree> children = new LinkedList<>();

        children.add(primary());
                
        while (true)
        {     
            if(tokenStream.next().getSymbol().equals(","))
            {
                tokenStream.consume();
                children.add(primary());
            }
            
            else
            {
                break;
            }
        }

        if (!tokenStream.next().getSymbol().equals(")"))
        {
            if (errorMessage.isEmpty())
            {
                throw new InvalidParsing(tokenStream.next().getLinePosition(), "Expecting end paranthese");
            }
            else
            {
                throw new InvalidParsing(tokenStream.next().getLinePosition(), errorMessage);
            }
        }
        
        else
        {
            tokenStream.consume();
        }
        
        return new TokenTree(children.toArray(new TokenTree[0]), new Token("Tuple", 0, EnumSet.of(OpTypes.NONE), token.getLinePosition(), token.getWordPosition()));
    }

    private TokenStream tokenStream;
    private String errorMessage;
}
