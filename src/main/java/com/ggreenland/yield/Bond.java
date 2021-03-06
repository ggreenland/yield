package com.ggreenland.yield;


public class Bond implements Comparable<Bond> {

    public final Type type;
    public final Double term;
    public final Double yield;
    public final String name;

    public Bond(Type type, Double term, Double yield, String name) {
        this.type = type;
        this.term = term;
        this.yield = yield;
        this.name = name;
    }

    public Bond(Type type, Double term, Double yield) {
        this(type,term,yield,"B" + Double.toString(yield));
    }


    @Override
    public int compareTo(Bond bond) {
        if (this.term < bond.term)
            return -1;
        if (this.term > bond.term)
            return 1;
        return 0;
    }

    @Override
    public String toString() {
        return "Bond{" +
                "type=" + type +
                ", term=" + term +
                ", yield=" + yield +
                '}';
    }
    public enum Type {
        CORP,
        GOV
    }

}

