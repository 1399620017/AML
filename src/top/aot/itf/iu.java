package top.aot.itf;

import org.bukkit.event.entity.EntityDeathEvent;
import top.aot.ml.MListMain;
import top.aot.cls.Cls;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/9/9 11:50
 * @description：
 */
public interface iu {

    String s = Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);

    // 关联onEnable()
    default void d(MListMain t) {
    }

    // 关联击杀处理
    default void _kill(EntityDeathEvent e) {

    }

    // 命令注册
    default void co() {

    }

    // 监听器注册
    default void e(MListMain t) {

    }

    // 版本判断
    default boolean is17Version() {
        return false;
    }

}
