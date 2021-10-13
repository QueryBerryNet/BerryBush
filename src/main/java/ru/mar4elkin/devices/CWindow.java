package ru.mar4elkin.devices;

import ru.mar4elkin.devices.enums.EAvailableDevices;
import ru.mar4elkin.devices.enums.EWindowPosition;
import ru.mar4elkin.devices.interfaces.IRunableGetter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CWindow extends CBaseDevice {

    protected EWindowPosition position;

    public CWindow(String ip, String name, Date lastConnect, EAvailableDevices deviceType, EWindowPosition position) {
        super(ip, name, lastConnect, deviceType);
        this.position = position;
    }

    @IRunableGetter
    public EWindowPosition getPosition() {
        return this.position;
    }

    public void setPosition(EWindowPosition position) {
        this.position = position;
    }

}
