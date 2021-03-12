package top.aot.ml.gui;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import top.aot.plugin.aml.APlugin.AssemblyDynamic;

import java.util.Arrays;

public abstract class BaseButton<T> extends AssemblyDynamic<T> {

    BaseButton(T gui) {
        super(gui);
    }

    protected abstract String buttonName();

    /**
     * 按钮功能介绍 .n换行
     */
    protected abstract String explain();

    @Override
    protected void init(T gui, ItemMeta itemMeta) {
        setTitle("§b[" + buttonName() + "§b]");
        setLore(Arrays.asList(explain().split("\\.n")));
    }

    @Override
    protected Material material() {
        return Material.SKULL_ITEM;
    }

}
