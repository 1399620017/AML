package top.aot.cp.role;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import top.aot.bean.Monster;
import top.aot.constant.IntegerConstant;
import top.aot.constant.StringConstant;
import top.aot.cp.entity.Copy;
import top.aot.plugin.aml.APlugin.Util.DateTool;
import top.aot.plugin.aml.APlugin.AsxConfig;

import java.util.*;

/**
 * @author ：aoisa
 * @date ：Created in 2020/10/23 10:47
 * @description：
 */
public class CpRole extends AsxConfig {

    private static final Map<String, CpRole> MAP = new HashMap<>();

    public static CpRole getRole(Player player) {
        if (MAP.containsKey(player.getName())) {
            return MAP.get(player.getName());
        } else {
            CpRole cpRole = new CpRole(player.getName());
            MAP.put(player.getName(), cpRole);
            return cpRole;
        }
    }

    public static CpRole getRole(String playerName) {
        if (MAP.containsKey(playerName)) {
            return MAP.get(playerName);
        } else {
            CpRole cpRole = new CpRole(playerName);
            MAP.put(playerName, cpRole);
            return cpRole;
        }
    }

    public static void reloadRole(String playerName) {
        MAP.put(playerName, new CpRole(playerName));
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
        return customConfig.getString(StringConstant.CONFIG_FIELD_COPYING);
    }

    private long getLastCopyEndTime() {
        return customConfig.getLong(StringConstant.CONFIG_FIELD_LASTCOPYENDTIME, IntegerConstant.CONFIG_FIELD_VALUE_0);
    }

    public int getNumber(Copy copy) {
        checkTime(copy.getTimeType());
        return customConfig.getInt("number." + copy.getTimeType() + "." + copy.key, IntegerConstant.CONFIG_FIELD_VALUE_0);
    }

    public void costNumber(Copy copy) {
        checkTime(copy.getTimeType());
        String key = "number." + copy.getTimeType() + "." + copy.key;
        int number = customConfig.getInt(key, IntegerConstant.CONFIG_FIELD_VALUE_0);
        customConfig.set(key, number + 1);
        update();
    }

    public void start(Copy copy) {
        // 设置副本自动结束时间
        customConfig.set(StringConstant.CONFIG_FIELD_LASTCOPYENDTIME, System.currentTimeMillis() + 1000 * copy.limitTime);
        // 设置正在执行的副本id
        customConfig.set(StringConstant.CONFIG_FIELD_COPYING, copy.key);
        customConfig.set(StringConstant.CONFIG_FIELD_KILLNUMBER, null);
        customConfig.set(StringConstant.CONFIG_FIELD_FINISH, false);
        customConfig.set(StringConstant.CONFIG_FIELD_FINISHNAME, null);
        // 保存数据
        update();
    }

    /** 退出副本 */
    public void quitCopy() {
        customConfig.set(StringConstant.CONFIG_FIELD_LASTCOPYENDTIME, IntegerConstant.CONFIG_FIELD_VALUE_0);
        customConfig.set(StringConstant.CONFIG_FIELD_COPYING, null);
        customConfig.set(StringConstant.CONFIG_FIELD_KILLNUMBER, null);
        customConfig.set(StringConstant.CONFIG_FIELD_FINISH, false);
        customConfig.set(StringConstant.CONFIG_FIELD_FINISHNAME, null);
        update();
    }

    /** 获取当前阶段 -1等于完成全部（无用） */
    public int getStage() {
        return customConfig.getInt("stage", -1);
    }

    /** 获取当前进行的副本 null等于没有进行任何副本 */
    public String getCurrentCopyId() {
        if (getLastCopyEndTime() < System.currentTimeMillis()) {
            return null;
        }
        return getCopying();
    }

    /** 传入击杀的生物 返回数量 */
    public int killMonster(Monster monster) {
        String currentCopyName = getCurrentCopyId();
        if (currentCopyName != null) {
            int killNumber = getKillNumber(monster);
            killNumber++;
            setKillNubmer(monster, killNumber);
            return killNumber;
        }
        return 1;
    }

    /** 获取当前副本击杀怪物计数 */
    private int getKillNumber(Monster monster) {
        return customConfig.getInt("killNumber." + monster.getId(), IntegerConstant.CONFIG_FIELD_VALUE_0);
    }

    /** 设置当前副本击杀怪物计数 */
    private void setKillNubmer(Monster monster, int number) {
        customConfig.set("killNumber." + monster.getId(), number);
        update();
    }

    /** 检查副本次数 */
    private void checkTime(String timeType) {
        switch (timeType) {
            case StringConstant.COPY_COUNT_TYPE_DAY:
                String day = DateTool.getDayNumber();
                if (!Objects.equals(day, customConfig.getString(StringConstant.CONFIG_FIELD_DAY, StringConstant.CONFIG_FIELD_DEFAULT_STR))) {
                    customConfig.set(StringConstant.CONFIG_FIELD_DAY, day);
                    customConfig.set("number." + timeType, null);
                    update();
                }
                break;
            case StringConstant.COPY_COUNT_TYPE_WEEK:
                String week = DateTool.getWeekNumber();
                if (!Objects.equals(week, customConfig.getString(StringConstant.CONFIG_FIELD_WEEK, StringConstant.CONFIG_FIELD_DEFAULT_STR))) {
                    customConfig.set(StringConstant.CONFIG_FIELD_WEEK, week);
                    customConfig.set("number." + timeType, null);
                    update();
                }
                break;
            case StringConstant.COPY_COUNT_TYPE_MONTH:
                String month = DateTool.getMonthString();
                if (!Objects.equals(month, customConfig.getString(StringConstant.CONFIG_FIELD_MONTH, StringConstant.CONFIG_FIELD_DEFAULT_STR))) {
                    customConfig.set(StringConstant.CONFIG_FIELD_MONTH, month);
                    customConfig.set("number." + timeType, null);
                    update();
                }
                break;
            case StringConstant.COPY_COUNT_TYPE_YEAR:
                String year = DateTool.getMonthString();
                if (!Objects.equals(year, customConfig.getString(StringConstant.CONFIG_FIELD_YEAR, StringConstant.CONFIG_FIELD_DEFAULT_STR))) {
                    customConfig.set(StringConstant.CONFIG_FIELD_YEAR, year);
                    customConfig.set("number." + timeType, null);
                    update();
                }
                break;
            default:
                break;
        }
    }

    public Map<String, Integer> getKillMap() {
        Map<String, Integer> map;
        if (customConfig.contains(StringConstant.CONFIG_FIELD_KILLNUMBER)) {
            ConfigurationSection killNumber = customConfig.getConfigurationSection(StringConstant.CONFIG_FIELD_KILLNUMBER);
            Set<String> keys = killNumber.getKeys(false);
            map = new HashMap<>((int) (keys.size() * 1.25 + 1));
            for (String key : keys) {
                map.put(key, killNumber.getInt(key));
            }
        } else {
            map = Collections.emptyMap();
        }
        return map;
    }

    /** 设置副本为完成 */
    public void setFinish(Copy copy) {
        customConfig.set(StringConstant.CONFIG_FIELD_FINISH, true);
        customConfig.set(StringConstant.CONFIG_FIELD_FINISHNAME, copy.key);
        customConfig.set(StringConstant.CONFIG_FIELD_KILLNUMBER, null);
        customConfig.set(StringConstant.CONFIG_FIELD_COPYING, null);
        customConfig.set(StringConstant.CONFIG_FIELD_LASTCOPYENDTIME, IntegerConstant.CONFIG_FIELD_VALUE_0);
        update();
    }

    /** 领取奖励 */
    public void reward() {
        customConfig.set(StringConstant.CONFIG_FIELD_FINISH, false);
        customConfig.set(StringConstant.CONFIG_FIELD_FINISHNAME, null);
        customConfig.set(StringConstant.CONFIG_FIELD_KILLNUMBER, null);
        customConfig.set(StringConstant.CONFIG_FIELD_COPYING, null);
        customConfig.set(StringConstant.CONFIG_FIELD_LASTCOPYENDTIME, IntegerConstant.CONFIG_FIELD_VALUE_0);
        update();
    }

    /** 当前是否有副本已经完成 */
    public boolean hasFinish() {
        return customConfig.getBoolean(StringConstant.CONFIG_FIELD_FINISH, false);
    }

    /** 获取当前完成的副本 */
    public String getFinish() {
        return customConfig.getString(StringConstant.CONFIG_FIELD_FINISHNAME);
    }
}
