package ru.mar4elkin;

import ru.mar4elkin.datastorage.CBase;
import ru.mar4elkin.datastorage.CDevice;
import ru.mar4elkin.devices.CBaseDevice;
import ru.mar4elkin.devices.CThermometer;
import ru.mar4elkin.devices.CWindow;
import ru.mar4elkin.devices.enums.EAvailableDevices;
import ru.mar4elkin.devices.enums.EWindowPosition;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        CThermometer cThermometer = new CThermometer(
                1, "127.0.0.1", "test_name", new Date(2021, 2, 3),
                EAvailableDevices.Thermometer, 37f
        );

        CWindow cWindow = new CWindow(
                1, "127.0.0.1", "test_name", new Date(2021, 2, 3),
                EAvailableDevices.Window, EWindowPosition.Closed
        );

        ArrayList<CBaseDevice> iotDeviceList = new ArrayList<CBaseDevice>();
        iotDeviceList.add(cThermometer);
        iotDeviceList.add(cWindow);

        CDevice cDevice = new CDevice("mysql", "127.0.0.1", "laravel", "root", "", iotDeviceList);
        cDevice.createProjectDirectory();
        cDevice.initializeDevice();


    }
}
