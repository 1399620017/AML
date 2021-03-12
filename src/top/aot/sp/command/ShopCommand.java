package top.aot.sp.command;

import org.bukkit.entity.Player;
import top.aot.cls.Cls.Role;
import top.aot.plugin.aml.APlugin.GuiBase;
import top.aot.plugin.aml.APlugin.Command;
import top.aot.sp.gui.ShopGui;

/**
 * @author ：aoisa
 * @date ：Created in 2020/8/13 10:50
 * @description：
 */
public class ShopCommand extends Command {
    /**
     * 创建命令类 name 命令字符串 len 命令参数长度
     *
     * @param name
     * @param len
     * @param usage
     * @param desc
     * @param op
     */
    public ShopCommand(String name, int len, String usage, String desc, boolean op) {
        super(name, len, usage, desc, op);
    }

    @Override
    public boolean send(Player player, String[] args) {
        Role role = Role.getRole(player);
        GuiBase.openWindow(player, new ShopGui(player, role));
        return true;
    }
}
