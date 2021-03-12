package top.aot.ml.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import setting.MonsterList;
import top.aot.bean.Monster;
import top.aot.cls.Cls;
import top.aot.ml.gui.MLGui;
import top.aot.plugin.aml.APlugin.GuiBase;
import top.aot.plugin.aml.APlugin.Msg;

import java.util.Objects;

public class AMLCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        switch (arg3.length) {
            case 1:
                if (Objects.equals(arg3[0], "v")) {
                    Msg.sendMsgTrue(arg0, Cls.C.version());
                }
                break;
            case 0:
                if (arg0 instanceof Player) {
                    Player player = (Player) arg0;
                    GuiBase.openWindow(player, new MLGui(player));
                } else {
                    if (arg0.isOp()) {
                        Msg.sendMsg(arg0, "/aml lock <player> <monsterKey> <true/false> 解锁player的monsterKey的怪物");
                    }
                }
                break;
            case 4:
                if (arg0.isOp()) {
                    if (Objects.equals(arg3[0], "lock")) {
                        Player player = Bukkit.getPlayer(arg3[1]);
                        if (Cls.C.ex(player, true) && player.isOnline()) {
                            String monsterKey = arg3[2];
                            Monster monster = MonsterList.list.getMonsterById(monsterKey);
                            if (Cls.C.ex(monster, false)) {
                                Msg.sendMsgFalse(arg0, "monsterId不存在");
                                return true;
                            }
                            try {
                                boolean b = Boolean.parseBoolean(arg3[3]);
                                Cls.Role role = Cls.Role.getRole(player);
                                if (b) {
                                    role.unlockMonster(monster);
                                    Msg.sendMsgTrue(arg0, "解锁成功！");
                                } else {
                                    role.lockMonster(monster);
                                    Msg.sendMsgTrue(arg0, "锁定成功！");
                                }
                            } catch (Exception e) {
                                Msg.sendMsgFalse(arg0, "开启关闭状态错误");
                                return true;
                            }
                        } else {
                            Msg.sendMsgFalse(arg0, "player不在线");
                        }
                    } else {
                        Msg.sendMsg(arg0, "/aml lock <player> <monsterKey> <true/false> 解锁player的monsterKey的怪物");
                    }
                }
                break;
            default:
                if (arg0.isOp()) {
                    Msg.sendMsg(arg0, "/aml lock <player> <monsterKey> <true/false> 解锁player的monsterKey的怪物");
                }
                break;
        }

        return true;
    }

}
