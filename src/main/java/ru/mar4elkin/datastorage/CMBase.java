package ru.mar4elkin.datastorage;

import ru.mar4elkin.datastorage.enums.EJoin;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * CMBase класс для более удобной работой с базой данных
 */
public class CMBase {

    Connection connection;
    String url;
    String sqlString;

    /**
     * В конструкторе создается url для подключение к базе данных и собственно подключение к ней
     * @param driver
     * название jdbc драйвера, например mysql
     * @param host
     * адресс базы данных, например 127.0.0.1
     * @param dbname
     * название базы данных
     * @param username
     * имя пользователя
     * @param password
     * пароль
     */
    public CMBase(String driver, String host, String dbname, String username, String password) {
        //TODO: добавить проверки
        this.url = "jdbc:" + driver + "://" + host + "/" + dbname + "?user=" + username + "&=" + password;
        //this.InitConnection();
    }

    /**
     * Метод подлючается к бд, если это возможно
     */
    private void InitConnection() {
        try {
            this.connection = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Метод нужен для проверки sql строки
     * @return this.sqlString
     * Возращяет sql запрос в виде строки
     */
    public String getRawSql() {
        return this.sqlString;
    }

    /**
     * Метод возращяет jdbc обьект в случае успешного запроса к бд
     * @return null | ResultSet
     */
    public ResultSet executeQuery() {
        try {
            Statement statement = this.connection.createStatement();
            return statement.executeQuery(this.sqlString += ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Аналог метода executeQuery
     * @return int
     * ол-во затронутых строк
     */
    public int executeUpdate() {
        try {
            Statement statement = this.connection.createStatement();
            return statement.executeUpdate(this.sqlString += ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Имплементация sql-insert
     * @param tableName
     * название таблицы
     * @param cols
     * стобцы
     * @param rows
     * ряды
     * @return this
     * вернет обьект mbase для вызова методов после, например insert(...).join(...).where(...)
     */
    public CMBase insert(String tableName, ArrayList<String> cols, ArrayList<String> rows) {
        String query = "INSERT INTO " + tableName + " (";
        query = query.concat(cols.stream().collect(Collectors.joining(",")));
        query = query.concat(") VALUES (");
        query = query.concat(rows.stream().collect(Collectors.joining(",")));
        this.sqlString = query.concat(")");
        return this;
    }

    /**
     * Имплементация sql-select
     * @param tableName
     * название таблицы
     * @param cols
     * стобцы
     * @return this
     * вернет обьект mbase для вызова методов после, например insert(...).join(...).where(...)
     */
    public CMBase select(String tableName, ArrayList<String> cols) {
        String query = "SELECT ";
        query = query.concat(cols.stream().collect(Collectors.joining(",")));
        query += " FROM " + tableName;
        this.sqlString = query;
        return this;
    }

    /**
     * Имплементация sql-update
     * @param tableName
     * название таблицы
     * @param colValue
     * словарь стобцы-значение
     * @return this
     * вернет обьект mbase для вызова методов после, например insert(...).join(...).where(...)
     */
    public CMBase update(String tableName, HashMap<String, String> colValue) {
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

    /**
     * Имплементация sql-delete
     * @param tableName
     * название таблицы
     * @return this
     * вернет обьект mbase для вызова методов после, например insert(...).join(...).where(...)
     */
    public CMBase delete(String tableName) {
        this.sqlString = " DELETE FROM " + tableName;
        return this;
    }

    /**
     * Имплементация sql-where
     * @param col
     * столбец
     * @param value
     * значение
     * @return this
     * вернет обьект mbase для вызова методов после, например insert(...).join(...).where(...)
     */
    public CMBase where(String col, String value) {
        this.sqlString += " WHERE " + col + " = " + value;
        return this;
    }

    /**
     *
     * @param joinType
     * тип join'a full, left, ...
     * @param tableName
     * название таблицы
     * @param col1
     * первый столбец
     * @param col2
     * второй столбец
     * @return this
     * вернет обьект mbase для вызова методов после, например insert(...).join(...).where(...)
     */
    public CMBase join(EJoin joinType, String tableName, String col1, String col2) {
        this.sqlString += " " + joinType.toStr() + " " + tableName + " ON " + col1 + "=" + col2;
        return this;
    }

}
