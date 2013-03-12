package real.Objects;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import real.BaseClasses.ConditionBase;
import real.BaseClasses.OperationBase;
import real.Enumerations.OpTypes;
import real.Objects.ConditionOperations.Add;
import real.Objects.ConditionOperations.Atomic.AttributeLiteral;
import real.Objects.ConditionOperations.Atomic.BooleanLiteral;
import real.Objects.ConditionOperations.Atomic.NumberLiteral;
import real.Objects.ConditionOperations.Atomic.StringLiteral;
import real.Objects.ConditionOperations.BooleanOperations.And;
import real.Objects.ConditionOperations.BooleanOperations.Equal;
import real.Objects.ConditionOperations.BooleanOperations.Greater;
import real.Objects.ConditionOperations.BooleanOperations.GreaterEqual;
import real.Objects.ConditionOperations.BooleanOperations.Less;
import real.Objects.ConditionOperations.BooleanOperations.LessEqual;
import real.Objects.ConditionOperations.BooleanOperations.Not;
import real.Objects.ConditionOperations.BooleanOperations.Or;
import real.Objects.ConditionOperations.Div;
import real.Objects.ConditionOperations.Mult;
import real.Objects.ConditionOperations.Sub;
import real.Objects.Exceptions.InvalidParsing;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.Exceptions.WrongType;
import real.Objects.Parser.ExpressionParser;
import real.Objects.Parser.Token;
import real.Objects.Parser.TokenOpManager;
import real.Objects.Parser.TokenStream;
import real.Objects.Parser.TokenTree;
import real.Objects.RAOperations.Intersection;
import real.Objects.RAOperations.NaturalJoin;
import real.Objects.RAOperations.ReferencedDataset;
import real.Objects.RAOperations.Selection;
import real.Objects.RAOperations.Union;

public class Query
{
    private ExpressionParser parser;
    private TokenTree current;
    private HashMap<String, OperationBase> datasets;
    private OperationBase currentData;
            
    public Query()
    {
        TokenOpManager opManager = new TokenOpManager();
        datasets = new HashMap<>();
        
        opManager.addOp(new Token("+", 4, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("-", 4, EnumSet.of(OpTypes.LEFT, OpTypes.UNARY)));
        opManager.addOp(new Token("*", 6, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("/", 6, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("=", 2, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("<=", 2, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token(">=", 2, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("<", 2, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token(">", 2, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("AND", 1, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("OR", 0, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("^", 9, EnumSet.of(OpTypes.RIGHT)));
        
        //function operators
        opManager.addOp(new Token("π", 0, EnumSet.of(OpTypes.NONE)));  
        opManager.addOp(new Token("δ", 0, EnumSet.of(OpTypes.NONE)));   
        opManager.addOp(new Token("ρ", 0, EnumSet.of(OpTypes.NONE)));   
        opManager.addOp(new Token("γ", 0, EnumSet.of(OpTypes.NONE))); 
        opManager.addOp(new Token("τ", 0, EnumSet.of(OpTypes.NONE)));
        
        //relational binary operators      
        //todo: figure out the proper precendence for each operator.
        opManager.addOp(new Token("∪", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("∩", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("‒", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("×", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("⋈", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("→", 2, EnumSet.of(OpTypes.LEFT))); 
        opManager.addOp(new Token("⟕", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("⟖", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("⟗", 6, EnumSet.of(OpTypes.LEFT))); 
        
        TokenStream tokenStream = new TokenStream(opManager);

        parser = new ExpressionParser(tokenStream);
    }
    
    private LinkedList<TokenTree> parse(String str)
    {
        try
        {
            return parser.parse(str);
        }
        catch (InvalidParsing ex)
        {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }   
    }
    
    public Dataset interpret(String str) throws InvalidSchema, NoSuchDataset
    {
        LinkedList<TokenTree> trees = parse(str);
              
        if (trees != null)
        {
            for (int i = 0; i < trees.size(); ++i)
            {
                current = trees.get(i);
                
                //must be a value to be saved
                if(current.getToken().getSymbol().equals("="))
                {
                    TokenTree[] children = current.getChildren();                    
                    currentData = interpretOperation(children[1]);
                    datasets.put(children[0].getToken().getSymbol(), currentData);
                }
                
                else
                {
                    //we need to make a observer error class so we can send
                    //errors to the errorview
                    System.out.println("must declare sentence with 'var = expression'.");
                    break;
                }                         
            }
            
             return currentData.execute();
        }
        
        return null;
    }

    //inteprets the relation alg and uses interpret condition to interpret the condtions.
    public OperationBase interpretOperation(TokenTree tree)
    {
        String word = tree.getToken().getSymbol();
        TokenTree[] children = tree.getChildren();
        
        try
        {

            switch (word)
            {
                case "∪":
                    return new Union(interpretOperation(children[0]), interpretOperation(children[1]));
                case "∩":
                    return new Intersection(interpretOperation(children[0]), interpretOperation(children[1]));
                case "Attribute":
                    return new ReferencedDataset(children[0].getToken().getSymbol());
                case "⋈":
                    return new NaturalJoin(interpretOperation(children[0]), interpretOperation(children[1]));
                case "δ":
                    OperationBase relation = interpretOperation(children[1]);
                    ConditionBase condition = interpretCondition(children[0], relation);
                    return new Selection(relation, condition);
                default:
                    System.out.println(word + "is not a supported operator");

            }

        }
        
        catch(WrongType ex)
        {
            System.out.println(ex.getMessage());
        }
        
        catch(InvalidSchema ex)
        {
            System.out.println(ex.getMessage());
        }
        
        catch(NoSuchDataset ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return null;
    }
    
    public ConditionBase interpretCondition(TokenTree tree, OperationBase relation) throws WrongType, InvalidSchema, NoSuchDataset
    {
        String word = tree.getToken().getSymbol();
        TokenTree[] children = tree.getChildren();
        
        switch(word)
        {
            case "+":
                return new Add(interpretCondition(children[0], relation),interpretCondition(children[1], relation));
            case "-":
                return new Sub(interpretCondition(children[0], relation),interpretCondition(children[1], relation));
            case "*":
                return new Mult(interpretCondition(children[0], relation),interpretCondition(children[1], relation));
            case "/":
                return new Div(interpretCondition(children[0], relation),interpretCondition(children[1], relation));
            case "=":
                return new Equal(interpretCondition(children[0], relation),interpretCondition(children[1], relation));
            case "<=":
                return new LessEqual(interpretCondition(children[0], relation),interpretCondition(children[1], relation));
            case ">=":
                return new GreaterEqual(interpretCondition(children[0], relation),interpretCondition(children[1], relation));    
            case ">":
                return new Greater(interpretCondition(children[0], relation),interpretCondition(children[1], relation)); 
            case "<":
                return new Less(interpretCondition(children[0], relation),interpretCondition(children[1], relation));
            case "!=":
                return new Not(interpretCondition(children[0], relation),interpretCondition(children[1], relation));
            case "AND":
                return new And(interpretCondition(children[0], relation),interpretCondition(children[1], relation));
            case "Or":
                return new Or(interpretCondition(children[0], relation),interpretCondition(children[1], relation));
            case "Attribute":
                String value = children[0].getToken().getSymbol();
                return new AttributeLiteral(value, relation.execute().getColumn(value).getDataType());
            case "String":
                return new StringLiteral(children[0].getToken().getSymbol());
            case "Number":
                float number = Float.parseFloat(children[0].getToken().getSymbol());
                return new NumberLiteral(number);
            case "Boolean":
                boolean b = Boolean.parseBoolean(children[0].getToken().getSymbol());
                return new BooleanLiteral(b);
        }
        
        
        //probably throw exception
        return null;
    }
}
