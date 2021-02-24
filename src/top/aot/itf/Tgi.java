package top.aot.itf;

import org.bukkit.Material;

import java.util.List;

/**
 * monster项目只读接口
 *
 * @author root
 */
public interface Tgi {

    /**
     * 获取Monster的名字
     * @return name
     */
    String getName();

    /**
     * 获取Monster的图标
     * @return 图标材质
     */
    Material getMaterial();

    /**
     * 获取Monster的子ID
     * @return 子ID
     */
    short getDataId();

    /**
     * 获取Monster的说明信息(lore)
     * @return 物品Lore
     */
    List<String> getDesc();

    /**
     * 获取Monster的图标显示数量
     * @return 物品显示数量
     */
    int getNumber();

    /**
     * 获取Monster的图标栏位
     * @return 物品栏位
     */
    int getSlot();

    /**
     * 获取包含的Monster列表
     * @return 包含的Monster列表
     */
    List<String> getMonsterList();
}
