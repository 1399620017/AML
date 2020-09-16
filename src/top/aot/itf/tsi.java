package top.aot.itf;

import java.util.List;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/9/9 14:02
 * @description： monster项目读写接口
 */
public interface tsi extends tgi {

    void setName(String name);
    void setItemId(int itemId);
    void setDataId(short dataId);
    void setItemName(String itemName);
    void setDesc(List<String> desc);
    void setNumber(int number);
    void setSlot(int slot);
    void setMonsterList(List<String> monsterList);
}
