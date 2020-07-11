package top.aot.ml.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import top.aot.ml.cls.Cls;

public class KillEntityListener implements Listener{
	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}

	@EventHandler
	public void _kill(EntityDeathEvent e) {
		Cls.C._kill(e);
	}
}
