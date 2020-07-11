package top.aot.ml;

import org.bukkit.command.ConsoleCommandSender;
import setting.MonsterList;
import top.aot.ml.cls.Cls;
import top.aot.ml.plugin.APlugin.CorePlugin;

public class MListMain extends CorePlugin {

    static {
        Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
    }

    public static MonsterList list;

    @Override
    public void start() {
        Cls.C.d(this);
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
        Cls.C.e(this);
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
