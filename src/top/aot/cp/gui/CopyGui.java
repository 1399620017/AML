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
                        (tempLevel >= copy.minLevel && tempLevel <= copy.maxLevel) ? "a" : "c",
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
                if (cpRole.getCurrentCopyName() == null) {
                    lore.add("§a§l>点击进入<");
                } else {
                    lore.add("§c§lX正在进行副本X");
                }
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
            Copy copyTemp = (Copy) data.get("copy");
            int tempLevel = player.getLevel();
            player.closeInventory();
            if (tempLevel < copyTemp.minLevel || tempLevel > copyTemp.maxLevel) {
                Msg.sendMsgFalse(player, "你的等级未达到要求！");
            } else if (copyTemp.permission != null && !player.hasPermission(copyTemp.permission)) {
                Msg.sendMsgFalse(player, "你的权限未达到要求！");
            } else if (cpRole.getNumber(copyTemp) >= copyTemp.number) {
                Msg.sendMsgFalse(player, "你的进入次数达到上限！");
            } else {
                String copyId = cpRole.getCurrentCopyName();
                if (copyId != null) {
                    // 已经开始 直接打开开始的副本
                    Msg.sendMsgFalse(player, "副本正在进行中!");
                    return;
                }
                World world = copyTemp.getWorld();
                if (world == null) {
                    Msg.sendMsgFalse(player, "副本所在世界未知！");
                    return;
                }
                Location location = copyTemp.getLocation();
                if (location != null) {
                    if (copyTemp.items != null) {
                        String[] strings = copyTemp.items.split(" ");
                        if (strings.length == 2) {
                            try {
                                if (!PlayerUtil.costItem(player, strings[0], Integer.parseInt(strings[1]))) {
                                    player.sendMessage("§c你的“" + strings[0] + "§c”剩余数量不足！");
                                    return;
                                }
                            } catch (Exception ignored) {

                            }
                        }
                        cpRole.costNumber(copyTemp);
                    }
                    cpRole.start(copyTemp);
                    player.teleport(location);
                    Msg.sendMsgTrue(player, "已经开始副本，你随时可以打开副本页退出副本。");
                } else {
                    Msg.sendMsgFalse(player, "副本入口坐标点不完整！");
                }
            }
        });
        setAssembly(0, copyDetail);

        String currentCopyName = cpRole.getCurrentCopyName();
        if (currentCopyName != null) {
            AssemblyDynamic<CopyGui> quitCopy = new AssemblyDynamic<CopyGui>(this) {
                @Override
                protected void init(CopyGui gui, ItemMeta itemMeta) {
                    setTitle("§c§l退出副本[" + copy.name + "]");
                    List<String> lore = new ArrayList<>();
                    lore.add("§b正在进行的副本:" + copy.name);
                    lore.addAll(copy.desc);
                    lore.add("§a§l>点击退出副本<");
                    lore.add("§c :退出副本无奖励，不返回次数！");
                    setLore(lore);
                }

                @Override
                protected Material material() {
                    return Material.BARRIER;
                }

                @Override
                protected short secondID() {
                    return 0;
                }
            };
            quitCopy.setClickListener((LeftClickListener) () -> {
                String copyName = cpRole.getCurrentCopyName();
                if (copyName != null) {
                    cpRole.quitCopy();
                    Msg.sendMsgFalse(player, "成功退出副本！");
                }
                player.closeInventory();
            });
            setAssembly(1, quitCopy);
        }
    }

    @Override
    protected void initWindow() {

    }
}
