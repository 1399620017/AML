package top.aot.sp;

import setting.MLShop;
import top.aot.itf.Main;
import top.aot.cls.Cls;
import top.aot.plugin.APlugin;
import top.aot.sp.command.ASPCommand;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/9/21 12:50
 * @description：
 */
public enum spm implements Main {

    A{
        @Override
        public void init() {
            MLShop.reload();
            APlugin.plugin.getCommand("asp").setExecutor(new ASPCommand());
        }
    };
    static {
        Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
    }
}
