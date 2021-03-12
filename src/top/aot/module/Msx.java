package top.aot.module;

/**
 * @author ：aoisa
 * @date ：Created in 2020/9/14 15:17
 * @description：sx模块
 */
public final class Msx {

    interface Sxmi {

    }

    public enum esx implements Sxmi {
        /***/
        enable {

        },
        /***/
        disable {

        }
    }

    private static Sxmi sxmi = esx.disable;

    private Msx() {
    }

    public static Sxmi get() {
        return sxmi;
    }

    public static void enable() {
        sxmi = esx.enable;
    }

    public static void disable() {
        sxmi = esx.disable;
    }

}
