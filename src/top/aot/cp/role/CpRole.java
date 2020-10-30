package top.aot.cp.role;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import top.aot.bean.Monster;
import top.aot.cp.entity.Copy;
import top.aot.plugin.APlugin.Util.DateTool;
import top.aot.plugin.APlugin.AsxConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/10/23 10:47
 * @description：
 */
public class CpRole extends AsxConfig {

    private static final Map<String, CpRole> map = new HashMap<>();

    public static CpRole getRole(Player player) {
        if (map.containsKey(player.getName())) {
            return map.get(player.getName());
        } else {
            CpRole cpRole = new CpRole(player.getName());
            map.put(player.getName(), cpRole);
            return cpRole;
        }
    }

    public static CpRole getRole(String playerName) {
        if (map.containsKey(playerName)) {
            return map.get(playerName);
        } else {
            CpRole cpRole = new CpRole(playerName);
            map.put(playerName, cpRole);
            return cpRole;
        }
    }

    public static void reloadRole(String playerName) {
        map.put(playerName, new CpRole(playerName));
    }

    private CpRole(String fileName) {
        super(fileName);
    }

    @Override
    protected void defaultValue() {

    }

    @Override
    protected void loadConfig(FileConfiguration config) {

    }

    @Override
    protected void saveConfig(FileConfiguration config) {

    }

    private String getCopying() {
        return customConfig.getString("coyping");
    }

    private long getLastCopyEndTime() {
        return customConfig.getLong("lastCopyEndTime", 0);
    }

    public int getNumber(Copy copy) {
        checkTime(copy.getTimeType());
        return customConfig.getInt("number." + copy.getTimeType() + "." + copy.key, 0);
    }

    public void costNumber(Copy copy) {
        checkTime(copy.getTimeType());
        String key = "number." + copy.getTimeType() + "." + copy.key;
        int number = customConfig.getInt(key, 0);
        customConfig.set(key, number + 1);
        update();
    }

    public void start(Copy copy) {
        // 设置副本自动结束时间
        customConfig.set("lastCopyEndTime", System.currentTimeMillis() + 1000 * copy.limitTime);
        // 设置正在执行的副本id
        customConfig.set("coyping", copy.key);
        // 保存数据
        update();
    }

    // 退出副本
    public void quitCopy() {
        customConfig.set("lastCopyEndTime", 0);
        customConfig.set("coyping", null);
        update();
    }

    // 获取当前阶段 -1等于完成全部
    public int getStage() {
        return customConfig.getInt("stage", -1);
    }

    // 获取当前进行的副本 null等于没有进行任何副本
    public String getCurrentCopyName() {
        if (getLastCopyEndTime() < System.currentTimeMillis()) {
            return null;
        }
        return getCopying();
    }

    // 传入击杀的生物
    public boolean killMonster(Monster monster) {
        String currentCopyName = getCurrentCopyName();
        if (currentCopyName != null) {
            // TODO
        }
        return false;
    }

    // 检查副本次数
    private void checkTime(String timeType) {
        switch (timeType) {
            case "日":
                String day = DateTool.getDayNumber();
                if (!Objects.equals(day, customConfig.getString("day", "1"))) {
                    customConfig.set("day", day);
                    customConfig.set("number." + timeType, null);
                    update();
                }
                break;
            case "周":
                String week = DateTool.getWeekNumber();
                if (!Objects.equals(week, customConfig.getString("week", "1"))) {
                    customConfig.set("week", week);
                    customConfig.set("number." + timeType, null);
                    update();
                }
                break;
            case "月":
                String month = DateTool.getMonthString();
                if (!Objects.equals(month, customConfig.getString("month", "1"))) {
                    customConfig.set("month", month);
                    customConfig.set("number." + timeType, null);
                    update();
                }
                break;
            case "年":
                String year = DateTool.getMonthString();
                if (!Objects.equals(year, customConfig.getString("year", "1"))) {
                    customConfig.set("year", year);
                    customConfig.set("number." + timeType, null);
                    update();
                }
                break;
            default:
                break;
        }
    }
}
