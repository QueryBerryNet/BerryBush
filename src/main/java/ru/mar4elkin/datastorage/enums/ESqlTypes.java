package ru.mar4elkin.datastorage.enums;

public enum ESqlTypes {
    VARCHAR("VARCHAR(256)"),
    INT("INT"),
    BOOLEAN("BOOL"),
    DATE("DATE"),
    FLOAT("FLOAT"),
    ENUM("ENUM");

    private final String estr;

    ESqlTypes(String estr) {
        this.estr = estr;
    }

    public String toStr() {
        return estr;
    }
}
