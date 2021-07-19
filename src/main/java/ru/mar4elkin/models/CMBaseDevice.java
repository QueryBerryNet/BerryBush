package ru.mar4elkin.models;

import org.sql2o.Query;
import ru.mar4elkin.devices.CBaseDevice;
import ru.mar4elkin.models.interfaces.ModelQuery;

import java.util.List;

public class CMBaseDevice extends CMDatabaseWrapper implements ModelQuery<CBaseDevice> {

    public CMBaseDevice(String username, String password) {
        super(username, password);
    }

    @Override
    public List<CBaseDevice> select(String queryText) {
        Query query = this.conn.createQuery(queryText);
        return query.executeAndFetch(CBaseDevice.class);
    }
}
