package top.aot.sp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.aot.cls.Cls;
import top.aot.plugin.APlugin.GuiBase;
import top.aot.sp.gui.ShopGui;

public class ASPCommand implements CommandExecutor {

	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}

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
