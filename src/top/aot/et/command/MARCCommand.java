package top.aot.et.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.aot.et.role.RcRole;
import top.aot.et.role.RcRoleList;
import top.aot.cls.Cls;
import top.aot.plugin.APlugin.GuiBase;

public class MARCCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        if (arg0 instanceof Player) {
            Player player = (Player) arg0;
            RcRole role = RcRoleList.getRole(player);
            GuiBase.openWindow(player, new Cls.RcGui(player, role.getEValues().get("point")));
        }
        return true;
    }

}
