package top.aot.sp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.aot.cls.Cls;
import top.aot.plugin.aml.APlugin.GuiBase;
import top.aot.sp.gui.ShopGui;

public class ASPCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        if (arg0 instanceof Player) {
            Player player = (Player) arg0;
            Cls.Role role = Cls.Role.getRole(player);
            GuiBase.openWindow(player, new ShopGui(player, role));
        }
        return true;
    }

}
