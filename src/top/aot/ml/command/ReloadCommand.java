package top.aot.ml.command;

import org.bukkit.entity.Player;
import setting.GuiSetup;
import setting.MonsterList;
import setting.MonsterTable;
import top.aot.cls.Cls;
import top.aot.plugin.APlugin.Command;
import top.aot.plugin.APlugin.Msg;

public class ReloadCommand extends Command{

	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}

	public ReloadCommand(String name, int len, String usage, String desc, boolean op) {
		super(name, len, usage, desc, op);
	}

	@Override
	public boolean send(Player player, String[] args) {
		MonsterList.reload();
		MonsterTable.reloadMonsterTable();
		GuiSetup.reloadMonsterTable();
		Msg.sendMsgTrue(player, "重载配置完成");
		return true;
	}

}
