package ru.mar4elkin.devices;

import ru.mar4elkin.devices.enums.EAvailableDevices;

import java.util.Date;

public class CBaseDevice {

    protected int deviceId;
    protected String ipAddress;
    protected String name;
    protected Date lastConnect;
    protected EAvailableDevices deviceType;

    public CBaseDevice(int id, String ip, String name, Date lastConnect, EAvailableDevices deviceType) {
        this.deviceId = id;
        this.ipAddress = ip;
        this.name = name;
        this.lastConnect = lastConnect;
        this.deviceType = deviceType;
    }
}
