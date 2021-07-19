package ru.mar4elkin.models;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class CMDatabaseWrapper {
    Sql2o db;
    Connection conn;

    public CMDatabaseWrapper(String username, String password){
        this.db = new Sql2o("jdbc:hsqldb:mem:testDB", username, password);
        this.conn = this.db.open();
    }

    public int query(String table, String values){
        this.conn.createQuery(table, values).executeUpdate();
        return this.conn.getResult();
    }
}
