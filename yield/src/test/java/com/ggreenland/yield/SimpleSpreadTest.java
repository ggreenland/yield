package com.ggreenland.yield;

import com.ggreenland.yield.service.LocalBondDataService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleSpreadTest {

    private static Bond g1 = new Bond(Bond.Type.GOV, 5.2, 13.4);
    private static Bond g2 = new Bond(Bond.Type.GOV, 11.1, 3.2);
    private static Bond g3 = new Bond(Bond.Type.GOV, 21.5, 8.8);
    private static Bond c1 = new Bond(Bond.Type.CORP, 1.0, 3.2);
    private static Bond c2 = new Bond(Bond.Type.CORP, 10.1, 4.2);
    private static Bond c3 = new Bond(Bond.Type.CORP, 42.1, 7.1);

    private SpreadCalculator spreadCalculator;

    @Before
    public void init(){
        List<Bond> bonds = new ArrayList<Bond>(
                Arrays.asList(c1,c2,c3,g1,g2,g3)
        );
        spreadCalculator = new SimpleSpread(new LocalBondDataService(bonds));
    }

    @Test
    public void testSpreadLowEnd() throws Exception {
        double spread = spreadCalculator.calculate(c1);
        assertThat(spread, is(closeTo(c1.yield - g1.yield, 0.001)));
    }

    @Test
    public void testSpreadHighEnd() throws Exception {
        double spread = spreadCalculator.calculate(c3);
        assertThat(spread, is(closeTo(c3.yield - g3.yield, 0.001)));
    }

    @Test
    public void testSpreadMid() throws Exception {
        double spread = spreadCalculator.calculate(c2);
        assertThat(spread, is(closeTo(c2.yield - g2.yield, 0.001)));
    }
}