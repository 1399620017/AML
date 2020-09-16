package top.aot.itf;

import org.bukkit.Material;

import java.util.List;

// monster项目只读接口
public interface tgi {

    String getName();
    Material getMaterial();
    short getDataId();
    List<String> getDesc();
    int getNumber();
    int getSlot();
    List<String> getMonsterList();
}
