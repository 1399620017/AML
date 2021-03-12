package top.aot.et.gui;

import org.bukkit.entity.Player;
import top.aot.plugin.aml.APlugin.Gui;

/**
 * @author ：aoisa
 * @date ：Created in 2020/9/11 14:21
 * @description：d
 */
public abstract class EtGui extends Gui {
    public EtGui(Gui beforeGui, Player owner, String title, int lv) {
        super(beforeGui, owner, title, lv);
    }

    public EtGui(Player owner, String title, int lv) {
        super(owner, title, lv);

    }
}
