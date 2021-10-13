package ru.mar4elkin.devices;

import ru.mar4elkin.datastorage.interfaces.ISqlConstants;
import ru.mar4elkin.datastorage.enums.ESqlAttrs;
import ru.mar4elkin.devices.enums.EAvailableDevices;
import ru.mar4elkin.devices.interfaces.IRunableGetter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

public class CBaseDevice {

    @ISqlConstants(constants = {
        ESqlAttrs.PK,
        ESqlAttrs.AUTO_INCREMENT,
        ESqlAttrs.NOT_NULL
    })
    protected int id;
    protected String ipAddress;
    protected String name;
    protected Date lastConnect;
    protected EAvailableDevices deviceType;

    public CBaseDevice(String ip, String name, Date lastConnect, EAvailableDevices deviceType) {
        this.ipAddress = ip;
        this.name = name;
        this.lastConnect = lastConnect;
        this.deviceType = deviceType;
    }

    @IRunableGetter
    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @IRunableGetter
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @IRunableGetter
    public Date getLastConnect() {
        return this.lastConnect;
    }

    public void setLastConnect(Date lastConnect) {
        this.lastConnect = lastConnect;
    }

    @IRunableGetter
    public EAvailableDevices getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(EAvailableDevices deviceType) {
        this.deviceType = deviceType;
    }

}
