package ru.mar4elkin.datastorage.enums;

public enum EJoin {
    INNER("INNER JOIN"),
    LEFT("LEFT JOIN"),
    RIGHT("RIGHT JOIN"),
    FULL("FULL JOIN");

    private final String estr;

    EJoin(String estr) {
        this.estr = estr;
    }

    public String toStr() {
        return estr;
    }
}
