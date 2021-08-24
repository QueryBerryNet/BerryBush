package ru.mar4elkin.devices;

import ru.mar4elkin.devices.enums.EAvailableDevices;
import ru.mar4elkin.devices.enums.EWindowPosition;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CWindow extends CBaseDevice {

    protected EWindowPosition position;

    public CWindow(int id, String ip, String name, Date lastConnect, EAvailableDevices deviceType, EWindowPosition position) {
        super(id, ip, name, lastConnect, deviceType);
        this.position = position;
    }

    public EWindowPosition getPosition() {
        return this.position;
    }

    public void setPosition(EWindowPosition position) {
        this.position = position;
    }

    @Override
    public HashMap<String, Object> getAll() {
        HashMap<String, Object> fields = super.getAll();
        fields.put("position", this.position);
        return fields;
    }
}
