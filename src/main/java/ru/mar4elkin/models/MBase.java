package ru.mar4elkin.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MBase {

    Connection connection;
    String url;

    public MBase(String driver, String host, String dbname, String username, String password) {
        //TODO: добавить проверки
        this.url = "jdbc:" + driver + "://" + host + "/" + dbname + "?user=" + username + "&=" + password;
        this.InitConnection();
    }

    private void InitConnection() {
        try {
            this.connection = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public String insert(String tableName, ArrayList<String> cols, ArrayList<String> rows) {

        String query = "INSERT INTO " + tableName + " (";
        query = query.concat(cols.stream().collect(Collectors.joining(",")));
        query = query.concat(") VALUES (");
        query = query.concat(rows.stream().collect(Collectors.joining(",")));
        return query.concat(");");
    }

    public String select() {
        return "";
    }

    public String update() {
        return "";
    }

    public String delete() {
        return "";
    }

    public String create() {
        return "";
    }

    public String drop() {
        return "";
    }

    public String where(String s, String col, String value) {
        return s.concat(" WHERE " + col + " = " + value);
    }

}
