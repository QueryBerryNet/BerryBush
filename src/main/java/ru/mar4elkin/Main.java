package ru.mar4elkin;
import ru.mar4elkin.datastorage.CDevice;
import ru.mar4elkin.devices.CThermometer;
import ru.mar4elkin.devices.CWindow;
import ru.mar4elkin.devices.enums.EAvailableDevices;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, SQLException {

        CDevice cDevice = new CDevice("mysql", "127.0.0.1", "berry_brush", "root", "");

//        ArrayList<Class> iotDeviceList = new ArrayList<>();
//        iotDeviceList.add(CThermometer.class);
//        iotDeviceList.add(CWindow.class);
//
//        cDevice.createProjectDirectory();
//        cDevice.initDatabase(iotDeviceList);

       CThermometer temp = new CThermometer("127.0.0.1", "TestThermometer2", null, EAvailableDevices.Thermometer, 37.6f);
       cDevice.add(temp);
//        cDevice.get(CThermometer.class);


    }
}
