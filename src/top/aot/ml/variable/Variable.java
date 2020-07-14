package top.aot.ml.variable;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import top.aot.ml.MListMain;
import top.aot.ml.cls.Cls;
import top.aot.ml.plugin.APlugin;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class Variable extends EZPlaceholderHook {

    static {
        Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
    }

    public Variable() {
        super(APlugin.plugin, Cls.C.s(1));
    }

    @Override
    public String onPlaceholderRequest(Player player, String variable) {
        String[] variables = variable.split("_");
        if (variables.length == 2) {
            switch (variables[0]) {
                case "ms":
                    Cls.Role role = Cls.Role.getRole(player);
                    if (Objects.equals(variables[1], "num")) {
                        return String.valueOf(role.getUnlockNum());
                    } else if (Objects.equals(variables[1], "sum")) {
                        return String.valueOf(MListMain.list.getMonsterNum());
                    }
                    return "0";
                case "num":
                    // 获取玩家击杀的某类型怪物数量
                    Cls.Monster monster = MListMain.list.getMonsterById(variables[1]);
                    if (monster == null) {
                        return "0";
                    }
                    role = Cls.Role.getRole(player);
                    return String.valueOf(role.getKillNum(monster));
                case "state":
                    // 获取玩家击杀的某类型怪物数量
                    monster = MListMain.list.getMonsterById(variables[1]);
                    if (monster == null) {
                        return "false";
                    }
                    role = Cls.Role.getRole(player);
                    return String.valueOf(role.isUnlock(monster));
                default:
                    break;
            }
        } else if (variables.length == 3) {
            switch (variables[0]) {
                case "pay":
                    Cls.Monster monster = MListMain.list.getMonsterById(variables[1]);
                    if (monster == null) {
                        return "false";
                    }
                    Cls.Role role = Cls.Role.getRole(player);
                    int killNum = role.getKillNum(monster);
                    try {
                        int payNum = Integer.parseInt(variables[2]);
                        if (killNum < payNum) {
                            return "false";
                        } else {
                            role.reduceKillNum(monster, payNum);
                            return "true";
                        }
                    } catch (Exception e) {
                        return "false";
                    }
                default:
                    break;
            }
        }
        return null;
    }

}
