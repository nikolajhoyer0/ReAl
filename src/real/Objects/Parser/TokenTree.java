package real.Objects.Parser;

public class TokenTree
{
    public TokenTree(final TokenTree[] children, final Token token)
    {
        this.children = children;
        this.token = token;
    }
    
    public TokenTree[] getChildren()
    {
        return this.children;
    }

    
    public Token getToken()
    {
        return this.token;
    } 
    
    private TokenTree[] children;
    private Token token;
}
