package top.aot.sp.command;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import setting.MLShop;
import setting.MLShop.Commodity;
import top.aot.cls.Cls.Monster;
import top.aot.ml.MListMain;
import top.aot.plugin.APlugin.Command;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/8/13 10:50
 * @description：
 */
public class ShopAddCommand extends Command {
    /**
     * 创建命令类 name 命令字符串 len 命令参数长度
     *
     * @param name
     * @param len
     * @param usage
     * @param desc
     * @param op
     */
    public ShopAddCommand(String name, int len, String usage, String desc, boolean op) {
        super(name, len, usage, desc, op);
    }

    @Override
    public boolean send(Player player, String[] args) {
        try {
            Monster monster = MListMain.list.getMonsterById(args[0]);
            if (monster == null) {
                sendFalseMsg(player, "无此类型怪物！");
                return true;
            }
            int point = Integer.parseInt(args[1]);
            @SuppressWarnings("all")
            ItemStack itemStackInHand = player.getItemInHand();
            if (itemStackInHand != null && itemStackInHand.getType() != Material.AIR) {
                Commodity commodity = new Commodity(args[0], point, itemStackInHand);
                int index = MLShop.getInstance().addCommodity(commodity);
                if (index < 0) {
                    sendFalseMsg(player, "怪物商城栏位不足！请使用/ml set <index> <monsterId> <point> 方式替换现有商品！");
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
