package top.aot.itf;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * NMS接口
 */
public interface Ni {

    default String getNmsVersion() {
        return null;
    }

    default Class<?> getNmsClass(String className) {
        return null;
    }

    default Class<?> getCraftClass(String path, String className) {
        return null;
    }

    default Method getMethod(Class<?> cls, String methodName) {
        return null;
    }

    default Method getMethod(Class<?> cls, Class<?> resultCls) {
        return null;
    }

    default Method getMethod(Class<?> cls, Class<?> resultCls, Class<?>... params) {
        return null;
    }

    default Field getField(Class<?> cls, String fieldName) {
        return null;
    }

    default Field getField(Class<?> cls, Class<?> fieldType) {
        return null;
    }

    default void setValue(Field field, Object obj, Object args) {

    }

    default Object getValue(Field field, Object obj) {
        return null;
    }

    default Object invokeMethod(Method method, Object obj, Object... args) {
        return null;
    }

    default Object newNBTTag(Constructor<?> constructor, Object... args) {
        return null;
    }

    default Constructor<?> getConstructor(Class<?> nmsNBTTagLongCls, Class<?>... classes) {
        return null;
    }

    default Class<?> getClassOfPath(String path) {
        return null;
    }
}
