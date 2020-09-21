package top.aot.sp.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import setting.MLShop;
import setting.MLShop.Commodity;
import top.aot.cls.Cls.Monster;
import top.aot.ml.MListMain;
import top.aot.plugin.APlugin.AssemblyDynamic;
import top.aot.plugin.APlugin.Gui;
import top.aot.plugin.APlugin.Msg;
import top.aot.plugin.APlugin.Util.PlayerUtil;
import top.aot.plugin.APlugin.LeftClickListener;
import top.aot.cls.Cls.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/8/13 9:29
 * @description：
 */
public class ShopGui extends Gui {

    private Map<Integer, Commodity> map;
    private Role role;

    public ShopGui(Player owner, Role role) {
        super(owner, "§c§l怪物商店[使用击杀数购买商品]", 6);
        this.role = role;
    }

    @Override
    public boolean clickRegion(String clickedRegionName, ClickType clickType, ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean closeEvent() {
        return false;
    }

    @Override
    public void updateWindow() {
        for (int i = 0; i < 54; i++) {
            if (map.containsKey(i)) {
                Commodity commodity = map.get(i);
                ItemStack rawItemStack = commodity.getItemStack();
                ItemMeta rawItemMeta = rawItemStack.getItemMeta();
                String rawName;
                if (rawItemMeta.hasDisplayName()) {
                    rawName = rawItemMeta.getDisplayName();
                } else {
                    rawName = rawItemStack.getType().name();
                }
                AssemblyDynamic<ShopGui> dynamic = new AssemblyDynamic<ShopGui>(ShopGui.this) {

                    @Override
                    protected void init(ShopGui gui, ItemMeta itemMeta) {
                        Monster monster = MListMain.list.getMonsterById(commodity.getMonsterId());
                        int rolePoint = role.getKillNum(commodity.getMonsterId());
                        setTitle(rawName + " §e售价:" + monster.getName() + "[" + commodity.getPoint() + "]");
                        setLevel(rawItemStack.getAmount());
                        List<String> lore = new ArrayList<>();
                        lore.add("§7§l¤商品信息¤");
                        lore.addAll(rawItemMeta.getLore());
                        if (commodity.getPoint() > rolePoint) {
                            lore.add("§c§lX击杀数不足X §6" + monster.getName() + ":§c" + commodity.getPoint() +  "/" + rolePoint);
                        } else {
                            lore.add("§a§l>点击购买< §6" + monster.getName() + ":§a" + commodity.getPoint() +  "/" + rolePoint);
                        }
                        setLore(lore);
                    }

                    @Override
                    protected Material material() {
                        return commodity.getItemStack().getType();
                    }

                    @Override
                    protected short secondID() {
                        return commodity.getItemStack().getDurability();
                    }
                };
                dynamic.setClickListener((LeftClickListener) () -> {
                    Player owner = getOwner();
                    owner.closeInventory();
                    if (PlayerUtil.getNullSoltNumber(owner) < 1) {
                        Msg.sendMsgFalse(owner, "背包至少保留一个空位！");
                        return;
                    }
                    Monster monster = MListMain.list.getMonsterById(commodity.getMonsterId());
                    String monsterId = monster.getId();
                    if (commodity.getPoint() > role.getKillNum(monsterId)) {
                        Msg.sendMsgFalse(owner, "剩余点数不足！");
                        return;
                    }
                    role.addKillNum(monsterId, -commodity.getPoint());
                    owner.getInventory().addItem(commodity.getItemStack());
                    Msg.sendMsgTrue(owner, String.format("购买成功,剩余击杀数:%s[%s]", monster.getName(), role.getKillNum(monsterId)));
                });
                setAssembly(i, dynamic);
            }
        }
    }

    @Override
    protected void initWindow() {
        map = MLShop.getInstance().getMap();
    }
}
