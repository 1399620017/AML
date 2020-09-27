package top.aot.sp.command;

import org.bukkit.entity.Player;
import setting.MLShop;
import top.aot.plugin.APlugin.Command;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/8/13 10:50
 * @description：
 */
public class ShopDelCommand extends Command {
    /**
     * 创建命令类 name 命令字符串 len 命令参数长度
     *
     * @param name
     * @param len
     * @param usage
     * @param desc
     * @param op
     */
    public ShopDelCommand(String name, int len, String usage, String desc, boolean op) {
        super(name, len, usage, desc, op);
    }

    @Override
    public boolean send(Player player, String[] args) {
        int index;
        try {
            index = Integer.parseInt(args[0]);
            if (index < 0 || index > 53) {
                sendFalseMsg(player, "栏位<index>只允许输入0-53的数字");
                return true;
            }
        } catch (Exception e) {
            sendFalseMsg(player, "栏位<index>只允许输入0-53的数字");
            return true;
        }
        boolean result = MLShop.getInstance().delCommandity(index);
        if (result) {
            sendTrueMsg(player, "成功下架栏位[" + index + "]的商品！");
        } else {
            sendFalseMsg(player, "此栏位不存在商品！");
        }
        return true;
    }
}
