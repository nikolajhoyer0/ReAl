package real.Objects;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import real.Objects.ConditionOperations.Atomic.StringLiteral;
import real.Objects.Exceptions.*;
import real.Objects.RAOperations.NaturalJoin;
import real.Objects.Services.DataManager;
import static org.junit.Assert.*;

public class NaturalJoinTest {

    public NaturalJoinTest()
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
    public void testNJ()
    {
        try {
            Kernel.GetService(DataManager.class).LoadDataset("C:\\Users\\Sinni\\Documents\\battels.csv", "battles");
            Kernel.GetService(DataManager.class).LoadDataset("C:\\Users\\Sinni\\Documents\\ships.csv", "ships");
            NaturalJoin join = new NaturalJoin(null, null);
        }
        catch (InvalidDataset | DatasetDuplicate ex)
        {
            assertTrue(false);
        }
    }
}
