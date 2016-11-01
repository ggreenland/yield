package com.ggreenland.yield;

import com.ggreenland.yield.service.BondServiceException;


/**
 * An interface for calculating the yield spread of a particular bond
 */
public interface SpreadCalculator {

    double calculate(Bond bond) throws BondServiceException;
}
