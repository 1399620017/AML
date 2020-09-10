package top.aot.itf;

import org.bukkit.entity.Entity;
import top.aot.cls.Cls;
import top.aot.ml.nms.Currency;

/**
 * 实体接口
 */
public interface ei {
    String s = Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);

    default Object getNmsEntity(Entity entity) {
        return null;
    }

    default Object getCraftCreature(Entity entity) {
        return null;
    }

    default Object getEntityNbt(Object nmsEntity) {
        return null;
    }

    default Currency.cee getCurrencyEntity(Entity entity) {
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
