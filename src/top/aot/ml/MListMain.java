package top.aot.ml;

import org.bukkit.command.ConsoleCommandSender;
import top.aot.cls.Cls;
import top.aot.plugin.APlugin.CorePlugin;

public class MListMain extends CorePlugin {

    static {
        Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
    }

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
        return Cls.C.s(8);
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
