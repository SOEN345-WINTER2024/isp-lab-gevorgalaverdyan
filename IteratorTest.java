import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

public class IteratorTest {

    private List<String> list;
    private Iterator<String> itr;

    @Before
    public void setUp()
    {
        list = new ArrayList<String>();
        list.add ("cat");
        list.add ("dog");
        itr = list.iterator();
    }

    @Test
    public void testHasNext_BaseCase()
    {
        assertTrue(itr.hasNext());
    }

    @Test public void testHasNext_C1()
    {
        itr.next(); itr.next();
        assertFalse(itr.hasNext());
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testHasNext_C5()
    {
        list.add("elephant");
        itr.hasNext();
    }

    @Test public void testNext_BaseCase()
    {
        assertEquals ("cat", itr.next());
    }

    @Test(expected=NoSuchElementException.class)
    public void testNext_C1()
    {
        itr.next(); itr.next();
        itr.next();
    }

    @Test public void testNext_C2()
    {
        list = new ArrayList<String>();
        list.add (null);
        itr = list.iterator();
        assertNull (itr.next());
    }

    @Test(expected=ConcurrentModificationException.class)
    public void testNext_C5()
    {
        list.add ("elephant");
        itr.next();
    }

    @Test public void testRemove_BaseCase()
    {
        itr.next();
        itr.remove();
        assertFalse (list.contains ("cat"));
    }

    @Test public void testRemove_C1()
    {
        itr.next(); itr.next();
        itr.remove();
        assertFalse (list.contains ("dog"));
    }

    @Test public void testRemove_C2()
    {
        list.add (null);
        list.add ("elephant");
        itr = list.iterator();
        itr.next(); itr.next();
        itr.next();
        itr.remove();
        assertFalse (list.contains (null));
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testRemove_C3()
    {
        list = Collections.unmodifiableList (list);
        itr = list.iterator();
        itr.next();   // consume first element to make C4 true
        itr.remove();
    }

    @Test (expected=IllegalStateException.class)
    public void testRemove_C4()
    {
        itr.remove();
    }

    @Test (expected=ConcurrentModificationException.class)
    public void testRemove_C5()
    {
        itr.next();
        list.add ("elephant");
        itr.remove();
    }

}

