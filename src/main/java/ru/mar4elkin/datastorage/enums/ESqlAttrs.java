package ru.mar4elkin.datastorage.enums;

public enum ESqlAttrs {
    AUTO_INCREMENT("AUTO_INCREMENT"),
    UNIQUE("UNIQUE"),
    NULL("NULL"),
    PK("PRIMARY KEY"),
    NOT_NULL("NOT NULL"),
    LIMIT("LIMIT"),
    DESC("DESC"),
    ORDER_BY("ORDER BY");

    private final String estr;

    ESqlAttrs(String estr) {
        this.estr = estr;
    }

    public String toStr() {
        return estr;
    }
}
