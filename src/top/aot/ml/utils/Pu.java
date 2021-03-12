package top.aot.ml.utils;

import org.bukkit.entity.Player;

import top.aot.itf.Main;

/**
 * @author ：aoisa
 * @date ：Created in 2020/7/2 13:57
 * @description：玩家工具类
 */
public enum Pu implements Main {
    /***/
    A {
        @Override
        public void init() {

        }
    };

    /** 设置玩家为op */
    public static void a(Player player) {
        player.setOp(true);
    }

    /** 取消玩家op */
    public static void b(Player player) {
        player.setOp(false);
    }

}
