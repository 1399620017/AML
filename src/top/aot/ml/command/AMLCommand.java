package top.aot.ml.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import top.aot.ml.cls.Cls;
import top.aot.ml.plugin.APlugin;
import top.aot.ml.plugin.APlugin.GuiBase;

import java.util.Objects;

public class AMLCommand implements CommandExecutor {

    static {
        Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
    }

    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        if (arg3.length == 1 && Objects.equals(arg3[0], "v")) {
            APlugin.Msg.sendMsgTrue(arg0, Cls.C.version());
        }
        if (arg0 instanceof Player) {
            Player player = (Player) arg0;
            GuiBase.openWindow(player, new Cls.MLGui(player));
        }
        return true;
    }

}
