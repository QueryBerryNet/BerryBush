package ru.mar4elkin.datastorage;

import ru.mar4elkin.datastorage.enums.EJoin;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class CBase {

    Connection connection;
    String url;
    String sqlString;

    public CBase(String driver, String host, String dbname, String username, String password) {
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

    public ResultSet executeQuery() {
        try {
            Statement statement = this.connection.createStatement();
            return statement.executeQuery(this.sqlString += ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executeUpdate() {
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(this.sqlString += ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getRawSql() {
        return this.sqlString;
    }

    public CBase insert(String tableName, ArrayList<String> cols, ArrayList<String> rows) {
        StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " (");

        query = new StringBuilder(query.toString().concat(
                String.join(",", cols)
        ));

        query = new StringBuilder(query.toString().concat(") VALUES ("));

        for (String r: rows) {
            query.append("'").append(r).append("',");
        }

        query.setLength(query.length() - 1);

        this.sqlString = query.toString().concat(")");
        return this;
    }

    public CBase select(String tableName, ArrayList<String> cols) {
        String query = "SELECT ";
        query = query.concat(cols.stream().collect(Collectors.joining(",")));
        query += " FROM " + tableName;
        this.sqlString = query;
        return this;
    }

    public CBase update(String tableName, HashMap<String, String> colValue) {
        StringBuilder query = new StringBuilder("UPDATE " + tableName + " SET ");

        int i = 0;
        for (Map.Entry<String, String> entry : colValue.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (i++ == colValue.size() -1) {
                query
                    .append(key)
                    .append(" = ")
                    .append("'")
                    .append(value)
                    .append("'");
            } else {
                query
                    .append(key)
                    .append(" = ")
                    .append("'")
                    .append(value)
                    .append("'")
                    .append(", ");
            }
        }
        this.sqlString = query.toString();
        return this;
    }

    public CBase delete(String tableName) {
        this.sqlString = " DELETE FROM " + tableName;
        return this;
    }

    public CBase where(String col, String value) {
        this.sqlString += " WHERE " + col + " = " + "'" + value + "'";
        return this;
    }

    public CBase join(EJoin joinType, String tableName, String col1, String col2) {
        this.sqlString += " " + joinType.toStr() + " " + tableName + " ON " + col1 + "=" + col2;
        return this;
    }

}
