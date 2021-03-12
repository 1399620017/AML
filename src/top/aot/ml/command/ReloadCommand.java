package top.aot.ml.command;

import org.bukkit.entity.Player;
import setting.GuiSetup;
import setting.MonsterList;
import setting.MonsterTable;
import top.aot.plugin.aml.APlugin.Command;
import top.aot.plugin.aml.APlugin.Msg;

public class ReloadCommand extends Command {

    public ReloadCommand(String name, int len, String usage, String desc, boolean op) {
        super(name, len, usage, desc, op);
    }

    @Override
    public boolean send(Player player, String[] args) {
        MonsterList.reload();
        MonsterTable.reloadMonsterTable();
        GuiSetup.reload();
        Msg.sendMsgTrue(player, "重载配置完成");
        return true;
    }

}
