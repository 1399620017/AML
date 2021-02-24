package setting;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import top.aot.bean.Monster;
import top.aot.bean.Reward;
import top.aot.constant.StringConstant;
import top.aot.cp.entity.Copy;
import top.aot.plugin.APlugin.AsxConfig;
import top.aot.plugin.APlugin.Msg;

import java.util.*;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/10/22 21:52
 * @description：
 */
public class CopyList extends AsxConfig {

    private static CopyList instance;

    /** COPY实例MAP */
    public static final Map<String, Copy> COPY_MAP = new HashMap<>();

    public static void reload() {
        instance = new CopyList();
        Msg.sendConMsgTrue(StringConstant.COPYLIST_LOAD_TIPS + COPY_MAP.size());
    }

    private CopyList() {
        super(StringConstant.COPYLIST_FILE_NAME);
    }

    @Override
    protected void defaultValue() {

    }

    @Override
    protected void loadConfig(FileConfiguration config) {
        COPY_MAP.clear();
        for (String copyKey : config.getKeys(false)) {
            Copy copy = new Copy();
            copy.key = copyKey;
            copy.name = config.getString(copyKey + ".name");
            copy.setTimeType(config.getString(copyKey + ".timeType"));
            copy.numberMaxList = config.getStringList(copyKey + ".numberMaxList");
            copy.number = config.getInt(copyKey + ".number");
            copy.limitTime = config.getInt(copyKey + ".limitTime");
            copy.desc = config.getStringList(copyKey + ".desc");
            copy.minLevel = config.getInt(copyKey + ".minLevel");
            copy.maxLevel = config.getInt(copyKey + ".maxLevel");
            copy.maxNumber = config.getInt(copyKey + ".maxNumber", 1);
            copy.permission = config.getString(copyKey + ".permission");
            copy.items = config.getString(copyKey + ".items");
            copy.world = config.getString(copyKey + ".world");
            copy.x = config.getDouble(copyKey + ".x");
            copy.y = config.getDouble(copyKey + ".y");
            copy.z = config.getDouble(copyKey + ".z");
            copy.yaw = (float) config.getDouble(copyKey + ".yaw");
            copy.pitch = (float) config.getDouble(copyKey + ".pitch");
            ConfigurationSection monsterListCofig = config.getConfigurationSection(copyKey + ".monster-list");
            if (monsterListCofig != null) {
                Set<String> monsterIdSet = monsterListCofig.getKeys(false);
                for (String mid : monsterIdSet) {
                    int number = monsterListCofig.getInt(mid);
                    copy.addMonster(mid, number);
                    copy.addMonsterName(MonsterList.getMonster(mid).getName(), number);
                }
            }
            ConfigurationSection rewardListCofig = config.getConfigurationSection(copyKey + ".reward-list");
            if (rewardListCofig != null) {
                Set<String> rewardIdSet = rewardListCofig.getKeys(false);
                for (String rid : rewardIdSet) {
                    Reward reward = new Reward();
                    reward.setName(rid);
                    String type = rewardListCofig.getString(rid + ".type");
                    reward.setProbability(rewardListCofig.getInt(rid + ".probability"));
                    reward.setType(type);
                    reward.setCommand(rewardListCofig.getString(rid + ".command"));
                    reward.setItemStack(rewardListCofig.getItemStack(rid + ".itemStack"));
                    copy.addReward(rid, reward);
                }
            }
            COPY_MAP.put(copyKey, copy);
        }
    }


    /** 删除副本门票要求 */
    public static void delItems(Copy copy) {
        setItems(copy, null);
    }

    /** 添加副本奖励 */
    public static void addReward(Copy copy, String name, Reward reward) {
        copy.addReward(name, reward);
        instance.customConfig.set(copy.key + ".reward-list." + name + ".type", reward.getType());
        instance.customConfig.set(copy.key + ".reward-list." + name + ".probability", reward.getProbability());
        instance.customConfig.set(copy.key + ".reward-list." + name + ".command", reward.getCommand());
        instance.customConfig.set(copy.key + ".reward-list." + name + ".itemStack", reward.getItemStack());
        COPY_MAP.put(copy.key, copy);
        instance.update();
    }

    /** 添加副本怪物 */
    public static void addMonster(Copy copy, Monster monster, int number) {
        copy.addMonster(monster.getId(), number);
        copy.addMonsterName(monster.getName(), number);
        instance.customConfig.set(copy.key + ".monster-list." + monster.getId(), number);
        COPY_MAP.put(copy.key, copy);
        instance.update();
    }

    /** 设置副本最大奖励数量 */
    public static void setMaxNumber(Copy copy, int maxNumber) {
        copy.maxNumber = maxNumber;
        instance.customConfig.set(copy.key + ".maxNumber", maxNumber);
        COPY_MAP.put(copy.key, copy);
        instance.update();
    }

    /** 设置副本等级要求 */
    public static void setLevel(Copy copy, int minLevel, int maxLevel) {
        copy.minLevel = minLevel;
        copy.maxLevel = maxLevel;
        instance.customConfig.set(copy.key + ".minLevel", minLevel);
        instance.customConfig.set(copy.key + ".maxLevel", maxLevel);
        COPY_MAP.put(copy.key, copy);
        instance.update();
    }

    /** 设置副本权限要求 */
    public static void setPermission(Copy copy, String permission) {
        copy.permission = permission;
        instance.customConfig.set(copy.key + ".permission", permission);
        COPY_MAP.put(copy.key, copy);
        instance.update();
    }

    /** 设置手中物品固定数量为进入副本门票 */
    public static void setItems(Copy copy, String items) {
        copy.items = items;
        instance.customConfig.set(copy.key + ".items", items);
        COPY_MAP.put(copy.key, copy);
        instance.update();
    }

    /** 设置当前位置为副本传送点 */
    public static void setLocation(Copy copy, Location location) {
        copy.world = location.getWorld().getName();
        copy.x = location.getX();
        copy.y = location.getY();
        copy.z = location.getZ();
        copy.yaw = location.getYaw();
        copy.pitch = location.getPitch();
        instance.customConfig.set(copy.key + ".world", copy.world);
        instance.customConfig.set(copy.key + ".x", copy.x);
        instance.customConfig.set(copy.key + ".y", copy.y);
        instance.customConfig.set(copy.key + ".z", copy.z);
        instance.customConfig.set(copy.key + ".yaw", copy.yaw);
        instance.customConfig.set(copy.key + ".pitch", copy.pitch);
        COPY_MAP.put(copy.key, copy);
        instance.update();
    }

    /** 设置副本说明 */
    public static void setDesc(Copy copy, String desc) {
        copy.desc = new ArrayList<>();
        copy.desc.add(desc.replaceAll("&", "§"));
        instance.customConfig.set(copy.key + ".desc", copy.desc);
        COPY_MAP.put(copy.key, copy);
        instance.update();
    }

    @Override
    protected void saveConfig(FileConfiguration config) {

    }

    /** 创建一个新的副本的操作, 返回被创建的副本 */
    public static Copy create(String copyId, String copyName, String world, double x, double y,
                              double z, float pitch, float yaw) {
        Copy copy = new Copy();
        copy.key = copyId;
        copy.name = copyName;
        instance.customConfig.set(copyId + ".name", copyName);
        copy.world = world;
        instance.customConfig.set(copyId + ".world", world);
        copy.x = x;
        instance.customConfig.set(copyId + ".x", x);
        copy.y = y;
        instance.customConfig.set(copyId + ".y", y);
        copy.z = z;
        instance.customConfig.set(copyId + ".z", z);
        copy.pitch = pitch;
        instance.customConfig.set(copyId + ".pitch", pitch);
        copy.yaw = yaw;
        instance.customConfig.set(copyId + ".yaw", yaw);
        COPY_MAP.put(copy.key, copy);
        instance.update();
        return copy;
    }

    /** 设置时副本次数刷新周期 */
    public static void setType(Copy copy, String typeName) {
        copy.setTimeType(typeName);
        instance.customConfig.set(copy.key + ".timeType", typeName);
        COPY_MAP.put(copy.key, copy);
        instance.update();
    }

    /** 设置副本进入次数 */
    public static void setCount(Copy copy, int count) {
        copy.number = count;
        instance.customConfig.set(copy.key + ".number", count);
        COPY_MAP.put(copy.key, copy);
        instance.update();
    }

    /** 添加副本额外进入次数 */
    public static void addPermNum(Copy copy, String permStr, int num) {
        copy.numberMaxList.add(permStr + ":" + num);
        instance.customConfig.set(copy.key + ".numberMaxList", copy.numberMaxList);
        COPY_MAP.put(copy.key, copy);
        instance.update();
    }

    /** 清理副本额外进入次数 */
    public static void clearPermNum(Copy copy) {
        instance.customConfig.set(copy.key + ".numberMaxList", null);
        COPY_MAP.put(copy.key, copy);
        instance.update();
    }

    /** 设置副本时间限制 */
    public static void setLimitTime(Copy copy, int limitTime) {
        copy.limitTime = limitTime;
        instance.customConfig.set(copy.key + ".limitTime", limitTime);
        COPY_MAP.put(copy.key, copy);
        instance.update();
    }

    /** 判断副本是否存在 */
    public static boolean hasCopy(String copyId) {
        return COPY_MAP.containsKey(copyId);
    }

    /** 获取副本实例 */
    public static Copy getCopy(String copyId) {
        return COPY_MAP.get(copyId);
    }
}
