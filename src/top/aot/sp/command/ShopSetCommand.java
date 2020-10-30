package top.aot.sp.command;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import setting.MLShop;
import setting.MonsterList;
import top.aot.bean.Monster;
import top.aot.plugin.APlugin.Util.PlayerUtil;
import top.aot.plugin.APlugin.Command;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/8/13 10:50
 * @description：
 */
public class ShopSetCommand extends Command {
    /**
     * 创建命令类 name 命令字符串 len 命令参数长度
     *
     * @param name
     * @param len
     * @param usage
     * @param desc
     * @param op
     */
    public ShopSetCommand(String name, int len, String usage, String desc, boolean op) {
        super(name, len, usage, desc, op);
    }

    @Override
    public boolean send(Player player, String[] args) {
        int index;
        try {
            index = Integer.parseInt(args[0]);
            if (index < 1 || index > 54) {
                sendFalseMsg(player, "栏位<index>只允许输入1-54的数字");
                return true;
            }
        } catch (Exception e) {
            sendFalseMsg(player, "栏位<index>只允许输入1-54的数字");
            return true;
        }
        index--;
        try {
            Monster monster = MonsterList.list.getMonsterById(args[1]);
            if (monster == null) {
                sendFalseMsg(player, "无此类型怪物！");
                return true;
            }
            int point = Integer.parseInt(args[2]);
            ItemStack itemStackInHand = PlayerUtil.getItemInHand(player);
            if (itemStackInHand != null && itemStackInHand.getType() != Material.AIR) {
                MLShop.Commodity commodity = new MLShop.Commodity(args[1], point, itemStackInHand);
                boolean result = MLShop.getInstance().setCommandity(index, commodity);
                if (result) {
                    sendFalseMsg(player, "成功替换栏位[" + index + "]的商品。" );
                } else {
                    sendTrueMsg(player, "成功添加商品到栏位:" + index);
                }
            } else {
                sendFalseMsg(player, "此商店不支持上架空气！");
            }
        } catch (Exception e) {
            sendFalseMsg(player, "价格<point>请输入整数|可以为负数");
        }
        return true;
    }
}
