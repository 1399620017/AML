package top.aot.module;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/9/14 15:17
 * @description：sx模块
 */
public final class msx {

    interface sxmi {

    }

    public enum esx implements sxmi{
        enable {

        },
        disable {

        }
    }

    private static sxmi sxmi = esx.disable;

    private msx() {}

    public static sxmi get() {
        return sxmi;
    }

    public static void enable() {
        sxmi = esx.enable;
    }

    public static void disable() {
        sxmi = esx.disable;
    }

}
