package top.aot.et.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import top.aot.cls.Cls;

public class KillListener implements Listener{

	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void kill(EntityDeathEvent e) {
		Cls.E._kill_rc(e);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void entityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		Cls.E._damage_rc(e);
	}

}
