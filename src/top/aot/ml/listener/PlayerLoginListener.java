package top.aot.ml.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import top.aot.cls.Cls;

/**
 * @author aoisa
 */
public class PlayerLoginListener implements Listener {

    @EventHandler
    public void login(PlayerJoinEvent e) {
        Cls.Role.reloadRole(e.getPlayer());
    }
}
