package top.aot.ml.variable;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;
import setting.MonsterList;
import top.aot.bean.Monster;
import top.aot.cls.Cls;

import java.util.Objects;

public class Variable extends PlaceholderHook {

    public static boolean register() {
        return PlaceholderAPI.registerPlaceholderHook(Cls.C.s(1), new Variable());
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
                        return String.valueOf(MonsterList.list.getMonsterNum());
                    }
                    return "0";
                case "num":
                    // 获取玩家击杀的某类型怪物数量
                    Monster monster = MonsterList.list.getMonsterById(variables[1]);
                    if (Cls.C.ex(monster, false)) {
                        return "0";
                    }
                    role = Cls.Role.getRole(player);
                    return String.valueOf(role.getKillNum(monster));
                case "state":
                    // 获取玩家击杀的某类型怪物数量
                    monster = MonsterList.list.getMonsterById(variables[1]);
                    if (Cls.C.ex(monster, false)) {
                        return "false";
                    }
                    role = Cls.Role.getRole(player);
                    return String.valueOf(role.isUnlock(monster));
                default:
                    break;
            }
        } else if (variables.length == 3) {
            if ("pay".equals(variables[0])) {
                Monster monster = MonsterList.list.getMonsterById(variables[1]);
                if (Cls.C.ex(monster, false)) {
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
            }
        }
        return null;
    }

}
