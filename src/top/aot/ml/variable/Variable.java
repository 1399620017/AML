package top.aot.ml.variable;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import top.aot.ml.MListMain;
import top.aot.ml.cls.Cls;
import top.aot.ml.plugin.APlugin;

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
        switch (variable) {
            case "ms_num":
                Cls.Role role = Cls.Role.getRole(player);
                return String.valueOf(role.getUnlockNum());
            case "ms_sum":
                return String.valueOf(MListMain.list.getMonsterNum());
            default:
                break;
        }
        return null;
    }

}
