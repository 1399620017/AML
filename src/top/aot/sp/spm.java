package top.aot.sp;

import setting.MLShop;
import top.aot.itf.Main;
import top.aot.cls.Cls;

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
        }
    };
    static {
        Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
    }
}
