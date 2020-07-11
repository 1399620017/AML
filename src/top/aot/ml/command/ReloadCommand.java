package top.aot.ml.command;

import org.bukkit.entity.Player;

import setting.MonsterList;
import setting.MonsterTable;
import top.aot.ml.MListMain;
import top.aot.ml.cls.Cls;
import top.aot.ml.plugin.APlugin.Command;
import top.aot.ml.plugin.APlugin.Msg;

public class ReloadCommand extends Command{

	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}

	public ReloadCommand(String name, int len, String usage, String desc, boolean op) {
		super(name, len, usage, desc, op);
	}

	@Override
	public boolean send(Player player, String[] args) {
		MListMain.list = new MonsterList();
		MonsterTable.reloadMonsterTable();
		Msg.sendMsgTrue(player, "重载配置完成");
		return true;
	}

}
