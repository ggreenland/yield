package com.ggreenland.yield;

import com.ggreenland.yield.service.BondDataService;
import com.ggreenland.yield.service.BondServiceException;

import java.util.ListIterator;


/**
 * Calculate the yield spread on a bond agasint the linearly interpolated
 * bond curve
 */
public class LinearInterpolatedSpread implements SpreadCalculator {

    private final BondDataService bondData;

    public LinearInterpolatedSpread(BondDataService bondData){
        this.bondData = bondData;
    }

    private double interpolateYield(Bond b1, Bond b2, Bond target){

        double m = (b2.yield - b1.yield)/(b2.term - b1.term);
        return m*(target.term - b1.term) + b1.yield;
    }

    @Override
    public double calculate(Bond bond) throws BondServiceException {

        ListIterator<Bond> it = bondData.find(Bond.Type.GOV, bond.term);

        Bond mb = it.next();
        Bond b1;
        Bond b2;
        if (mb.term >= bond.term){
            it.previous();  // reset position
            if (!it.hasPrevious())
                throw new BondServiceException("Insufficient Bond Data");
            b2 = mb;
            b1 = it.previous();
        } else {
            if (!it.hasNext())
                throw new BondServiceException("Insufficient Bond Data");
            b2 = it.next();
            b1 = mb;
        }

        double yield = interpolateYield(b1, b2, bond);
        return bond.yield - yield;
    }
}
