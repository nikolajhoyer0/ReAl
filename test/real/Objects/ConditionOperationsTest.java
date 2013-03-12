package real.Objects;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import real.Objects.ConditionOperations.Add;
import real.Objects.ConditionOperations.Atomic.BooleanLiteral;
import real.Objects.ConditionOperations.Atomic.NumberLiteral;
import real.Objects.ConditionOperations.Atomic.StringLiteral;
import real.Objects.ConditionOperations.BooleanOperations.Equal;
import real.Objects.Exceptions.WrongType;

public class ConditionOperationsTest
{
    
    public ConditionOperationsTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    @Test
    public void testAdd()
    {
        NumberLiteral n1 = new NumberLiteral(4);
        NumberLiteral n2 = new NumberLiteral(7);
        StringLiteral str1 = new StringLiteral("hello");
        StringLiteral str2 = new StringLiteral("okay");
        
        try
        {               
            Add a1 = new Add(n1, n2);
            assertEquals(11, (int)a1.evaluate(null));
            assertFalse((boolean)new Equal(str1, str2).evaluate(null));
            assertFalse((boolean)new Equal(n1, n2).evaluate(null));
            assertTrue((boolean)new Equal(str1, str1).evaluate(null));
            assertTrue((boolean)new Equal(n1, n1).evaluate(null));
            assertTrue((boolean)new Equal(new BooleanLiteral(true), new BooleanLiteral(true)).evaluate(null));
            assertFalse((boolean)new Equal(new BooleanLiteral(true), new BooleanLiteral(false)).evaluate(null));
            assertTrue((boolean)new Equal(a1, a1).evaluate(null));
        }
        
        catch (WrongType ex)
        {
            assertTrue(false);
        }

        
        StringLiteral s1 = new StringLiteral("hello");
        
        try
        {
            Add a2 = new Add(n1, s1);
        
        }
        catch (WrongType ex)
        {
            assertTrue(true);
        }
         
        try
        {
            Add a3 = new Add(new NumberLiteral(2), new NumberLiteral(3));
            Add a4 = new Add(new StringLiteral("hello"), a3);
        }
        catch (WrongType ex)
        {
            assertTrue(true);
            System.out.println(ex.getMessage());
        }       
    }
}
