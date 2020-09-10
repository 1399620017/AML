package top.aot.itf;

import org.bukkit.Material;

import java.util.List;

public interface tgi {

    String getName();
    Material getMaterial();
    short getDataId();
    List<String> getDesc();
    int getNumber();
    int getSlot();
    List<String> getMonsterList();
}
