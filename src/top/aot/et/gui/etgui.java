package top.aot.et.gui;

import org.bukkit.entity.Player;
import top.aot.plugin.APlugin.Gui;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/9/11 14:21
 * @description：d
 */
public abstract class etgui extends Gui {
    public etgui(Gui beforeGui, Player owner, String title, int lv) {
        super(beforeGui, owner, title, lv);
    }

    public etgui(Player owner, String title, int lv) {
        super(owner, title, lv);

    }
}
