package top.aot.itf;

import org.bukkit.entity.Entity;
import top.aot.ml.nms.Cur;

/**
 * 实体接口
 */
public interface Ei {

    default Object getNmsEntity(Entity entity) {
        return null;
    }

    default Object getCraftCreature(Entity entity) {
        return null;
    }

    default Object getEntityNbt(Object nmsEntity) {
        return null;
    }

    default Cur.Cee getCurrencyEntity(Entity entity) {
        return null;
    }

    default Object getEntityCreature(Object craftCreature) {
        return null;
    }

    default Object getNmEntityCreature(Object craftCreature) {
        return null;
    }

    default Object getNmEEntityCreature(Object craftCreature) {
        return null;
    }
}
