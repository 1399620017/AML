package top.aot.et.command;

import org.bukkit.entity.Player;
import setting.EventList;
import setting.EventSetup;
import top.aot.et.rcm;
import top.aot.cls.Cls;
import top.aot.plugin.APlugin.Command;
import top.aot.plugin.APlugin.Msg;

public class ReloadRcCommand extends Command {

	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}

	public ReloadRcCommand(String name, int len, String usage, String desc, boolean op) {
		super(name, len, usage, desc, op);
	}

	@Override
	public boolean send(Player player, String[] args) {
		rcm.eventList = new EventList();
		rcm.setting = new EventSetup();
		Msg.sendMsgTrue(player, "重载插件配置完毕！");
		return true;
	}

}
