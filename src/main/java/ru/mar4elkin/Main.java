package ru.mar4elkin;

import ru.mar4elkin.models.MBase;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        MBase mbase = new MBase("mysql", "127.0.0.1", "laravel", "root", "");

        ArrayList<String> cols = new ArrayList<String>();
        ArrayList<String> rows = new ArrayList<String>();
        ArrayList<String> colsSelect = new ArrayList<String>();

        cols.add("col1");
        cols.add("col2");
        rows.add("row1");
        rows.add("row2");
        colsSelect.add("*");

        String test = mbase.insert("testTable", cols, rows).where("col1", "row1").getRawSql();
        System.out.println(test);

        String test1 = mbase.select("blabla", cols).getRawSql();
        System.out.println(test1);

        HashMap<String, String> colvals = new HashMap<String, String>();
        colvals.put("col1", "some_value");
        colvals.put("col2", "some_value2");

        String test2 = mbase.update("blabla", colvals).getRawSql();
        System.out.println(test2);

    }
}
