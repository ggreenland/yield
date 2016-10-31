package com.ggreenland.yield.service;

import com.ggreenland.yield.Bond;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BondSearchTest {

    private static Bond g1 = new Bond(Bond.Type.GOV, 5.2, 13.4);
    private static Bond g2 = new Bond(Bond.Type.GOV, 11.1, 3.2);
    private static Bond g3 = new Bond(Bond.Type.GOV, 21.5, 8.8);

    private static List<Bond> bonds = new ArrayList<Bond>(
            Arrays.asList(g1,g2,g3)
    );

    @Before
    public void init(){
        Collections.sort(bonds);
    }

    @Test
    public void testFindClosetBondIndex1() throws Exception {

        // Find bond where search term is equal
        assertThat(BondSearch.findClosetBondIndex(bonds, 5.2), is(bonds.indexOf(g1)));
    }

    @Test
    public void testFindClosetBondIndex2() throws Exception {

         // Find bond where search term is less than all
        assertThat(BondSearch.findClosetBondIndex(bonds, 5.1), is(bonds.indexOf(g1)));
    }

    @Test
    public void testFindClosetBondIndex3() throws Exception {

        // Find bond where search term is greater than all
        assertThat(BondSearch.findClosetBondIndex(bonds, 35.1), is(bonds.indexOf(g3)));
    }

    @Test
    public void testFindClosetBondIndex4() throws Exception {

        // Find bond where search term is close to g2 but less
        assertThat(BondSearch.findClosetBondIndex(bonds, 10.4), is(bonds.indexOf(g2)));
    }

    @Test
    public void testFindClosetBondIndex5() throws Exception {

        // Find bond where search term is close to g2 but less
        assertThat(BondSearch.findClosetBondIndex(bonds, 10.4), is(bonds.indexOf(g2)));
    }
}