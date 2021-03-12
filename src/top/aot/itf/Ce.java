package top.aot.itf;

import org.bukkit.entity.Entity;

/**
 * @author ：aoisa
 * @date ：Created in 2020/9/9 11:49
 * 通用实体接口
 */
public interface Ce {

    default String getName(Entity entity) {
        return null;
    }
}
