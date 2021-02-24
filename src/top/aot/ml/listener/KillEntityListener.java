package top.aot.ml.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import top.aot.cls.Cls;

public class KillEntityListener implements Listener{

	@EventHandler
	public void _kill(EntityDeathEvent e) {
		Cls.C._kill(e);
	}
}
