import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SolutionTest{
    ArrayList<Integer> expected = new ArrayList<>();

    @Before
    public void init() {
        expected.clear();
    }

    @Test
    public void first() throws Exception {
        expected.add(1);
        expected.add(1);
        expected.add(2);
        Assert.assertEquals(expected,Solution.Fibo(3));
        expected.add(3);
        Assert.assertEquals(expected,Solution.Fibo(4));
    }

    @Test
    public void some() throws Exception {
        expected.add(1);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(5);
        Assert.assertEquals(expected,Solution.Fibo(5));
    }


}