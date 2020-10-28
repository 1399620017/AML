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
import top.aot.cp.entity.Copy;
import top.aot.cp.gui.CopyGui;
import top.aot.cp.role.CpRole;
import top.aot.plugin.APlugin;
import top.aot.plugin.APlugin.Util.PlayerUtil;
import top.aot.plugin.APlugin.GuiBase;
import top.aot.plugin.APlugin.Msg;

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
                        return true;
                    }
                } else if (Objects.equals(strings[0], "reload")) {
                    CopyList.reload();
                    Msg.sendMessage(commandSender, "重载成功！");
                    return true;
                }
            } else if (strings.length == 3) {
                switch (strings[0]) {
                    case "open":
                        Player player = Bukkit.getPlayer(strings[1]);
                        if (player != null && player.isOnline() && !player.isDead()) {
                            CpRole cpRole = CpRole.getRole(player.getName());
                            String copyId = cpRole.getCurrentCopyName();
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
                                        if (itemName.contains(" ")) {
                                            try {
                                                int num = Integer.parseInt(strings[2]);
                                                String items = itemName + " " + num;
                                                CopyList.setItems(copy, items);
                                                Msg.sendMessage(commandSender, "设置副本门票成功！");
                                            } catch (Exception e) {
                                                Msg.sendMsgFalse(commandSender, "你必须输入一个整数！");
                                            }
                                        } else {
                                            Msg.sendMsgFalse(commandSender, "名字中有空格的物品不允许设置为门票！");
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
                    default:
                        break;
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
            Msg.sendMessage(commandSender, "/acp setlevel <副本ID> <minLevel-maxLevel> 设置副本等级为minLevel到maxLevel之间，包括minLevel和maxLevel。");
            Msg.sendMessage(commandSender, "/acp setperm <副本ID> <权限|无> 设置进入副本的权限要求。");
            Msg.sendMessage(commandSender, "/acp list 查看副本列表");
            Msg.sendMessage(commandSender, "/acp reload 手动更改配置后使用此命令重载配置");
        } else {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if (strings.length == 2) {
                    switch (strings[0]) {
                        case "open":
                            if (player.isOnline() && !player.isDead()) {
                                CpRole cpRole = CpRole.getRole(player.getName());
                                String copyId = cpRole.getCurrentCopyName();
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
                            break;
                        case "":
                            break;
                        default:
                            Msg.sendMessage(commandSender, "/acp open <副本ID> 打开副本首页");
                            break;
                    }
                }
            }
        }
        return true;
    }
}
