package top.aot.et.command;

import org.bukkit.entity.Player;
import setting.EventList;
import setting.EventSetup;
import top.aot.et.RCMain;
import top.aot.cls.Cls;
import top.aot.plugin.APlugin;

public class ReloadRcCommand extends APlugin.Command {

	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}

	public ReloadRcCommand(String name, int len, String usage, String desc, boolean op) {
		super(name, len, usage, desc, op);
	}

	@Override
	public boolean send(Player player, String[] args) {
		RCMain.eventList = new EventList();
		RCMain.setting = new EventSetup();
		APlugin.Msg.sendMsgTrue(player, "重载插件配置完毕！");
		return true;
	}

}
