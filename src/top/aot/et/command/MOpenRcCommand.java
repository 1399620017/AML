package top.aot.et.command;

import org.bukkit.entity.Player;
import top.aot.et.role.RcRole;
import top.aot.et.role.RcRoleList;
import top.aot.cls.Cls;
import top.aot.plugin.APlugin.Command;
import top.aot.plugin.APlugin.GuiBase;

public class MOpenRcCommand extends Command {

    static {
        Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
    }

    public MOpenRcCommand(String name, int len, String usage, String desc, boolean op) {
        super(name, len, usage, desc, op);
    }

    @Override
    public boolean send(Player player, String[] args) {
        RcRole role = RcRoleList.getRole(player);
        GuiBase.openWindow(player, new Cls.RcGui(player, role.getEValues().get("point")));
        return true;
    }

}
