package top.aot.cp.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import setting.CopyList;
import setting.MonsterList;
import top.aot.bean.Monster;
import top.aot.bean.Reward;
import top.aot.cp.entity.Copy;
import top.aot.cp.gui.CopyGui;
import top.aot.cp.role.CpRole;
import top.aot.plugin.APlugin.GuiBase;
import top.aot.plugin.APlugin.Msg;
import top.aot.plugin.APlugin.Util.PlayerUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/10/23 10:06
 * @description：
 */
public class ACPCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.isOp()) {
            if (strings.length == 1) {
                if (Objects.equals(strings[0], "list")) {
                    Msg.sendMessage(commandSender, "列表如下：");
                    for (Map.Entry<String, Copy> entry : CopyList.map.entrySet()) {
                        Msg.sendMessage(commandSender, "ID: " + entry.getKey() +
                                " 名字： " + entry.getValue().name + " 所在世界： " + entry.getValue().world);
                    }
                    return true;
                } else if (Objects.equals(strings[0], "reload")) {
                    CopyList.reload();
                    Msg.sendMessage(commandSender, "重载成功！");
                    return true;
                } else if (Objects.equals(strings[0], "open")) {
                    if (commandSender instanceof Player) {
                        Player player = (Player) commandSender;
                        CpRole cpRole = CpRole.getRole(player.getName());
                        // 如果玩家有完成的副本时
                        if (cpRole.hasFinish()) {
                            String copyId = cpRole.getFinish();
                            if (CopyList.map.containsKey(copyId)) {
                                Copy copy = CopyList.map.get(copyId);
                                Map<String, Object> data = new HashMap<>();
                                data.put("copy", copy);
                                data.put("cpRole", cpRole);
                                GuiBase.openWindow(player, new CopyGui(player, copy.name, data));
                            } else {
                                Msg.sendMsgFalse(player, "所选副本已经关闭");
                            }
                        } else {
                            String copyId = cpRole.getCurrentCopyId();
                            if (copyId == null) {
                                Msg.sendMsgFalse(player, "你未进行任何副本！");
                            } else if (CopyList.map.containsKey(copyId)) {
                                Copy copy = CopyList.map.get(copyId);
                                Map<String, Object> data = new HashMap<>();
                                data.put("copy", copy);
                                data.put("cpRole", cpRole);
                                GuiBase.openWindow(player, new CopyGui(player, copy.name, data));
                            } else {
                                Msg.sendMsgFalse(player, "所选副本已经关闭");
                            }
                        }
                        return true;
                    } else {
                        Msg.sendMsgFalse(commandSender, "请使用玩家输入此命令");
                    }
                }
            } else if (strings.length == 2) {
                if (Objects.equals(strings[0], "show")) {
                    String copyId = strings[1];
                    Copy copy = CopyList.getCopy(copyId);
                    if (copy != null) {
                        Msg.sendMessage(commandSender, "副本ID:" + copy.key);
                        Msg.sendMessage(commandSender, "副本名字:" + copy.name);
                        Msg.sendMessage(commandSender, "副本位置:");
                        Msg.sendMessage(commandSender, " - 所在世界:" + copy.world);
                        Msg.sendMessage(commandSender, " - 坐标x:" + copy.x);
                        Msg.sendMessage(commandSender, " - 坐标y:" + copy.y);
                        Msg.sendMessage(commandSender, " - 坐标z:" + copy.z);
                        Msg.sendMessage(commandSender, "等级要求:" + copy.minLevel + "-" + copy.maxLevel);
                        Msg.sendMessage(commandSender, "权限要求:" + (copy.permission == null ? "" : copy.permission));
                        Msg.sendMessage(commandSender, "怪物列表:");
                        for (Map.Entry<String, Integer> entry : copy.getMonsterMap().entrySet()) {
                            Msg.sendMessage(commandSender, " - " + entry.getKey() + " *" + entry.getValue());
                        }
                        Msg.sendMessage(commandSender, "奖励列表:");
                        for (Map.Entry<String, Reward> entry : copy.getRewardMap().entrySet()) {
                            Reward reward = entry.getValue();
                            float probability = reward.getProbability() / 100F;
                            Msg.sendMessage(commandSender, String.format(" - %s 类型:%s 概率:%.2f%%",
                                    reward.getName(), reward.getType(), probability));
                        }
                    } else {
                        Msg.sendMsgFalse(commandSender, "所选副本不存在！");
                    }
                    return true;
                } else if (Objects.equals(strings[0], "delitems")) {
                    String copyId = strings[1];
                    Copy copy = CopyList.getCopy(copyId);
                    if (copy != null) {
                        CopyList.delItems(copy);
                        Msg.sendMessage(commandSender, "副本ID:" + copy.key);
                    } else {
                        Msg.sendMsgFalse(commandSender, "所选副本不存在！");
                    }
                    return true;
                }
            } else if (strings.length == 3) {
                switch (strings[0]) {
                    case "open":
                        Player player = Bukkit.getPlayer(strings[1]);
                        if (player != null && player.isOnline() && !player.isDead()) {
                            CpRole cpRole = CpRole.getRole(player.getName());
                            // 如果玩家有完成的副本时
                            if (cpRole.hasFinish()) {
                                String copyId = cpRole.getFinish();
                                if (CopyList.map.containsKey(copyId)) {
                                    Copy copy = CopyList.map.get(copyId);
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("copy", copy);
                                    GuiBase.openWindow(player, new CopyGui(player, copy.name, data));
                                } else {
                                    Msg.sendMsgFalse(player, "所选副本已经关闭");
                                }
                            } else {
                                String copyId = cpRole.getCurrentCopyId();
                                if (copyId == null) {
                                    copyId = strings[2];
                                }
                                if (CopyList.map.containsKey(copyId)) {
                                    Copy copy = CopyList.map.get(copyId);
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("copy", copy);
                                    GuiBase.openWindow(player, new CopyGui(player, copy.name, data));
                                } else {
                                    Msg.sendMsgFalse(player, "所选副本已经关闭");
                                }
                            }
                        }
                        return true;
                    case "create":
                        if (commandSender instanceof Player) {
                            player = (Player) commandSender;
                            String copyId = strings[1];
                            if (CopyList.hasCopy(copyId)) {
                                Msg.sendMsgFalse(commandSender, "此ID的副本已经存在！");
                            } else {
                                String copyName = strings[2].replaceAll("&", "§");
                                Location location = player.getLocation();
                                String world = location.getWorld().getName();
                                double x = location.getX();
                                double y = location.getY();
                                double z = location.getZ();
                                float pitch = location.getPitch();
                                float yaw = location.getYaw();
                                Copy copy = CopyList.create(copyId, copyName, world, x, y, z, pitch, yaw);
                                Msg.sendMsgTrue(player, String.format(
                                        "创建成功！坐标： %s %s %s %s", copy.world,
                                        copy.x, copy.y, copy.z));
                            }
                        } else {
                            Msg.sendMsgFalse(commandSender, "此命令必须使用玩家输入！");
                        }
                        return true;
                    case "settype":
                        String copyId = strings[1];
                        if (CopyList.hasCopy(copyId)) {
                            Copy copy = CopyList.getCopy(copyId);
                            if (Objects.equals(strings[2], "日")) {
                                CopyList.setType(copy, "日");
                            } else if (Objects.equals(strings[2], "周")) {
                                CopyList.setType(copy, "周");
                            } else if (Objects.equals(strings[2], "月")) {
                                CopyList.setType(copy, "月");
                            } else if (Objects.equals(strings[2], "年")) {
                                CopyList.setType(copy, "年");
                            } else {
                                CopyList.setType(copy, "无");
                            }
                            Msg.sendMessage(commandSender, "已经设置副本刷新周期：" + strings[2]);
                        } else {
                            Msg.sendMsgFalse(commandSender, "此ID的副本不存在！");
                        }
                        return true;
                    case "setcount":
                        copyId = strings[1];
                        if (CopyList.hasCopy(copyId)) {
                            Copy copy = CopyList.getCopy(copyId);
                            try {
                                CopyList.setCount(copy, Integer.parseInt(strings[2]));
                                Msg.sendMessage(commandSender, "已经设置副本进入次数：" + strings[2]);
                            } catch (Exception e) {
                                Msg.sendMsgFalse(commandSender, "你必须输入一个整数！");
                            }
                        } else {
                            Msg.sendMsgFalse(commandSender, "此ID的副本不存在！");
                        }
                        return true;
                    case "settime":
                        copyId = strings[1];
                        if (CopyList.hasCopy(copyId)) {
                            Copy copy = CopyList.getCopy(copyId);
                            try {
                                CopyList.setLimitTime(copy, Integer.parseInt(strings[2]));
                                Msg.sendMessage(commandSender, "已经设置副本限制时间：" + strings[2]);
                            } catch (Exception e) {
                                Msg.sendMsgFalse(commandSender, "你必须输入一个整数！");
                            }
                        } else {
                            Msg.sendMsgFalse(commandSender, "此ID的副本不存在！");
                        }
                        return true;
                    case "setdesc":
                        copyId = strings[1];
                        if (CopyList.hasCopy(copyId)) {
                            Copy copy = CopyList.getCopy(copyId);
                            CopyList.setDesc(copy, strings[2]);
                            Msg.sendMessage(commandSender, "已经设置副本说明：" + strings[2]);
                        } else {
                            Msg.sendMsgFalse(commandSender, "此ID的副本不存在！");
                        }
                        return true;
                    case "setloc":
                        copyId = strings[1];
                        if (CopyList.hasCopy(copyId)) {
                            Copy copy = CopyList.getCopy(copyId);
                            Player player1 = Bukkit.getPlayer(strings[2]);
                            if (player1 != null && player1.isOnline()) {
                                CopyList.setLocation(copy, player1.getLocation());
                                Msg.sendMessage(commandSender, "已经设置副本位置!");
                            } else {
                                Msg.sendMsgFalse(commandSender, "玩家不在线！");
                            }
                        } else {
                            Msg.sendMsgFalse(commandSender, "此ID的副本不存在！");
                        }
                        return true;
                    case "setitems":
                        if (commandSender instanceof Player) {
                            player = (Player) commandSender;
                            copyId = strings[1];
                            if (CopyList.hasCopy(copyId)) {
                                Copy copy = CopyList.getCopy(copyId);
                                ItemStack item = PlayerUtil.getItemInHand(player);
                                if (item != null && item.getType() != Material.AIR) {
                                    if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                                        String itemName = item.getItemMeta().getDisplayName();
                                        try {
                                            int num = Integer.parseInt(strings[2]);
                                            String items = itemName + " " + num;
                                            CopyList.setItems(copy, items);
                                            Msg.sendMessage(commandSender, "设置副本门票成功！");
                                        } catch (Exception e) {
                                            Msg.sendMsgFalse(commandSender, "你必须输入一个整数！");
                                        }
                                    } else {
                                        Msg.sendMsgFalse(commandSender, "原版物品不允许设置为门票！");
                                    }
                                } else {
                                    Msg.sendMsgFalse(commandSender, "空气不允许设置为门票！");
                                }
                            } else {
                                Msg.sendMsgFalse(commandSender, "此ID的副本不存在！");
                            }
                        } else {
                            Msg.sendMsgFalse(commandSender, "此命令必须使用玩家输入！");
                        }
                        return true;
                    case "setlevel":
                        copyId = strings[1];
                        if (CopyList.hasCopy(copyId)) {
                            Copy copy = CopyList.getCopy(copyId);
                            String[] levels = strings[2].split("-");
                            if (levels.length == 2) {
                                try {
                                    CopyList.setLevel(copy, Integer.parseInt(levels[0]), Integer.parseInt(levels[1]));
                                    Msg.sendMessage(commandSender, "已经设置副本等级:" + strings[2]);
                                } catch (Exception e) {
                                    Msg.sendMsgFalse(commandSender, "等级格式不正确,例: /acp setlevel 0-99");
                                }
                            } else {
                                Msg.sendMsgFalse(commandSender, "等级格式不正确,例: /acp setlevel 0-99");
                            }
                        } else {
                            Msg.sendMsgFalse(commandSender, "此ID的副本不存在！");
                        }
                        return true;
                    case "setperm":
                        copyId = strings[1];
                        if (CopyList.hasCopy(copyId)) {
                            Copy copy = CopyList.getCopy(copyId);
                            if (Objects.equals(strings[2], "无")) {
                                CopyList.setPermission(copy, null);
                                Msg.sendMessage(commandSender, "已经清空权限要求");
                            } else {
                                CopyList.setPermission(copy, strings[2]);
                                Msg.sendMessage(commandSender, "已经设置副本权限要求：" + strings[2]);
                            }
                        } else {
                            Msg.sendMsgFalse(commandSender, "此ID的副本不存在！");
                        }
                        return true;
                    case "addmonster":
                        copyId = strings[1];
                        if (CopyList.hasCopy(copyId)) {
                            Copy copy = CopyList.getCopy(copyId);
                            String[] monsters = strings[2].split("-");
                            if (monsters.length == 2) {
                                try {
                                    Monster monster = MonsterList.getMonster(monsters[0]);
                                    if (monster != null) {
                                        CopyList.addMonster(copy, monster, Integer.parseInt(monsters[1]));
                                        Msg.sendMessage(commandSender, "已经添加副本怪物:" + strings[2]);
                                    } else {
                                        Msg.sendMsgFalse(commandSender, "ID为" + monsters[0] + "的怪物不存在！");
                                    }
                                } catch (Exception e) {
                                    Msg.sendMsgFalse(commandSender, "命令格式不正确,例: /acp addmonster a-3 代表添加击杀a怪物3次");
                                }
                            } else {
                                Msg.sendMsgFalse(commandSender, "命令格式不正确,例: /acp addmonster a-3 代表添加击杀a怪物3次");
                            }
                        } else {
                            Msg.sendMsgFalse(commandSender, "此ID的副本不存在！");
                        }
                        return true;
                    case "setmaxreward":
                        copyId = strings[1];
                        if (CopyList.hasCopy(copyId)) {
                            Copy copy = CopyList.getCopy(copyId);
                            try {
                                int maxreward = Integer.parseInt(strings[2]);
                                if (maxreward > 0 && maxreward < 27) {
                                    CopyList.setMaxNumber(copy, maxreward);
                                    Msg.sendMessage(commandSender, "设置成功！");
                                } else {
                                    Msg.sendMsgFalse(commandSender, "最大奖励数量不能设置过大");
                                }
                            } catch (Exception e) {
                                Msg.sendMsgFalse(commandSender, "命令格式错误,例: /acp setlevel 0-99");
                            }
                        } else {
                            Msg.sendMsgFalse(commandSender, "此ID的副本不存在！");
                        }
                        return true;
                    default:
                        break;
                }
            } else if (strings.length == 5) {
                if (Objects.equals(strings[0], "addreward")) {
                    String copyId = strings[1];
                    if (CopyList.hasCopy(copyId)) {
                        Copy copy = CopyList.getCopy(copyId);
                        try {
                            int gl =  Integer.parseInt(strings[4]);
                            Reward reward = new Reward();
                            reward.setName(strings[2]);
                            reward.setProbability(gl);
                            if (Objects.equals(strings[3], "item")) {
                                if (commandSender instanceof Player) {
                                    reward.setType("item");
                                    ItemStack itemStack = PlayerUtil.getItemInHand((Player) commandSender);
                                    if (itemStack != null) {
                                        reward.setItemStack(itemStack);
                                    } else {
                                        Msg.sendMsgFalse(commandSender, "必须手持物品才能使用此命令");
                                        return true;
                                    }
                                } else {
                                    Msg.sendMsgFalse(commandSender, "必须使用玩家输入此命令");
                                    return true;
                                }
                            } else {
                                if (commandSender instanceof Player) {
                                    ItemStack itemStack = PlayerUtil.getItemInHand((Player) commandSender);
                                    if (itemStack != null) {
                                        String cmd = strings[3].replaceAll("-", " ");
                                        reward.setType("command");
                                        reward.setCommand(cmd);
                                        reward.setItemStack(itemStack);
                                    } else {
                                        Msg.sendMsgFalse(commandSender, "必须手持物品才能使用此命令");
                                        return true;
                                    }
                                } else {
                                    Msg.sendMsgFalse(commandSender, "必须使用玩家输入此命令");
                                    return true;
                                }
                            }
                            CopyList.addReward(copy, strings[2], reward);
                            Msg.sendMessage(commandSender, "设置奖励成功!");
                        } catch (Exception e) {
                            Msg.sendMsgFalse(commandSender, "概率必须是整数");
                        }
                    } else {
                        Msg.sendMessage(commandSender, "无此ID副本");
                    }
                    return true;
                }
            }
            Msg.sendMessage(commandSender, "/acp open <玩家名字> <副本ID> 打开副本首页");
            Msg.sendMessage(commandSender, "/acp create <副本ID> <副本名字> 从当前所站位置创建副本点,颜色符号使用&");
            Msg.sendMessage(commandSender, "/acp settype <副本ID> <日|周|月|年|无> 设置副本进入次数刷新周期，无周期的不限制次数。");
            Msg.sendMessage(commandSender, "/acp setcount <副本ID> <次数> 设置副本刷新周期内最大进入次数。");
            Msg.sendMessage(commandSender, "/acp settime <副本ID> <时间> 设置每次副本使用的最大时间，单位秒");
            Msg.sendMessage(commandSender, "/acp setdesc <副本ID> <说明> 设置副本说明，颜色符号使用&，配置文件可设置多行。");
            Msg.sendMessage(commandSender, "/acp setloc <副本ID> <玩家名> 设置玩家当前位置为副本传送点。");
            Msg.sendMessage(commandSender, "/acp setitems <副本ID> <数量> 设置手持物品为副本的门票。");
            Msg.sendMessage(commandSender, "/acp delitems <副本ID> 清除副本门票要求。");
            Msg.sendMessage(commandSender, "/acp setlevel <副本ID> <minLevel-maxLevel> 设置副本等级为minLevel到maxLevel之间，包括minLevel和maxLevel。");
            Msg.sendMessage(commandSender, "/acp setperm <副本ID> <权限|无> 设置进入副本的权限要求。");
            Msg.sendMessage(commandSender, "/acp addmonster <副本ID> <怪物ID-数量> 添加击杀怪物列表！");
            Msg.sendMessage(commandSender, "/acp addreward <副本ID> <rewardId> <item|command> <概率万分比> 添加奖励列表，item是手持物品|command是命令,玩家变量<p>,命令中的空格用-代替。");
            Msg.sendMessage(commandSender, "/acp setmaxreward <副本ID> <数量> 设置这个副本随机到的奖励最大数。");
            Msg.sendMessage(commandSender, "/acp list 查看副本列表");
            Msg.sendMessage(commandSender, "/acp show <副本ID> 查看副本详情");
            Msg.sendMessage(commandSender, "/acp reload 手动更改配置后使用此命令重载配置");
        } else {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if (strings.length == 1) {
                    if (Objects.equals(strings[0], "open")) {
                        CpRole cpRole = CpRole.getRole(player.getName());
                        // 如果玩家有完成的副本时
                        if (cpRole.hasFinish()) {
                            String copyId = cpRole.getFinish();
                            if (CopyList.map.containsKey(copyId)) {
                                Copy copy = CopyList.map.get(copyId);
                                Map<String, Object> data = new HashMap<>();
                                data.put("copy", copy);
                                data.put("cpRole", cpRole);
                                GuiBase.openWindow(player, new CopyGui(player, copy.name, data));
                            } else {
                                Msg.sendMsgFalse(player, "所选副本已经关闭");
                            }
                        } else {
                            String copyId = cpRole.getCurrentCopyId();
                            if (copyId == null) {
                                Msg.sendMsgFalse(player, "你未进行任何副本！");
                            } else if (CopyList.map.containsKey(copyId)) {
                                Copy copy = CopyList.map.get(copyId);
                                Map<String, Object> data = new HashMap<>();
                                data.put("copy", copy);
                                data.put("cpRole", cpRole);
                                GuiBase.openWindow(player, new CopyGui(player, copy.name, data));
                            } else {
                                Msg.sendMsgFalse(player, "所选副本已经关闭");
                            }
                        }
                        return true;
                    }
                } else if (strings.length == 2) {
                    if (Objects.equals(strings[0], "open")) {
                        CpRole cpRole = CpRole.getRole(player.getName());
                        // 如果玩家有完成的副本时
                        if (cpRole.hasFinish()) {
                            String copyId = cpRole.getFinish();
                            if (CopyList.map.containsKey(copyId)) {
                                Copy copy = CopyList.map.get(copyId);
                                Map<String, Object> data = new HashMap<>();
                                data.put("copy", copy);
                                GuiBase.openWindow(player, new CopyGui(player, copy.name, data));
                            } else {
                                Msg.sendMsgFalse(player, "所选副本已经关闭");
                            }
                        } else {
                            String copyId = cpRole.getCurrentCopyId();
                            if (copyId == null) {
                                copyId = strings[1];
                            }
                            if (CopyList.map.containsKey(copyId)) {
                                Copy copy = CopyList.map.get(copyId);
                                Map<String, Object> data = new HashMap<>();
                                data.put("copy", copy);
                                GuiBase.openWindow(player, new CopyGui(player, copy.name, data));
                            } else {
                                Msg.sendMsgFalse(player, "所选副本已经关闭");
                            }
                        }
                        return true;
                    }
                }
                Msg.sendMessage(commandSender, "/acp open <副本ID> 打开副本首页");
                Msg.sendMessage(commandSender, "/acp open 打开正在进行的副本");
            }
        }
        return true;
    }
}
