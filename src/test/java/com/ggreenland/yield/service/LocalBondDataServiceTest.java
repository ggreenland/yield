package com.ggreenland.yield.service;

import com.ggreenland.yield.Bond;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

public class LocalBondDataServiceTest {

    private static Bond g1 = new Bond(Bond.Type.GOV, 5.2, 13.4);
    private static Bond g2 = new Bond(Bond.Type.GOV, 11.1, 3.2);
    private static Bond g3 = new Bond(Bond.Type.GOV, 21.5, 8.8);
    private static Bond c1 = new Bond(Bond.Type.CORP, 1.0, 3.2);
    private static Bond c2 = new Bond(Bond.Type.CORP, 10.1, 4.2);
    private static Bond c3 = new Bond(Bond.Type.CORP, 12.1, 7.1);


    private BondDataService createLocalBondDataService(String asset) throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(asset);
        if (resource == null)
            throw new IOException("resource not found");
        File jsonFile = new File(resource.getFile());
        return LocalBondDataService.fromJson(jsonFile);
    }


    @Test
    public void testFromJson() throws Exception {
        BondDataService service = createLocalBondDataService("input.json");
        List<Bond> govBonds = service.find(Bond.Type.GOV);
        assertThat(govBonds.size(), is(3));
        List<Bond> corpBonds = service.find(Bond.Type.CORP);
        assertThat(corpBonds.size(), is(2));
    }

    @Test
    public void testEmpty() throws Exception {
        List<Bond> bonds = new ArrayList<Bond>();
        BondDataService service = new LocalBondDataService(bonds);
        List<Bond> govBonds = service.find(Bond.Type.GOV);
        assertThat(govBonds.size(), is(0));
        List<Bond> corpBonds = service.find(Bond.Type.CORP);
        assertThat(corpBonds.size(), is(0));
    }

    @Test
    public void testFindEqual() throws Exception {
        List<Bond> bonds = new ArrayList<Bond>(
                Arrays.asList(c1,c2,c3,g1,g2,g3)
        );
        BondDataService service = new LocalBondDataService(bonds);

        ListIterator<Bond> it = service.find(Bond.Type.GOV, 5.2);
        assertSame(it.next(), g1);
    }

    @Test
    public void testFindLessThanRange() throws Exception {
        List<Bond> bonds = new ArrayList<Bond>(
                Arrays.asList(c1,c2,c3,g1,g2,g3)
        );
        BondDataService service = new LocalBondDataService(bonds);

        ListIterator<Bond> it = service.find(Bond.Type.GOV, 5.0);
        assertThat(it.hasPrevious(), is(false));
    }

    @Test
    public void testFindGreaterThanRange() throws Exception {
        List<Bond> bonds = new ArrayList<Bond>(
                Arrays.asList(c1,c2,c3,g1,g2,g3)
        );
        BondDataService service = new LocalBondDataService(bonds);

        ListIterator<Bond> it = service.find(Bond.Type.GOV, 55.0);
        // advance iterator
        it.next();
        // beyond that should not exist
        assertThat(it.hasNext(), is(false));
    }

}