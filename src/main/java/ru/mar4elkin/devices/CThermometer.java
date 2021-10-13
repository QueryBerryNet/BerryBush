package ru.mar4elkin.devices;

import ru.mar4elkin.devices.enums.EAvailableDevices;
import ru.mar4elkin.devices.interfaces.IRunableGetter;

import java.util.Date;

public class CThermometer extends CBaseDevice {

    protected float temp;

    public CThermometer(String ip, String name, Date lastConnect, EAvailableDevices deviceType, float temp) {
        super(ip, name, lastConnect, deviceType);
        this.temp = temp;
    }

    @IRunableGetter
    public float getTemp() {
        return this.temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

}
