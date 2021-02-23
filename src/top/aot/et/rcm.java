package top.aot.et;

import setting.MLEventList;
import setting.MLEventSetup;
import top.aot.cls.Cls;
import top.aot.et.command.MARCCommand;
import top.aot.itf.Main;
import top.aot.plugin.APlugin;

public enum rcm implements Main {
    A {
        @Override
        public void init() {
            setting = new MLEventSetup();
            MLEventList.reload();
            APlugin.plugin.getCommand("amrc").setExecutor(new MARCCommand());
        }
    };

    static {
        Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
    }

    public static MLEventSetup setting;

}
