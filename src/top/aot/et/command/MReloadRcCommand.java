package top.aot.et.command;

import org.bukkit.entity.Player;
import setting.MLEventList;
import setting.MLEventSetup;
import top.aot.et.rcm;
import top.aot.cls.Cls;
import top.aot.plugin.APlugin.Command;
import top.aot.plugin.APlugin.Msg;

public class MReloadRcCommand extends Command {

    static {
        Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
    }

    public MReloadRcCommand(String name, int len, String usage, String desc, boolean op) {
        super(name, len, usage, desc, op);
    }

    @Override
    public boolean send(Player player, String[] args) {
        MLEventList.list = new MLEventList();
        rcm.setting = new MLEventSetup();
        Msg.sendMsgTrue(player, "重载插件配置完毕！");
        return true;
    }

}
