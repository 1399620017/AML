package top.aot.ml.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import top.aot.ml.cls.Cls;

public class PlayerLoginListener implements Listener{
	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}

	@EventHandler
	public void _login(PlayerJoinEvent e) {
		Cls.Role.reloadRole(e.getPlayer());
	}
}
