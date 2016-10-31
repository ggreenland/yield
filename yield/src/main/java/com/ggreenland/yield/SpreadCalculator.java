package com.ggreenland.yield;

import com.ggreenland.yield.service.BondServiceException;


public interface SpreadCalculator {

    double calculate(Bond bond) throws BondServiceException;
}
