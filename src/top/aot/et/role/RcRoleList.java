package top.aot.et.role;

import org.bukkit.entity.Player;
import top.aot.cls.Cls;

import java.util.HashMap;
import java.util.Map;

public class RcRoleList {

    private static final Map<String, RcRole> roleList = new HashMap<>();

    public static RcRole getRole(Player player) {
        return getRole(player.getName());
    }

    public static RcRole getRole(String name) {
        if (!roleList.containsKey(name)) {
            roleList.put(name, new RcRole(name));
        }
        return roleList.get(name);
    }

}
