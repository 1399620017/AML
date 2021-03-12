package top.aot.et.role;

import org.bukkit.configuration.file.FileConfiguration;
import setting.MLEventList;
import top.aot.bean.RcEvent;
import top.aot.constant.IntegerConstant;
import top.aot.et.Rcm;
import top.aot.plugin.aml.APlugin.AsxConfig;
import top.aot.plugin.aml.APlugin.Util.DateTool;

import java.util.*;

/**
 * @author aoisa
 */
public class RcRole extends AsxConfig {

    private String date;
    private List<String> boxList;
    private int point;

    RcRole(String fileName) {
        super(fileName);
        reset();
    }

    private void reset() {
        if (!Objects.equals(DateTool.getDateString(), date)) {
            for (String id : Rcm.setting.geteList()) {
                customConfig.set("event." + id, IntegerConstant.CONFIG_FIELD_VALUE_0);
            }
            point = 0;
            boxList = new ArrayList<>();
            date = DateTool.getDateString();
            update();
        }
    }

    @Override
    protected void defaultValue() {
        date = "0";
        point = 0;
        boxList = new ArrayList<>();
    }

    @Override
    protected void saveConfig(FileConfiguration config) {
        config.set("date", date);
        config.set("boxList", boxList);
        config.set("point", point);
    }

    @Override
    protected void loadConfig(FileConfiguration config) {
        date = config.getString("date");
        boxList = config.getStringList("boxList");
        point = config.getInt("point");
    }

    /**
     * 获取任务数值
     */
    public Map<String, Integer> getEventValues() {
        reset();
        Map<String, Integer> map = new HashMap<>((int) (MLEventList.list.size() * 1.25 + 1));
        for (String key : MLEventList.list.getEventTable().keySet()) {
            map.put(key, customConfig.getInt("event." + key, IntegerConstant.CONFIG_FIELD_VALUE_0));
        }
        map.put("point", customConfig.getInt("point"));
        return map;
    }

    /**
     * 更新任务数值
     */
    public void setEventValues(String i, int num) {
        reset();
        customConfig.set("event." + i, num);
        update();
    }

    /**
     * 获取任务完成状态
     */
    public List<String> getBoxList() {
        reset();
        return boxList;
    }

    public void setEvent(RcEvent event) {
        boxList.add(event.getId());
        point += event.getPoint();
        update();
    }

    public void setBox(RcEvent box) {
        boxList.add(box.getId());
        update();
    }

}
