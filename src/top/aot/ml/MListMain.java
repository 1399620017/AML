package top.aot.ml;

import org.bukkit.command.ConsoleCommandSender;
import top.aot.cls.Cls;
import top.aot.plugin.aml.APlugin.CorePlugin;

/**
 * @author aoisa
 */
public class MListMain extends CorePlugin {

    @Override
    public void start() {
        Cls.C.d();
    }

    @Override
    public String pluginName() {
        return Cls.C.s(4);
    }

    @Override
    public String serverName() {
        return Cls.C.s(9);
    }

    @Override
    public String pluginCommand() {
        return "ml";
    }

    @Override
    public void listenter() {
        Cls.C.e();
    }

    @Override
    public void command() {
        Cls.C.co();
    }

    @Override
    public String[] consoleLog(ConsoleCommandSender serverSender) {
        return new String[] {
                Cls.C.version()
        };
    }

}
