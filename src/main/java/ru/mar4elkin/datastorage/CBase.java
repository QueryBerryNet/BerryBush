package ru.mar4elkin.datastorage;

import ru.mar4elkin.datastorage.enums.EJoin;
import ru.mar4elkin.datastorage.enums.ESqlAttrs;
import ru.mar4elkin.datastorage.enums.ESqlTypes;
import ru.mar4elkin.devices.CBaseDevice;

import java.sql.*;
import java.util.*;
import java.util.Date;
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

    public CBase insert(String tableName, ArrayList<String> cols, ArrayList<String> rows) {
        String query = "INSERT INTO " + tableName + " (";
        query = query.concat(cols.stream().collect(Collectors.joining(",")));
        query = query.concat(") VALUES (");
        query = query.concat(rows.stream().collect(Collectors.joining(",")));
        this.sqlString = query.concat(")");
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

    public CBase delete(String tableName) {
        this.sqlString = " DELETE FROM " + tableName;
        return this;
    }

    public CBase where(String col, String value) {
        this.sqlString += " WHERE " + col + " = " + value;
        return this;
    }

    public CBase join(EJoin joinType, String tableName, String col1, String col2) {
        this.sqlString += " " + joinType.toStr() + " " + tableName + " ON " + col1 + "=" + col2;
        return this;
    }

    public String sqlDatatypeMapping(CBaseDevice variable) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ")
                .append(variable.getDeviceType())
                .append(" (\n");
        for (Map.Entry<String, Object> entry : variable.getAll().entrySet()) {
            if (entry.getValue() instanceof String)
                sql.append(entry.getKey())
                        .append(" ")
                        .append(ESqlTypes.VARCHAR.toStr())
                        .append("(255)\n");
            else if (entry.getValue() instanceof Integer && entry.getKey().equals("device_id"))
                sql.append(entry.getKey())
                        .append(" ")
                        .append(ESqlTypes.INT.toStr())
                        .append(" ")
                        .append(ESqlAttrs.PK.toStr())
                        .append(" ")
                        .append(ESqlAttrs.AUTO_INCREMENT.toStr())
                        .append("\n");
            else if (entry.getValue() instanceof Integer)
                sql.append(entry.getKey())
                        .append(" ")
                        .append(ESqlTypes.INT.toStr())
                        .append("\n");
            else if (entry.getValue() instanceof Date)
                sql.append(entry.getKey())
                        .append(" ")
                        .append(ESqlTypes.DATE.toStr())
                        .append("\n");
            else if (entry.getValue() instanceof Enum)
                sql.append(entry.getKey())
                        .append(" ")
                        .append(ESqlTypes.ENUM.toStr())
                        .append(
                                Arrays.toString(entry.getValue().getClass().getEnumConstants())
                                .replace("[", "(")
                                .replace("]", ")")
                        )
                        .append("\n");
            else if (entry.getValue() instanceof Float)
                sql.append(entry.getKey())
                        .append(" ")
                        .append(ESqlTypes.BOOLEAN.toStr())
                        .append("\n");
        }
        sql.append(");");
        return sql.toString();
    }

}
