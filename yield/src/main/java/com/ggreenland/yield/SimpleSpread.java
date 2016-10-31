package com.ggreenland.yield;

import com.ggreenland.yield.service.BondDataService;
import com.ggreenland.yield.service.BondServiceException;

import java.util.ListIterator;


public class SimpleSpread implements SpreadCalculator {

    private final BondDataService bondData;

    public SimpleSpread(BondDataService bondData){
        this.bondData = bondData;
    }

    @Override
    public double calculate(Bond bond) throws BondServiceException{

        ListIterator<Bond> it = bondData.find(Bond.Type.GOV, bond.term);

        return bond.yield - it.next().yield;
    }
}
