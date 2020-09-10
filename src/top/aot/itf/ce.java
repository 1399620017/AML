package top.aot.itf;

import org.bukkit.entity.Entity;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/9/9 11:49
 * @description：
 */ // 通用实体接口
public interface ce {

    default String getName(Entity entity) {
        return null;
    }
}
