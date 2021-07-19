package ru.mar4elkin;

import ru.mar4elkin.models.CMDatabaseWrapper;

public class Main {

    public static void main(String[] args) {
        CMDatabaseWrapper dbw = new CMDatabaseWrapper("se", "");
        System.out.print(dbw);
    }
}
