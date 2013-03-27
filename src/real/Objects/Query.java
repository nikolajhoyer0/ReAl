package real.Objects;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import real.BaseClasses.ConditionBase;
import real.BaseClasses.OperationBase;
import real.Enumerations.DataType;
import real.Enumerations.OpTypes;
import real.Objects.ConditionOperations.Add;
import real.Objects.ConditionOperations.AggregateFunctions.Count;
import real.Objects.ConditionOperations.AggregateFunctions.Max;
import real.Objects.ConditionOperations.AggregateFunctions.Min;
import real.Objects.ConditionOperations.AggregateFunctions.Sum;
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
import real.Objects.ConditionOperations.Rename;
import real.Objects.ConditionOperations.Sub;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidParsing;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchAttribute;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.Exceptions.WrongType;
import real.Objects.GUI.TreeViewTest;
import real.Objects.Parser.ExpressionParser;
import real.Objects.Parser.Token;
import real.Objects.Parser.TokenOpManager;
import real.Objects.Parser.TokenStream;
import real.Objects.Parser.TokenTree;
import real.Objects.RAOperations.Difference;
import real.Objects.RAOperations.DuplicateElimination;
import real.Objects.RAOperations.FullOuterJoin;
import real.Objects.RAOperations.Grouping;
import real.Objects.RAOperations.Intersection;
import real.Objects.RAOperations.LeftOuterJoin;
import real.Objects.RAOperations.NaturalJoin;
import real.Objects.RAOperations.Product;
import real.Objects.RAOperations.Projection;
import real.Objects.RAOperations.ReferencedDataset;
import real.Objects.RAOperations.Renaming;
import real.Objects.RAOperations.RightOuterJoin;
import real.Objects.RAOperations.Selection;
import real.Objects.RAOperations.Sorting;
import real.Objects.RAOperations.Union;
import real.Objects.Services.LocalDataManager;

public class Query
{
    private ExpressionParser parser;
    private TokenTree current;
    private OperationBase currentData;
    //will be removed.
    private TreeViewTest view;
            
    public Query()
    {
        
        //will be removed
        //view = new TreeViewTest();
        //view.setSize(800, 820);
        //view.setVisible(true);
        
        
        TokenOpManager opManager = new TokenOpManager();
        
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
        opManager.addOp(new Token("σ", 0, EnumSet.of(OpTypes.NONE)));   
        opManager.addOp(new Token("ρ", 0, EnumSet.of(OpTypes.NONE)));   
        opManager.addOp(new Token("γ", 0, EnumSet.of(OpTypes.NONE))); 
        opManager.addOp(new Token("τ", 0, EnumSet.of(OpTypes.NONE)));
        opManager.addOp(new Token("δ", 0, EnumSet.of(OpTypes.NONE)));
        
        //relational binary operators      
        //todo: figure out the proper precendence for each operator.
        opManager.addOp(new Token("∪", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("∩", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("‒", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("×", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("⋈", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("→", 3, EnumSet.of(OpTypes.LEFT))); 
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
    
    public Dataset interpret(String str) throws InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation, WrongType
    {
        LinkedList<TokenTree> trees = parse(str);
        LocalDataManager local = Kernel.GetService(LocalDataManager.class);  
        Dataset localData = null;
        int defaultNumber = 0;
        local.clearLocal();
          
        if (trees != null)
        {
            //will be removed
            //view.load(trees.get(0));
            
            for (int i = 0; i < trees.size(); ++i)
            {
                current = trees.get(i);
                
                //must be a value to be saved
                if(current.getToken().getSymbol().equals("="))
                {
                    TokenTree[] children = current.getChildren();                    
                    currentData = interpretOperation(children[1]);                                  
                    String name = children[0].getChildren()[0].getToken().getSymbol();
                    
                    Dataset data = currentData.execute();
                    
                    if(currentData == null || data == null)
                    {
                        throw new InvalidEvaluation(current.getToken().getLinePosition(), "invalid statement.");
                    }
                    
                    localData = data.clone();
                    localData.setName(name);
                    local.LoadDataset(localData);    
                    local.LoadOperation(name, currentData);
                }
                
                //default value given
                else
                {                                                    
                    String name = "Default_" + defaultNumber;
                    defaultNumber++;
                    
                    currentData = interpretOperation(current);
                    
                    Dataset data = currentData.execute();
                    
                    if(currentData == null || data == null)
                    {
                        throw new InvalidEvaluation(current.getToken().getLinePosition(), "invalid statement.");
                    }
                    
                    localData = data.clone();
                    localData.setName(name);
                    local.LoadDataset(localData);    
                    local.LoadOperation(name, currentData);
                }                         
            }
            
             return localData;
        }
        
        return null;
    }

    //inteprets the relation alg and uses interpret condition to interpret the condtions.
    public OperationBase interpretOperation(TokenTree tree) throws InvalidSchema, InvalidParameters, InvalidEvaluation, WrongType, NoSuchAttribute
    {
        String word = tree.getToken().getSymbol();
        TokenTree[] children = tree.getChildren();
        int linePosition = tree.getToken().getLinePosition();

        switch (word)
        {
            case "∪":
                return new Union(interpretOperation(children[0]), interpretOperation(children[1]), linePosition);
            case "∩":
                return new Intersection(interpretOperation(children[0]), interpretOperation(children[1]), linePosition);
            case "Attribute":
                return new ReferencedDataset(children[0].getToken().getSymbol(), children[0].getToken().getLinePosition());
            case "⋈":
                return new NaturalJoin(interpretOperation(children[0]), interpretOperation(children[1]), linePosition);
            case "⟕":
                return new LeftOuterJoin(interpretOperation(children[0]), interpretOperation(children[1]), linePosition);
            case "⟖":
                return new RightOuterJoin(interpretOperation(children[0]), interpretOperation(children[1]), linePosition);
            case "⟗":
                return new FullOuterJoin(interpretOperation(children[0]), interpretOperation(children[1]), linePosition);
            case "σ":
                OperationBase relation = interpretOperation(children[1]);
                ConditionBase condition = interpretCondition(children[0], relation, false);
                return new Selection(relation, condition, linePosition);
            case "π":
                relation = interpretOperation(children[children.length - 1]);
                ConditionBase[] conditions = getConditions(children, relation, false);
                return new Projection(relation, conditions, linePosition);
            case "ρ":
                relation = interpretOperation(children[children.length - 1]);
                conditions = getConditions(children, relation, false);
                return new Renaming(relation, conditions, linePosition);
            case "×":
                return new Product(interpretOperation(children[0]), interpretOperation(children[1]), linePosition);
            case "‒":
                return new Difference(interpretOperation(children[0]), interpretOperation(children[1]), linePosition);
            case "τ":
                relation = interpretOperation(children[children.length - 1]);
                conditions = getConditions(children, relation, false);
                return new Sorting(relation, conditions, linePosition);
            case "γ":
                relation = interpretOperation(children[children.length - 1]);
                ConditionBase groupBy = interpretCondition(children[0], relation, false);
                conditions = getGroupConditions(children, relation, false);
                return new Grouping(relation, groupBy, conditions, linePosition);
            case "δ":
                return new DuplicateElimination(interpretOperation(children[0]), linePosition);
            default:
                throw new InvalidEvaluation(tree.getToken().getLinePosition(), "invalid syntax");
        }
    }
    
    public ConditionBase interpretCondition(TokenTree tree, OperationBase relation, boolean ignoreNoAttribute) throws WrongType, 
                InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation
    {
        String word = tree.getToken().getSymbol();
        TokenTree[] children = tree.getChildren();
        int linePosition = tree.getToken().getLinePosition();          
        
        switch(word)
        {
            case "+":
                return new Add(interpretCondition(children[0], relation, ignoreNoAttribute),interpretCondition(children[1], relation, ignoreNoAttribute), linePosition);
            case "-":
                return new Sub(interpretCondition(children[0], relation, ignoreNoAttribute),interpretCondition(children[1], relation, ignoreNoAttribute), linePosition);
            case "*":
                return new Mult(interpretCondition(children[0], relation, ignoreNoAttribute),interpretCondition(children[1], relation, ignoreNoAttribute), linePosition);
            case "/":
                return new Div(interpretCondition(children[0], relation, ignoreNoAttribute),interpretCondition(children[1], relation, ignoreNoAttribute), linePosition);
            case "=":
                return new Equal(interpretCondition(children[0], relation, ignoreNoAttribute),interpretCondition(children[1], relation, ignoreNoAttribute), linePosition);
            case "<=":
                return new LessEqual(interpretCondition(children[0], relation, ignoreNoAttribute),interpretCondition(children[1], relation, ignoreNoAttribute), linePosition);
            case ">=":
                return new GreaterEqual(interpretCondition(children[0], relation, ignoreNoAttribute),interpretCondition(children[1], relation, ignoreNoAttribute), linePosition);    
            case ">":
                return new Greater(interpretCondition(children[0], relation, ignoreNoAttribute),interpretCondition(children[1], relation, ignoreNoAttribute), linePosition); 
            case "<":
                return new Less(interpretCondition(children[0], relation, ignoreNoAttribute),interpretCondition(children[1], relation, ignoreNoAttribute), linePosition);
            case "!=":
                return new Not(interpretCondition(children[0], relation, ignoreNoAttribute),interpretCondition(children[1], relation, ignoreNoAttribute), linePosition);
            case "AND":
                return new And(interpretCondition(children[0], relation, ignoreNoAttribute),interpretCondition(children[1], relation, ignoreNoAttribute), linePosition);
            case "OR":
                return new Or(interpretCondition(children[0], relation, ignoreNoAttribute),interpretCondition(children[1], relation, ignoreNoAttribute), linePosition);
            case "→":
                return new Rename(interpretCondition(children[0], relation, ignoreNoAttribute),interpretCondition(children[1], relation, true), linePosition);    
            case "Max":
                return new Max(interpretCondition(children[0], relation, ignoreNoAttribute), linePosition);
            case "Sum":
                return new Sum(interpretCondition(children[0], relation, ignoreNoAttribute), linePosition);
            case "Count":
                return new Count(interpretCondition(children[0], relation, ignoreNoAttribute), linePosition);
            case "Min":
                return new Min(interpretCondition(children[0], relation, ignoreNoAttribute), linePosition);
            case "Attribute":
                String value = children[0].getToken().getSymbol();
                
                if(ignoreNoAttribute == true)
                {
                    Column column = relation.execute().getColumn(value);
                    
                    if(column == null)
                    {
                        return new AttributeLiteral(value, DataType.UNKNOWN, children[0].getToken().getLinePosition());
                    }
                    
                    else
                    {
                        return new AttributeLiteral(value, column.getDataType(), children[0].getToken().getLinePosition());
                    }
                }
                             
                else
                {
                    Column column = relation.execute().getColumn(value);
                    
                    if(column == null)
                    {
                        throw new InvalidParameters(children[0].getToken().getLinePosition(), value + "is not a valid attribute.");
                    }
                    
                    else
                    {
                        return new AttributeLiteral(value, column.getDataType(), children[0].getToken().getLinePosition());
                    }
                }
               
            case "String":
                return new StringLiteral(children[0].getToken().getSymbol(), linePosition);
            case "Number":
                float number = Float.parseFloat(children[0].getToken().getSymbol());
                return new NumberLiteral(number, linePosition);
            case "Boolean":
                boolean b = Boolean.parseBoolean(children[0].getToken().getSymbol());
                return new BooleanLiteral(b, linePosition);       
            default:
                throw new InvalidEvaluation(tree.getToken().getLinePosition(), "Invalid syntax");
        }
    }
    
    //returns the conditions except the last one which is always a relation
    public ConditionBase[] getConditions(TokenTree[] children, OperationBase relation, boolean ignoreNoAttribute) throws WrongType, InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation
    {
        ArrayList<ConditionBase> bases = new ArrayList<>();
        
        for (int i = 0; i < children.length - 1; ++i)
        {
            bases.add(interpretCondition(children[i], relation, ignoreNoAttribute));
        }
      
        return bases.toArray(new ConditionBase[1]);
    }
    
    //returns the condition except the first and last, which are group and relation
    public ConditionBase[] getGroupConditions(TokenTree[] children, OperationBase relation, boolean ignoreNoAttribute) throws WrongType, InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation
    {
        ArrayList<ConditionBase> bases = new ArrayList<>();
        
        for (int i = 1; i < children.length - 1; ++i)
        {
            bases.add(interpretCondition(children[i], relation, ignoreNoAttribute));
        }
      
        return bases.toArray(new ConditionBase[1]);
    }
}
