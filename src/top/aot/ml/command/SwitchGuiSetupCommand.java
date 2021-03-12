package top.aot.ml.command;

import org.bukkit.entity.Player;
import setting.GuiSetup;
import top.aot.plugin.aml.APlugin.Command;
import top.aot.plugin.aml.APlugin.Msg;

/**
 * @author aoisa
 */
public class SwitchGuiSetupCommand extends Command {

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
