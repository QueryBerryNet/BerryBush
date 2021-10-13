package ru.mar4elkin;
import ru.mar4elkin.datastorage.CDevice;
import ru.mar4elkin.devices.CThermometer;
import ru.mar4elkin.devices.CWindow;
import ru.mar4elkin.devices.enums.EAvailableDevices;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        CDevice cDevice = new CDevice("mysql", "127.0.0.1", "berry_brush", "root", "");

        ArrayList<Class> iotDeviceList = new ArrayList<>();
        iotDeviceList.add(CThermometer.class);
        iotDeviceList.add(CWindow.class);

        cDevice.createProjectDirectory();
        cDevice.initDatabase(iotDeviceList);

        CThermometer temp = new CThermometer("127.0.0.1", "TestThermometer", null, EAvailableDevices.Thermometer, 37.6f);
        cDevice.addDevice(temp);


    }
}
