package top.aot.et.command;

import org.bukkit.entity.Player;
import top.aot.et.gui.RcGui;
import top.aot.et.role.RcRole;
import top.aot.et.role.RcRoleList;
import top.aot.cls.Cls;
import top.aot.plugin.APlugin;

public class OpenRcCommand extends APlugin.Command {

	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}

	public OpenRcCommand(String name, int len, String usage, String desc, boolean op) {
		super(name, len, usage, desc, op);
	}

	@Override
	public boolean send(Player player, String[] args) {
		RcRole role = RcRoleList.getRole(player);
		APlugin.GuiBase.openWindow(player, new RcGui(player, role.getEValues().get("point")));
		return true;
	}

}
