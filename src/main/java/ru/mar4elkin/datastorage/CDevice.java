package ru.mar4elkin.datastorage;

import ru.mar4elkin.devices.CBaseDevice;

import java.util.ArrayList;
import java.util.Map;

public class CDevice extends CBase {

    ArrayList<CBaseDevice> device;

    /**
     * В конструкторе создается url для подключение к базе данных и собственно подключение к ней
     * @param driver
     * название jdbc драйвера, например mysql
     * @param host
     * адресс базы данных, например 127.0.0.1
     * @param dbname
     * название базы данных
     * @param username
     * имя пользователя
     * @param password
     * пароль
     * @param devices
     * обьекты класса CBaseDevice
     */
    public CDevice(String driver, String host, String dbname, String username, String password, ArrayList<CBaseDevice> devices) {
        super(driver, host, dbname, username, password);
        this.device = devices;
    }

    public void initializeDevice() {
        this.device.forEach((dev) -> {
            System.out.println(this.sqlDatatypeMapping(dev));
        });
    }

    public void updateDevice() {}

    public void deleteDevice() {}

}
