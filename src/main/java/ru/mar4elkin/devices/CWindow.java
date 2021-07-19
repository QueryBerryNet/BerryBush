package ru.mar4elkin.devices;

import ru.mar4elkin.devices.enums.EAvailableDevices;
import ru.mar4elkin.devices.enums.EWindowPosition;

import java.util.Date;

public class CWindow extends CBaseDevice {

    protected EWindowPosition position;

    public CWindow(int id, String ip, String name, Date lastConnect, EAvailableDevices deviceType, EWindowPosition position) {
        super(id, ip, name, lastConnect, deviceType);
        this.position = position;
    }
}
