package top.aot.cp.entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/10/23 14:03
 * @description：
 */
public class Copy {
    // 副本名字
    public String name;
    // 时间单位 每日|每周|每月|无限
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
}
