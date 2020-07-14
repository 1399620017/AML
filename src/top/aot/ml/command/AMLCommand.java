package top.aot.ml.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import setting.MonsterList;
import top.aot.ml.MListMain;
import top.aot.ml.cls.Cls;
import top.aot.ml.plugin.APlugin;
import top.aot.ml.plugin.APlugin.GuiBase;

import java.util.Objects;

public class AMLCommand implements CommandExecutor {

    static {
        Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
    }

    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        switch (arg3.length) {
            case 1:
                if (Objects.equals(arg3[0], "v")) {
                    APlugin.Msg.sendMsgTrue(arg0, Cls.C.version());
                }
                break;
            case 0:
                if (arg0 instanceof Player) {
                    Player player = (Player) arg0;
                    GuiBase.openWindow(player, new Cls.MLGui(player));
                } else {
                    if (arg0.isOp()) {
                        APlugin.Msg.sendMsg(arg0, "/asx lock <player> <monsterKey> <true/false> 解锁player的monsterKey的怪物");
                    }
                }
                break;
            case 4:
                if (arg0.isOp()) {
                    if (Objects.equals(arg3[0], "lock")) {
                        Player player = Bukkit.getPlayer(arg3[1]);
                        if (player != null && player.isOnline()) {
                            String monsterKey = arg3[2];
                            Cls.Monster monster = MListMain.list.getMonsterById(monsterKey);
                            if (monster == null) {
                                APlugin.Msg.sendMsgFalse(arg0, "monsterId不存在");
                                return true;
                            }
                            try {
                                boolean b = Boolean.parseBoolean(arg3[3]);
                                Cls.Role role = Cls.Role.getRole(player);
                                if (b) {
                                    role.unlockMonster(monster);
                                    APlugin.Msg.sendMsgTrue(arg0, "解锁成功！");
                                } else {
                                    role.lockMonster(monster);
                                    APlugin.Msg.sendMsgTrue(arg0, "锁定成功！");
                                }
                            } catch (Exception e) {
                                APlugin.Msg.sendMsgFalse(arg0, "开启关闭状态错误");
                                return true;
                            }
                        } else {
                            APlugin.Msg.sendMsgFalse(arg0, "player不在线");
                        }
                    } else {
                        APlugin.Msg.sendMsg(arg0, "/asx lock <player> <monsterKey> <true/false> 解锁player的monsterKey的怪物");
                    }
                }
                break;
            default:
                if (arg0.isOp()) {
                    APlugin.Msg.sendMsg(arg0, "/asx lock <player> <monsterKey> <true/false> 解锁player的monsterKey的怪物");
                }
                break;
        }

        return true;
    }

}
