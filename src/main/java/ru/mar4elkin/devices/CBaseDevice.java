package ru.mar4elkin.devices;

import ru.mar4elkin.devices.enums.EAvailableDevices;

import java.util.HashMap;
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

    public int getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastConnect() {
        return this.lastConnect;
    }

    public void setLastConnect(Date lastConnect) {
        this.lastConnect = lastConnect;
    }

    public EAvailableDevices getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(EAvailableDevices deviceType) {
        this.deviceType = deviceType;
    }

    public HashMap<String, Object> getAll() {
        HashMap<String, Object> fields = new HashMap<String, Object>();
        fields.put("device_id", this.deviceId);
        fields.put("ip_address", this.ipAddress);
        fields.put("name", this.name);
        fields.put("last_connect", this.lastConnect);
        fields.put("device_type", this.deviceType);
        return fields;
    }

}
