package top.aot.itf;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import top.aot.bean.RcEvent;

import java.util.Map;

public interface ircu {

    // 关联击杀处理
    default void _kill_rc(EntityDeathEvent e) {

    }

    default void _damage_rc(EntityDamageByEntityEvent e) {

    }

    default void ak(RcEvent ee) {

    }

    default void ad(RcEvent ee) {

    }

    default void ck() {

    }

    default void cd() {

    }
}
