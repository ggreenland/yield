package com.ggreenland.yield.service;

import com.ggreenland.yield.Bond;

import java.util.List;

public class BondSearch {

    /**
     * Find index of closest bond in a sorted bond list
     * Assumes bonds are sorted in increasing term length so that
     * a binary search can be performed on the list
     *
     * @param bonds Sorted List of bonds to search
     * @param term Term to compare bonds to
     * @return Index of closest bond
     */
    static int findClosetBondIndex(List<Bond> bonds, double term){

        int st = 0;
        int en = bonds.size() - 1;
        double curDiff;
        while ((en - st) > 1){
            int mid = st + (en - st) / 2;
            curDiff = term - bonds.get(mid).term;
            if (curDiff < 0){
                en = mid;
            } else {
                st = mid;
            }
        }
        double sd = term - bonds.get(st).term;
        double ed = term - bonds.get(en).term;
        if (sd <= 0 && ed <= 0)
            return st;
        if (sd > 0 && ed > 0)
            return en;
        return Math.abs(sd) <= Math.abs(ed) ? st : en;
    }
}
