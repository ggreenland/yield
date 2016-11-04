package com.ggreenland.yield.service;

import com.ggreenland.yield.Bond;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.ListIterator;

public class BondIterator implements ListIterator<Bond> {

    private final List<Bond> bonds;
    private int curPos;

    public BondIterator(List<Bond> bonds, double term){
        this.bonds = bonds;
        this.curPos = BondSearch.findClosetBondIndex(bonds, term);
    }

    @Override
    public boolean hasNext() {
        return this.curPos >= 0 && this.curPos < bonds.size();
    }

    @Override
    public Bond next() {
        Bond b = bonds.get(curPos);
        curPos = curPos + 1;
        return b;
    }

    @Override
    public boolean hasPrevious() {
        return this.curPos > 0 && this.curPos <= bonds.size();
    }

    @Override
    public Bond previous() {
        Bond b = bonds.get(curPos - 1);
        curPos = curPos - 1;
        return b;
    }

    @Override
    public int nextIndex() {
        if (hasNext())
            return curPos;
        return bonds.size();
    }

    @Override
    public int previousIndex() {
        if (hasPrevious())
            return curPos - 1;
        return -1;
    }

    @Override
    public void remove() {
        // Not implementing in this sample as we assume the bond list is fixed
        throw new NotImplementedException();
    }

    @Override
    public void set(Bond bond) {
        // Not implementing in this sample as we assume the bond list is fixed
        throw new NotImplementedException();
    }

    @Override
    public void add(Bond bond) {
        // Not implementing in this sample as we assume the bond list is fixed
        throw new NotImplementedException();
    }
}
