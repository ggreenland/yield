package com.ggreenland.yield.service;

import com.ggreenland.yield.Bond;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * A local implementation of BondDataService. This local service is used for
 * local CLI operations and testing.
 *
 * Even so we have chosen to implement it efficiently using a binary search
 * on sorted bond lists for the requirements currently defined
 */
public class LocalBondDataService implements BondDataService {

    private static final Gson gson = new Gson();
    private final List<Bond> govBonds = new ArrayList<Bond>();
    private final List<Bond> corpBonds = new ArrayList<Bond>();

    public LocalBondDataService(List<Bond> bonds){
        for (Bond bond : bonds){
            if (bond.type == Bond.Type.CORP)
                corpBonds.add(bond);
            else
                govBonds.add(bond);
        }
        Collections.sort(corpBonds);
        Collections.sort(govBonds);
    }

    static public BondDataService fromJson(File filepath) throws FileNotFoundException {
        Type listType = new TypeToken<ArrayList<Bond>>(){}.getType();
        List<Bond> bonds = gson.fromJson(new FileReader(filepath), listType);
        return new LocalBondDataService(bonds);
    }

    private List<Bond> getListByType(Bond.Type type){
        if (type == Bond.Type.CORP)
            return corpBonds;
        return govBonds;
    }

    @Override
    public List<Bond> find(Bond.Type type) {
        return Collections.unmodifiableList(getListByType(type));
    }

    @Override
    public ListIterator<Bond> find(Bond.Type type, double term) throws BondServiceException {
        return new BondIterator(getListByType(type),term);
    }
}
