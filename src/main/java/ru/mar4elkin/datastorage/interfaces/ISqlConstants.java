package ru.mar4elkin.datastorage.interfaces;
import ru.mar4elkin.datastorage.enums.ESqlAttrs;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)

@Target(ElementType.FIELD)
public @interface ISqlConstants {
    ESqlAttrs[] constants();
}
