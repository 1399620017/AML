package top.aot.cp.entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import top.aot.bean.Monster;
import top.aot.bean.Reward;
import top.aot.cp.role.CpRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/10/23 14:03
 * @description：
 */
public class Copy {
    // 副本名字
    public String name;
    // 时间单位 日|周|月|无
    private String timeType;
    // 副本图鉴的内部编号
    public String key;
    // 时间单位内副本总次数
    public int number;
    // 限时 以秒为单位，超时自动放弃当前副本
    public int limitTime;
    // 副本说明
    public List<String> desc;
    // 必填数据 最低等级要求
    public int minLevel;
    // 必填数据 最高等级要求
    public int maxLevel;
    // 最大奖励数量 1 背包要有此数量的空位才可以领取奖励
    public int maxNumber;
    // 需求权限 不需要可不填
    public String permission;
    // 进入条件 “name 数量”
    public String items;
    // 所在世界
    public String world;
    // 坐标点 x
    public double x;
    // 坐标点 y
    public double y;
    // 坐标点 z
    public double z;

    public float pitch;

    public float yaw;

    private final Map<String, Integer> map = new HashMap<>();
    private final Map<String, Integer> mapName = new HashMap<>();

    private final Map<String, Reward> rewardMap = new HashMap<>();

    // 世界实例
    public World getWorld() {
        return Bukkit.getWorld(world);
    }

    // 位置实例
    public Location getLocation() {
        World world = getWorld();
        if (world != null) {
            return new Location(getWorld(), x, y, z, yaw, pitch);
        } else {
            return null;
        }
    }

    public String getTimeType() {
        return timeType == null ? "无" : timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public void addMonster(String monsterId, int number) {
        map.put(monsterId, number);
    }

    public void addMonsterName(String monsterName, int number) {
        mapName.put(monsterName, number);
    }

    public Map<String, Integer> getMonsterMap() {
        return map;
    }

    public Map<String, Integer> getMapName() {
        return mapName;
    }

    public void addReward(String name, Reward reward) {
        rewardMap.put(name, reward);
    }

    public boolean hasReward(String name) {
        return rewardMap.containsKey(name);
    }

    public void delReward(String name) {
        rewardMap.remove(name);
    }

    public Map<String, Reward> getRewardMap() {
        return rewardMap;
    }

    // 判断此怪物是否是此副本的怪物
    public boolean hasMonster(Monster monster) {
        return map.containsKey(monster.getId());
    }

    // 获取此副本中此怪物的击杀数量要求
    public int getKillNumber(Monster monster) {
        return map.getOrDefault(monster.getId(), -1);
    }

    public boolean isFinish(CpRole cpRole) {
        Map<String, Integer> pMap = cpRole.getKillMap();
        // 判断是否完成全部击杀
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() > pMap.getOrDefault(entry.getKey(), 0)) {
                return false;
            }
        }
        return true;
    }

    public int getMaxRewardNumber() {
        return maxNumber;
    }
}
