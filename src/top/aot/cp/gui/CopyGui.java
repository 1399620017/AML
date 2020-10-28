package top.aot.cp.gui;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.aot.cp.entity.Copy;
import top.aot.cp.role.CpRole;
import top.aot.plugin.APlugin.Util.PlayerUtil;
import top.aot.plugin.APlugin.Msg;
import top.aot.plugin.APlugin.LeftClickListener;
import top.aot.plugin.APlugin.AssemblyDynamic;
import top.aot.plugin.APlugin.Gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/10/23 10:08
 * @description：
 */
public class CopyGui extends Gui {

    public CopyGui(Player owner, String title, Map<String, Object> data) {
        super(owner, title, 6, data);
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
        Copy copy = (Copy) data.get("copy");
        Player player = getOwner();
        CpRole cpRole = CpRole.getRole(player);
        AssemblyDynamic<CopyGui> copyDetail = new AssemblyDynamic<CopyGui>(this) {
            @Override
            protected void init(CopyGui gui, ItemMeta itemMeta) {
                setTitle(copy.name);
                List<String> lore = new ArrayList<>();
                int tempLevel = player.getLevel();
                lore.add(String.format("§%sLv.%s - Lv.%s",
                        (copy.minLevel >= tempLevel && copy.maxLevel <= tempLevel) ? "a" : "c",
                        copy.minLevel, copy.maxLevel));
                if (copy.permission != null) {
                    lore.add(player.hasPermission(copy.permission) ? "§a§l*你有权限进入" : "§c§lX你暂无权限进入");
                }
                if (copy.items != null) {
                    lore.add("§f进入条件");
                    String[] strings = copy.items.split(" ");
                    if (strings.length == 2) {
                        lore.add("§f :" + strings[0] + " §f" + strings[1] + "个消耗");
                    }
                }
                if (!Objects.equals(copy.getTimeType(), "无")) {
                    lore.add(String.format("§a本%s进入次数: %s/%s",
                            copy.getTimeType(),
                            cpRole.getNumber(copy), copy.number));
                }
                lore.addAll(copy.desc);
                lore.add("§a§l>点击进入<");
                if (copy.limitTime > 0) {
                    lore.add("§c :副本限时" + copy.limitTime + "秒");
                }
                setLore(lore);
//                setLight();
            }

            @Override
            protected Material material() {
                return Material.BOOK;
            }

            @Override
            protected short secondID() {
                return 0;
            }
        };
        copyDetail.setClickListener((LeftClickListener) () -> {
            int tempLevel = player.getLevel();
            player.closeInventory();
            if (copy.minLevel < tempLevel || copy.maxLevel > tempLevel) {
                Msg.sendMsgFalse(player, "你的等级未达到要求！");
                return;
            } else if (copy.permission != null && player.hasPermission(copy.permission)) {
                Msg.sendMsgFalse(player, "你的权限未达到要求！");
                return;
            } else if (cpRole.getNumber(copy) >= copy.number) {
                Msg.sendMsgFalse(player, "你的进入次数达到上限！");
                return;
            } else {
                String copyId = cpRole.getCurrentCopyName();
                if (copyId != null) {
                    // 已经开始 直接打开开始的副本
                    Msg.sendMsgFalse(player, "副本正在进行中!");
                    return;
                }
                World world = copy.getWorld();
                if (world == null) {
                    Msg.sendMsgFalse(player, "副本所在世界未知！");
                    return;
                }
                Location location = copy.getLocation();
                if (location != null) {
                    String[] strings = copy.items.split(" ");
                    if (strings.length == 2) {
                        try {
                            if (!PlayerUtil.costItem(player, strings[0], Integer.parseInt(strings[1]))) {
                                player.sendMessage("§c你的“" + strings[1] + "§c”剩余数量不足！");
                                return;
                            }
                        } catch (Exception ignored) {

                        }
                    }
                    cpRole.costNumber(copy);
                    cpRole.start(copy);
                    player.teleport(location);
                    Msg.sendMsgTrue(player, "已经开始副本，你随时可以打开副本页退出副本。");
                } else {
                    Msg.sendMsgFalse(player, "副本入口坐标点不完整！");
                }
            }
        });
        setAssembly(0, copyDetail);
    }

    @Override
    protected void initWindow() {

    }
}
