package top.aot.et;

import setting.MLEventList;
import setting.MLEventSetup;
import top.aot.et.command.MARCCommand;
import top.aot.itf.Main;
import top.aot.plugin.aml.APlugin;

public enum Rcm implements Main {
    /** 悬赏任务模块实例 */
    A {
        @Override
        public void init() {
            setting = new MLEventSetup();
            MLEventList.reload();
            APlugin.plugin.getCommand("amrc").setExecutor(new MARCCommand());
        }
    };

    public static MLEventSetup setting;

}
