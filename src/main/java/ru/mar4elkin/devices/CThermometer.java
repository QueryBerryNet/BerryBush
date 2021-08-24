package ru.mar4elkin.devices;

import ru.mar4elkin.devices.enums.EAvailableDevices;

import java.util.HashMap;
import java.util.Date;

public class CThermometer extends CBaseDevice {

    protected float temp;

    public CThermometer(int id, String ip, String name, Date lastConnect, EAvailableDevices deviceType, float temp) {
        super(id, ip, name, lastConnect, deviceType);
        this.temp = temp;
    }

    public float getTemp() {
        return this.temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    @Override
    public HashMap<String, Object> getAll() {
        HashMap<String, Object> fields = super.getAll();
        fields.put("temp", this.temp);
        return fields;
    }
}
