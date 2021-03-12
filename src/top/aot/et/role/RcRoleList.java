package top.aot.et.role;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class RcRoleList {

    private static final Map<String, RcRole> ROLE_MAP = new HashMap<>();

    public static RcRole getRole(Player player) {
        return getRole(player.getName());
    }

    public static RcRole getRole(String name) {
        if (!ROLE_MAP.containsKey(name)) {
            ROLE_MAP.put(name, new RcRole(name));
        }
        return ROLE_MAP.get(name);
    }

}
