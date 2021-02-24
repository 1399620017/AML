package top.aot.itf;

import java.util.List;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/9/9 14:02
 * @description： monster项目读写接口
 */
public interface Tsi extends Tgi {

    /**
     * 设置名称
     *
     * @param name
     * @return void
     * @author aoisa
     * @date 2021/2/24 9:01
     */
    void setName(String name);

    /**
     * 设置物品ID
     *
     * @param itemId
     * @return void
     * @author aoisa
     * @date 2021/2/24 9:02
     */
    void setItemId(int itemId);

    /**
     * 设置物品子ID
     *
     * @param dataId
     * @return void
     * @author aoisa
     * @date 2021/2/24 9:02
     */
    void setDataId(short dataId);

    /**
     * 设置物品显示名字
     *
     * @param itemName
     * @return void
     * @author ZhangHe
     * @date 2021/2/24 9:02
     */
    void setItemName(String itemName);

    /**
     * 设置物品Lore描述
     *
     * @param desc
     * @return void
     * @author aoisa
     * @date 2021/2/24 9:02
     */
    void setDesc(List<String> desc);

    /**
     * 设置物品显示数量
     *
     * @param number
     * @return void
     * @author aoisa
     * @date 2021/2/24 9:03
     */
    void setNumber(int number);

    /**
     * 设置物品栏位
     *
     * @param slot
     * @return void
     * @author aoisa
     * @date 2021/2/24 9:03
     */
    void setSlot(int slot);

    /**
     * 设置Monster列表
     *
     * @param monsterList
     * @return void
     * @author aoisa
     * @date 2021/2/24 9:03
     */
    void setMonsterList(List<String> monsterList);
}
