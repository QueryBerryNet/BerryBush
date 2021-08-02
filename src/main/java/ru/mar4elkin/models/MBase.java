package ru.mar4elkin.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class MBase {

    Connection connection;
    String url;
    String sqlString;

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

    public String getRawSql() {
        return this.sqlString;
    }

    public ResultSet executeQuery() {
        try {
            Statement statement = this.connection.createStatement();
            return statement.executeQuery(this.sqlString += ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int executeUpdate() {
        try {
            Statement statement = this.connection.createStatement();
            return statement.executeUpdate(this.sqlString += ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public MBase insert(String tableName, ArrayList<String> cols, ArrayList<String> rows) {
        String query = "INSERT INTO " + tableName + " (";
        query = query.concat(cols.stream().collect(Collectors.joining(",")));
        query = query.concat(") VALUES (");
        query = query.concat(rows.stream().collect(Collectors.joining(",")));
        this.sqlString = query.concat(")");
        return this;
    }

    public MBase select(String tableName, ArrayList<String> cols) {
        String query = "SELECT ";
        query = query.concat(cols.stream().collect(Collectors.joining(",")));
        query += " FROM " + tableName;
        this.sqlString = query;
        return this;
    }

    public MBase update(String tableName, HashMap<String, String> colValue) {
        String query = "UPDATE " + tableName + " SET ";

        int i = 0;
        for (Map.Entry<String, String> entry : colValue.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (i++ == colValue.size() -1) {
                query += key + " = " + value;
            } else {
                query += key + " = " + value + ", ";
            }
        }
        this.sqlString = query;
        return this;
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

    public MBase where(String col, String value) {
        this.sqlString += " WHERE " + col + " = " + value;
        return this;
    }

}
