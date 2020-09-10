package top.aot.et.role;

import org.bukkit.configuration.file.FileConfiguration;
import top.aot.et.RCMain;
import top.aot.bean.RcEvent;
import top.aot.cls.Cls;
import top.aot.plugin.APlugin;

import java.util.*;

public class RcRole extends APlugin.AsxConfig {

	static {
		Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
	}

	private String date;
	private List<String> boxList;
	private int point;

	public RcRole(String fileName) {
		super(fileName);
		reset();
	}
	
	private boolean reset() {
		if (!Objects.equals(APlugin.Util.DateTool.getDateString(), date)) {
			for (String id : RCMain.setting.geteList()) {
				customConfig.set("event." + id, 0);
			}
			point = 0;
			boxList = new ArrayList<>();
			date = APlugin.Util.DateTool.getDateString();
			update();
			return true;
		}
		return false;
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
	 * */
	public Map<String, Integer> getEValues() {
		reset();
		Map<String, Integer> map = new HashMap<>();
		for (String key : RCMain.eventList.getEventTable().keySet()) {
			map.put(key, customConfig.getInt("event." + key, 0));
		}
		map.put("point", customConfig.getInt("point"));
		return map;
	}
	
	/**
	 * 更新任务数值
	 * */
	public void setEValues(String i, int num) {
		reset();
		customConfig.set("event." + i, num);
		update();
	}
	
	/**
	 * 获取任务完成状态
	 * */
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
