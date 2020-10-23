package top.aot.ml.command;

import org.bukkit.entity.Player;
import setting.GuiSetup;
import top.aot.cls.Cls;
import top.aot.plugin.APlugin.Command;
import top.aot.plugin.APlugin.Msg;

public class SwitchGuiSetupCommand extends Command{

	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}

	public SwitchGuiSetupCommand(String name, int len, String usage, String desc, boolean op) {
		super(name, len, usage, desc, op);
	}

	@Override
	public boolean send(Player player, String[] args) {
		GuiSetup guiSetup = GuiSetup.table;
		guiSetup.switchEnable();
		if (guiSetup.isEnable()) {
			Msg.sendMsgTrue(player, "已经开启新版GUI配置！");
		} else {
			Msg.sendMsgFalse(player, "已经关闭新版GUI配置！");
		}
		return true;
	}

}
