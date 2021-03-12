package top.aot.itf;

import org.bukkit.event.entity.EntityDeathEvent;

/**
 * @author ：aoisa
 * @date ：Created in 2020/9/9 11:50
 * @description： 主类接口
 */
public interface Iu {

    /** 关联onEnable() */
    default void d() {
    }

    /** 关联击杀处理 */
    default void kill(EntityDeathEvent e) {

    }

    /** 命令注册 */
    default void co() {

    }

    /** 监听器注册 */
    default void e() {

    }

    /** 版本判断 */
    default boolean is17Version() {
        return false;
    }

}
