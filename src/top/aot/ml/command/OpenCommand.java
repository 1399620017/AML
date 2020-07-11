package top.aot.ml.command;

import org.bukkit.entity.Player;

import top.aot.ml.cls.Cls;
import top.aot.ml.plugin.APlugin.Command;
import top.aot.ml.plugin.APlugin.GuiBase;

public class OpenCommand extends Command{

	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}

	public OpenCommand(String name, int len, String usage, String desc, boolean op) {
		super(name, len, usage, desc, op);
	}

	@Override
	public boolean send(Player player, String[] args) {
		GuiBase.openWindow(player, new Cls.MLGui(player));
		return true;
	}

}
