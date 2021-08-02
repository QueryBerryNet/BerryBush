package ru.mar4elkin;

import ru.mar4elkin.models.MBase;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        MBase mbase = new MBase("mysql", "127.0.0.1", "laravel", "root", "");

        ArrayList<String> cols = new ArrayList<String>();
        ArrayList<String> rows = new ArrayList<String>();

        cols.add("col1");
        cols.add("col2");
        rows.add("row1");
        rows.add("row2");

        String test = mbase.insert("testTable", cols, rows).where("col1", "row1").getRawSql();
        System.out.println(test);

    }
}
