package com.ggreenland.yield.service;

import com.ggreenland.yield.Bond;

import java.util.List;
import java.util.ListIterator;

/**
 * An interface for accessing bond data.
 *
 *
 * How this interface is expanded and implemented would depend on
 * - Additional requirements for this service
 * - Scalability requirements
 * - Availability requirements
 *
 * In a real implementation we would likely back this with a relational database
 * service
 */
public interface BondDataService {

    /**
     * Find all bonds of a particular type
     * @param type Type of bond to find
     * @return A list of bonds sorted by increasing term
     */
    List<Bond> find(Bond.Type type);

    /**
     * Return a list iterator of sorted bonds (by increasing term) of a
     * particular type and with the next element pointing to the bond
     * with the closest term
     *
     * @param type Type of bond to find
     * @param term Term to search for
     * @return An iterator of bonds
     * @throws BondServiceException
     */
    ListIterator<Bond> find(Bond.Type type, double term) throws BondServiceException;

}
