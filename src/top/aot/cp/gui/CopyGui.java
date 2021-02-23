package top.aot.cp.gui;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import setting.CopyList;
import setting.MonsterList;
import top.aot.bean.Monster;
import top.aot.bean.Reward;
import top.aot.cp.entity.Copy;
import top.aot.cp.role.CpRole;
import top.aot.plugin.APlugin;
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
                if (cpRole.getFinish() != null) {
                    lore.add("§c§lX副本已经完成X");
                } else if (cpRole.getCurrentCopyId() == null) {
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
                String finishCopy = cpRole.getFinish();
                if (finishCopy != null) {
                    // 已经完成副本
                    Msg.sendMsgFalse(player, "副本已经完成，请重新输入/acp open领取奖励!");
                    return;
                }
                String copyId = cpRole.getCurrentCopyId();
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
                    }
                    cpRole.costNumber(copyTemp);
                    cpRole.start(copyTemp);
                    player.teleport(location);
                    Msg.sendMsgTrue(player, "已经开始副本，你随时可以打开副本页退出副本。");
                } else {
                    Msg.sendMsgFalse(player, "副本入口坐标点不完整！");
                }
            }
        });
        setAssembly(0, copyDetail);

        String currentCopyName = cpRole.getCurrentCopyId();
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
                    return Material.REDSTONE_TORCH_ON;
                }

                @Override
                protected short secondID() {
                    return 0;
                }
            };
            quitCopy.setClickListener((LeftClickListener) () -> {
                String copyName = cpRole.getCurrentCopyId();
                if (copyName != null) {
                    cpRole.quitCopy();
                    Msg.sendMsgFalse(player, "成功退出副本！");
                }
                player.closeInventory();
            });
            setAssembly(1, quitCopy);
        }

        // 副本奖励领取
        if (cpRole.hasFinish()) {
            String finishId = cpRole.getFinish();
            Copy finishCopy = CopyList.getCopy(finishId);
            AssemblyDynamic<CopyGui> rewardButton = new AssemblyDynamic<CopyGui>(this) {
                @Override
                protected void init(CopyGui gui, ItemMeta itemMeta) {
                    if (finishCopy != null) {
                        setTitle("§b§l领取副本奖励[" + finishCopy.name + "]");
                        List<String> lore = new ArrayList<>();
                        int maxSlot = finishCopy.getMaxRewardNumber();
                        lore.add("§b副本最大奖励数量:" + maxSlot);
                        lore.add("§c*请保留足够的背包栏位:" + maxSlot);
                        lore.add("§a§l>点击领取副本奖励<");
                        setLore(lore);
                        setLight();
                    } else {
                        setTitle("§c§l完成的副本已经关闭");
                        List<String> lore = new ArrayList<>();
                        lore.add("§c*请退出此副本");
                        setLore(lore);
                        setLight();
                    }
                }

                @Override
                protected Material material() {
                    return Material.REDSTONE_TORCH_ON;
                }

                @Override
                protected short secondID() {
                    return 0;
                }
            };
            rewardButton.setClickListener((LeftClickListener) () -> {
                String tempFinishId = cpRole.getFinish();
                if (tempFinishId != null) {
                    int maxNumber = finishCopy.getMaxRewardNumber();
                    if (PlayerUtil.getNullSoltNumber(player) > maxNumber) {
                        try {
                            cpRole.reward();
                            Map<String, Reward> rewardMap = finishCopy.getRewardMap();
                            int num = 0;
                            for (Map.Entry<String, Reward> entry : rewardMap.entrySet()) {
                                if (num <= maxNumber) {
                                    Reward reward = entry.getValue();
                                    if (Math.random() * 10000 < reward.getProbability()) {
                                        num++;
                                        if (Objects.equals(reward.getType(), "item")) {
                                            player.getInventory().addItem(reward.getItemStack().clone());
                                        } else {
                                            Bukkit.dispatchCommand(APlugin.serverSender,
                                                    reward.getCommand().replaceAll("-", " ")
                                                            .replaceAll("<p>", player.getName()));
                                        }
                                    }
                                } else {
                                    break;
                                }
                            }
                            Msg.sendMsgFalse(player, "成功领取奖励！");
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("此处有错误说明，副本奖励配置的命令有错误！");
                        }
                    } else {
                        Msg.sendMsgFalse(player, "请保留背包空位" + maxNumber + "个以上");
                    }
                }
                player.closeInventory();
            });
            setAssembly(2, rewardButton);
        }

        int monsterIndex = 9;
        for (Map.Entry<String, Integer> entry : copy.getMonsterMap().entrySet()) {
            if (monsterIndex > 35) {
                break;
            }
            String monsterId = entry.getKey();
            Monster monster = MonsterList.getMonster(monsterId);
            if (monster != null) {
                AssemblyDynamic<CopyGui> monsterButton = new AssemblyDynamic<CopyGui>(this) {

                    @Override
                    protected short secondID() {
                        return (short) monster.getTouId();
                    }

                    @Override
                    protected void init(CopyGui gui, ItemMeta itemMeta) {
                        itemMeta.setDisplayName(monster.getName() + "§a§l【副本怪物】");
                        List<String> lore = new ArrayList<>();
                        lore.add("");
                        List<String> customDesc = monster.getCustomDesc();
                        if (customDesc.size() > 0) {
                            lore.addAll(customDesc);
                        } else {
                            lore.add("§f§l[怪物介绍]");
                            lore.addAll(monster.getDesc());
                            lore.add("§f§l[怪物属性]");
                            lore.addAll(monster.getAttrs());
                            lore.add("§a生命值 " + monster.getHealth());
                            lore.add("§f§l[怪物掉落]");
                            lore.addAll(monster.getDrops());
                            lore.add("§d§l[所在地点:" + copy.name + "]");
                        }
                        lore.add("§a此怪物需要击杀数量:" + entry.getValue());
                        itemMeta.setLore(lore);
                    }

                    @Override
                    protected Material material() {
                        return Material.SKULL_ITEM;
                    }

                    @Override
                    protected int itemId() {
                        return monster.getItemId();
                    }
                };
                setAssembly(monsterIndex++, monsterButton);
            }
        }

        int rewardIndex = 36;
        for (Map.Entry<String, Reward> entry : copy.getRewardMap().entrySet()) {
            if (rewardIndex > 53) {
                break;
            }
            Reward reward = entry.getValue();
            ItemStack rewardItem = reward.getItemStack();
            AssemblyDynamic<CopyGui> rewardButton = new AssemblyDynamic<CopyGui>(this) {

                @Override
                protected short secondID() {
                    return 0;
                }

                @Override
                protected void init(CopyGui gui, ItemMeta itemMeta) {
                    setTitle("§e§l【副本奖励】");
                    List<String> lore = new ArrayList<>();
                    float probability = reward.getProbability() / 100F;
                    lore.add(String.format("§b奖励概率 %.2f%%", probability));
                    ItemMeta rewardMeta = rewardItem.getItemMeta();
                    lore.add("§f§l[物品:" + rewardMeta.getDisplayName() + "§f§l]");
                    lore.addAll(rewardMeta.getLore());
                    lore.add("§f§l[物品数量: " + rewardItem.getAmount() + "]");
                    itemMeta.setLore(lore);
                }

                @Override
                protected Material material() {
                    return rewardItem.getType();
                }

                @Override
                protected int itemId() {
                    return 0;
                }
            };
            setAssembly(rewardIndex++, rewardButton);
        }
    }

    @Override
    protected void initWindow() {

    }
}
