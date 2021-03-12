package top.aot.et.command;

import org.bukkit.entity.Player;
import setting.MLEventList;
import setting.MLEventSetup;
import top.aot.et.Rcm;
import top.aot.plugin.aml.APlugin.Command;
import top.aot.plugin.aml.APlugin.Msg;

public class MReloadRcCommand extends Command {

    public MReloadRcCommand(String name, int len, String usage, String desc, boolean op) {
        super(name, len, usage, desc, op);
    }

    @Override
    public boolean send(Player player, String[] args) {
        MLEventList.list = new MLEventList();
        Rcm.setting = new MLEventSetup();
        Msg.sendMsgTrue(player, "重载插件配置完毕！");
        return true;
    }

}
