package top.aot.sp;

import setting.MLShop;
import top.aot.itf.Main;
import top.aot.plugin.aml.APlugin;
import top.aot.sp.command.ASPCommand;

/**
 * @author ：aoisa
 * @date ：Created in 2020/9/21 12:50
 * @description：
 */
public enum Spm implements Main {
    /** 怪物商城模块入口 */
    A{
        @Override
        public void init() {
            MLShop.reload();
            APlugin.plugin.getCommand("asp").setExecutor(new ASPCommand());
        }
    };
}
