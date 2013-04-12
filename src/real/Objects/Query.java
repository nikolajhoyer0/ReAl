package real.Objects;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedList;
import real.BaseClasses.ConditionBase;
import real.BaseClasses.OperationBase;
import real.Enumerations.DataType;
import real.Enumerations.OpTypes;
import real.Objects.ConditionOperations.AggregateFunctions.*;
import real.Objects.ConditionOperations.Atomic.*;
import real.Objects.ConditionOperations.BooleanOperations.*;
import real.Objects.ConditionOperations.*;
import real.Objects.Exceptions.*;
import real.Objects.GUI.TreeViewTest;
import real.Objects.Parser.*;
import real.Objects.RAOperations.*;
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
        view = new TreeViewTest();
        view.setSize(800, 820);
        view.setVisible(true);
        
        
        TokenOpManager opManager = new TokenOpManager();
        
        opManager.addOp(new Token("+", 4, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("-", 4, EnumSet.of(OpTypes.LEFT, OpTypes.UNARY)));
        opManager.addOp(new Token("*", 6, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("/", 6, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("=", 2, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("<=", 2, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token(">=", 2, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("!=", 2, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("<", 2, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token(">", 2, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("AND", 1, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("OR", 0, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("and", 1, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("or", 0, EnumSet.of(OpTypes.LEFT)));
        
        //function operators
        opManager.addOp(new Token("π", 0, EnumSet.of(OpTypes.FUNCTION, OpTypes.RELATIONAL)));  
        opManager.addOp(new Token("σ", 0, EnumSet.of(OpTypes.FUNCTION, OpTypes.RELATIONAL)));   
        opManager.addOp(new Token("ρ", 0, EnumSet.of(OpTypes.FUNCTION, OpTypes.RELATIONAL)));   
        opManager.addOp(new Token("γ", 0, EnumSet.of(OpTypes.FUNCTION, OpTypes.RELATIONAL))); 
        opManager.addOp(new Token("τ", 0, EnumSet.of(OpTypes.FUNCTION, OpTypes.RELATIONAL)));
        opManager.addOp(new Token("δ", 0, EnumSet.of(OpTypes.FUNCTION, OpTypes.RELATIONAL)));
        
        //relational binary operators      
        //todo: figure out the proper precendence for each operator.
        opManager.addOp(new Token("∪", 6, EnumSet.of(OpTypes.LEFT, OpTypes.RELATIONAL)));   
        opManager.addOp(new Token("∩", 6, EnumSet.of(OpTypes.LEFT, OpTypes.RELATIONAL)));   
        opManager.addOp(new Token("–", 6, EnumSet.of(OpTypes.LEFT, OpTypes.RELATIONAL)));   
        opManager.addOp(new Token("×", 6, EnumSet.of(OpTypes.LEFT, OpTypes.RELATIONAL)));   
        opManager.addOp(new Token("⋈", 6, EnumSet.of(OpTypes.LEFT, OpTypes.RELATIONAL)));   
        opManager.addOp(new Token("→", 3, EnumSet.of(OpTypes.LEFT, OpTypes.RELATIONAL))); 
        opManager.addOp(new Token("⟕", 6, EnumSet.of(OpTypes.LEFT, OpTypes.RELATIONAL)));   
        opManager.addOp(new Token("⟖", 6, EnumSet.of(OpTypes.LEFT, OpTypes.RELATIONAL)));   
        opManager.addOp(new Token("⟗", 6, EnumSet.of(OpTypes.LEFT, OpTypes.RELATIONAL))); 

        TokenStream tokenStream = new TokenStream(opManager);

        parser = new ExpressionParser(tokenStream);
    }
      
    private LinkedList<TokenTree> parse(String str) throws InvalidParsing
    {
        return parser.parse(str);
    }
    
    public Dataset interpret(String str) throws InvalidSchema, NoSuchAttribute, InvalidParameters, InvalidEvaluation, WrongType, InvalidParsing
    {
        LinkedList<TokenTree> trees = parse(str);
        LocalDataManager local = Kernel.GetService(LocalDataManager.class);  
        Dataset localData = null;
        int defaultNumber = 0;
        local.clearLocal();
          
        if (trees != null && !trees.isEmpty())
        {
            //will be removed
            view.load(trees.get(0));
 
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
                        throw new InvalidEvaluation(current.getToken().getLinePosition(), "invalid statement: ");
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
                        throw new InvalidEvaluation(current.getToken().getLinePosition(), "invalid statement");
                    }
                    
                    localData = data.clone();
                    localData.setName(name);
                    local.LoadDataset(localData);    
                    local.LoadOperation(name, currentData);
                }                         
            }
  
             return localData;
        }
        
        throw new InvalidEvaluation(0, "Nothing to evaluate.");
    }

    //inteprets the relation alg and uses interpret condition to interpret the condtions.
    public OperationBase interpretOperation(TokenTree tree) throws InvalidSchema, InvalidParameters, InvalidEvaluation, WrongType, NoSuchAttribute
    {
        String word = tree.getToken().getSymbol();
        TokenTree[] children = tree.getChildren();
        int linePosition = tree.getToken().getLinePosition();
        OperationBase relation;
        ConditionBase condition;
        
        switch (word)
        {
            case "∪":
                return new Union(interpretOperation(children[0]), interpretOperation(children[1]), linePosition);
            case "∩":
                return new Intersection(interpretOperation(children[0]), interpretOperation(children[1]), linePosition);
            case "Attribute":
                return new ReferencedDataset(children[0].getToken().getSymbol(), children[0].getToken().getLinePosition());
            case "⋈":
                if(children.length == 2)
                {
                    return new NaturalJoin(interpretOperation(children[0]), interpretOperation(children[1]), linePosition);
                }
                
                else
                {
                    Product product = new Product(interpretOperation(children[0]), interpretOperation(children[2]), linePosition);
                    condition = interpretCondition(children[1], product, false);
                    
                    return new ThetaJoin(product, condition, linePosition);
                }                
            case "⟕":
                return new LeftOuterJoin(interpretOperation(children[0]), interpretOperation(children[1]), linePosition);
            case "⟖":
                return new RightOuterJoin(interpretOperation(children[0]), interpretOperation(children[1]), linePosition);
            case "⟗":
                return new FullOuterJoin(interpretOperation(children[0]), interpretOperation(children[1]), linePosition);
            case "σ":
                relation = interpretOperation(children[1]);
                condition = interpretCondition(children[0], relation, false);
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
            case "–":
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
            case "Table":
                OperationBase[] operations = getTuples(children);
                return new TupleList(operations, linePosition);
            default:
                throw new InvalidEvaluation(linePosition, "invalid syntax: " + word);
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
            case "AND": case "and":
                return new And(interpretCondition(children[0], relation, ignoreNoAttribute),interpretCondition(children[1], relation, ignoreNoAttribute), linePosition);
            case "OR": case "or":
                return new Or(interpretCondition(children[0], relation, ignoreNoAttribute),interpretCondition(children[1], relation, ignoreNoAttribute), linePosition);
            case "→":
                return new Rename(interpretCondition(children[0], relation, ignoreNoAttribute),interpretCondition(children[1], relation, true), linePosition);    
            case "max":
                return new Max(interpretCondition(children[0], relation, ignoreNoAttribute), linePosition);
            case "sum":
                return new Sum(interpretCondition(children[0], relation, ignoreNoAttribute), linePosition);
            case "count":
                return new Count(interpretCondition(children[0], relation, ignoreNoAttribute), linePosition);
            case "min":
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
                        throw new InvalidParameters(children[0].getToken().getLinePosition(), value + " is not a valid attribute.");
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
    
    public OperationBase[] getTuples(TokenTree[] children) throws InvalidEvaluation, InvalidParameters
    {
        LinkedList<Tuple> tuples = new LinkedList<>();
        
        for(int i = 0; i < children.length; ++i)
        {
            if(children[i].getToken().getSymbol().equals("Tuple"))
            {
                LinkedList<String> values = new LinkedList<>();
                LinkedList<DataType> types = new LinkedList<>();
                
                for(int j = 0; j < children[i].getChildren().length; ++j)
                {
                    Token token = children[i].getChildren()[j].getToken();
                    
                    switch(token.getSymbol())
                    {
                        case "Number":
                            types.add(DataType.NUMBER);
                            break;
                        case "Boolean":
                            types.add(DataType.BOOLEAN);
                            break;
                        case "String":
                            types.add(DataType.STRING);
                            break;
                        case "":
                            types.add(DataType.UNKNOWN);
                            break;
                        case "Attribute":
                            throw new InvalidParameters(children[i].getChildren()[j].getToken().getLinePosition(), "Must not be attributes. Please use quotations.");
                        default:
                            throw new InvalidEvaluation(children[i].getChildren()[j].getToken().getLinePosition(), "Developer Error: tuple creation didn't have any type.");
                    }
                    
                    values.add(children[i].getChildren()[j].getChildren()[0].getToken().getSymbol());
                }
                
                 tuples.add(new Tuple(values.toArray(new String[0]), types.toArray(new DataType[0]), children[i].getToken().getLinePosition()));
            }
            
            else
            {
                throw new InvalidEvaluation(children[i].getToken().getLinePosition(), "Invalid tuple creation");
            }                
        }
        
        return tuples.toArray(new Tuple[0]);
    }
}
