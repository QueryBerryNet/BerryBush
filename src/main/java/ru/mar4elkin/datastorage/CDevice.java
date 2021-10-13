package ru.mar4elkin.datastorage;

import org.apache.spark.sql.execution.columnar.BOOLEAN;
import ru.mar4elkin.datastorage.enums.ESqlAttrs;
import ru.mar4elkin.datastorage.enums.ESqlTypes;
import ru.mar4elkin.datastorage.interfaces.ISqlConstants;
import ru.mar4elkin.devices.CBaseDevice;
import ru.mar4elkin.devices.interfaces.IRunableGetter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class CDevice extends CBase {

    ArrayList<CBaseDevice> device;
    String dir;

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
     */
    public CDevice(String driver, String host, String dbname, String username, String password) {
        super(driver, host, dbname, username, password);
        this.dir = System.getProperty("user.home") + "\\.berrybrush";
    }

    //TODO: я пока не придумал что с этим делать нужно доделать
    public void createProjectDirectory() {
        try {
            Path pathDir = Paths.get(this.dir);
            if (!Files.exists(pathDir)) {
                Files.createDirectories(Path.of(this.dir));
                System.out.println("Directory is created! path: " + this.dir);
                File dotEnvFile = new File(this.dir + "\\.env");
                if (dotEnvFile.createNewFile()) {
                    System.out.println("File created: " + dotEnvFile.getName());
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());
        }
    }

    public void initDatabase(ArrayList<Class> devices) {
        devices.forEach((dev) -> {
            this.createTableByStaticClass(dev).executeUpdate();
        });
    }

    public void addDevice(CBaseDevice obj) throws InvocationTargetException, IllegalAccessException {
        this.InsertByObject(obj);
    }

    public void updateDevice() {}

    public void deleteDevice() {}

    //TODO: Перенести все что связанно с парсингом в sql строку в отдельный класс CJavaToSqlParser
    private String parseToSqlStringNameAndType(String name, Object object, ISqlConstants annotation, String sql) {
        if(object == String.class) {
            sql += name + " " + ESqlTypes.VARCHAR.toStr() + ",\n";
        }

        if(object == BOOLEAN.class) {
            sql += name + " " + ESqlTypes.BOOLEAN.toStr() + ",\n";
        }

        if(object == int.class) {
            if (annotation != null) {
                sql += name + " " + ESqlTypes.INT.toStr();
                for (ESqlAttrs a: annotation.constants()) {
                    sql += " " + a.toStr();
                }
                sql += ",\n";
            } else {
                sql += name + " " + ESqlTypes.INT.toStr() + ",\n";
            }
        }

        if(object == Date.class) {
            sql += name + " " + ESqlTypes.DATE.toStr() + ",\n";
        }

        if(object instanceof Class && ((Class<?>) object).isEnum()) {
            sql += name + " " + ESqlTypes.ENUM.toStr() + " (";
            Object[] possibleValues = ((Class<?>) object).getEnumConstants();
            for (Object p: possibleValues) {
                sql += "'" + p + "',";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += "),\n";
        }
        return sql;
    }

    private CBase createTableByStaticClass(Class variable) {
        String sql = "CREATE TABLE " + variable.getSimpleName() + " (\n";
        if (variable.getSuperclass() != null) {
            Field[] superfields = variable.getSuperclass().getDeclaredFields();
            for (Field field : superfields) {
                ISqlConstants fieldAnnotation = field.getAnnotation(ISqlConstants.class);
                String fieldName = field.getName().toLowerCase();
                Object fieldType = field.getGenericType();
                sql = this.parseToSqlStringNameAndType(fieldName, fieldType, fieldAnnotation, sql);
            }
        }

        Field[] fields = variable.getDeclaredFields();
        for (Field field : fields) {
            ISqlConstants fieldAnnotation = field.getAnnotation(ISqlConstants.class);
            String fieldName = field.getName().toLowerCase();
            Object fieldType = field.getGenericType();
            sql = this.parseToSqlStringNameAndType(fieldName, fieldType, fieldAnnotation, sql);
        }

        sql = sql.substring(0, sql.length() - 2);
        sql += "\n)";
        this.sqlString = sql;
        return this;
    }

    private void executeGetters(Method[] methods, CBaseDevice obj, ArrayList<String> cols, ArrayList<String> rows)
        throws InvocationTargetException, IllegalAccessException {
            for (Method m : methods) {
                if (m.getAnnotation(IRunableGetter.class) != null) {
                    String methodName = m.getName().toLowerCase();
                    //TODO: использовать регулярку вместо substring тк малоли я забуду использовать get в начале метода
                    methodName = methodName.substring(3, methodName.length());
                    Class<?> type = m.getReturnType();
                    String stringObject = "";
                    stringObject = String.valueOf(m.invoke(obj));

                    if (type == Date.class && m.invoke(obj) == null) {
                        stringObject = "2020-01-01 10:10:1";
                    }

                    cols.add(methodName);
                    rows.add(stringObject);
                }
            }
    }

    private void InsertByObject(CBaseDevice obj) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        ArrayList<String> cols = new ArrayList<String>();
        ArrayList<String> rows = new ArrayList<String>();

        if (this.getClass().getSuperclass() != null) {
            Method[] methodsSuper = obj.getClass().getSuperclass().getDeclaredMethods();
            this.executeGetters(methodsSuper, obj, cols, rows);
        }

        Method[] methods = this.getClass().getDeclaredMethods();
        this.executeGetters(methods, obj, cols, rows);
        this.insert(obj.getClass().getSimpleName().toLowerCase(), cols, rows).executeUpdate();
    }

}
