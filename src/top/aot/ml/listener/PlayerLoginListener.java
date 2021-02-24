package top.aot.ml.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import top.aot.cls.Cls;

public class PlayerLoginListener implements Listener{

	@EventHandler
	public void _login(PlayerJoinEvent e) {
		Cls.Role.reloadRole(e.getPlayer());
	}
}
