package top.aot.et.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import top.aot.cls.Cls;

/**
 * @author aoisa
 */
public class KillListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void kill(EntityDeathEvent e) {
        Cls.E._kill_rc(e);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void entityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        Cls.E._damage_rc(e);
    }

}
