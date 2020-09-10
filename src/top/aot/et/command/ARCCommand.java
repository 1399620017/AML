package top.aot.et.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.aot.et.gui.RcGui;
import top.aot.et.role.RcRole;
import top.aot.et.role.RcRoleList;
import top.aot.cls.Cls;
import top.aot.plugin.APlugin;

public class ARCCommand implements CommandExecutor {

	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player) {
			Player player = (Player) arg0;
			RcRole role = RcRoleList.getRole(player);
			APlugin.GuiBase.openWindow(player, new RcGui(player, role.getEValues().get("point")));
		}
		return true;
	}

}
