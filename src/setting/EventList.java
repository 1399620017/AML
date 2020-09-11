package setting;

import org.bukkit.configuration.file.FileConfiguration;
import top.aot.et.rcm;
import top.aot.bean.RcEvent;
import top.aot.cls.Cls;
import top.aot.plugin.APlugin.AsxConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventList extends AsxConfig {

	private Map<String, RcEvent> eMap;
	private Map<String, RcEvent> bMap;

	public EventList() {
		super("EventList");
	}

	@Override
	protected void defaultValue() {
		/**
		 * 普通任务
		 */
		customConfig.set("event.1.name", "§e屠牛大师");
		customConfig.set("event.1.type", "killEntity");
		customConfig.set("event.1.content", "§a等级 1 家养牛");
		customConfig.set("event.1.number", 10);
		List<String> cmds = new ArrayList<>();
		cmds.add("azh %player% 2");
		customConfig.set("event.1.cmds", cmds);
		List<String> desc = new ArrayList<>();
		desc.add("§a 战魂点*2");
		customConfig.set("event.1.desc", desc);
		customConfig.set("event.1.point", 5); // point奖励
		customConfig.set("event.1.slot", 0);
		customConfig.set("event.1.level", 0);

		customConfig.set("event.2.name", "§e刺客试炼");
		customConfig.set("event.2.type", "killPlayer");
		customConfig.set("event.2.content", "");
		customConfig.set("event.2.number", 3);
		customConfig.set("event.2.cmds", cmds);
		customConfig.set("event.2.desc", desc);
		customConfig.set("event.2.point", 5); // point奖励
		customConfig.set("event.2.slot", 0);
		customConfig.set("event.2.level", 0);

		customConfig.set("event.3.name", "§e神箭手");
		customConfig.set("event.3.type", "damageType");
		customConfig.set("event.3.content", "BOW");
		customConfig.set("event.3.number", 600);
		customConfig.set("event.3.cmds", cmds);
		customConfig.set("event.3.desc", desc);
		customConfig.set("event.3.point", 6); // point奖励
		customConfig.set("event.3.slot", 0);
		customConfig.set("event.3.level", 0);

		/**
		 * 积分宝箱
		 */
		customConfig.set("box.b1.name", "§e60积分宝箱");
		customConfig.set("box.b1.permission", "vip1");
		customConfig.set("box.b1.type", "-");
		customConfig.set("box.b1.content", "-");
		customConfig.set("box.b1.number", 60); // point要求
		List<String> bosCmds = new ArrayList<>();
		bosCmds.add("azh %player% 2");
		customConfig.set("box.b1.cmds", bosCmds);
		List<String> boxDesc = new ArrayList<>();
		boxDesc.add("§a 战魂点*2");
		customConfig.set("box.b1.desc", boxDesc);
		customConfig.set("box.b1.point", 0);
		customConfig.set("box.b1.slot", 0);
		customConfig.set("box.b1.level", 20);
		/**
		 * 积分宝箱
		 */
		customConfig.set("box.b2.name", "§e60积分宝箱");
		customConfig.set("box.b2.type", "-");
		customConfig.set("box.b2.content", "-");
		customConfig.set("box.b1.number", 60); // point要求
		List<String> bosCmds2 = new ArrayList<>();
		bosCmds.add("azh %player% 2");
		customConfig.set("box.b2.cmds", bosCmds2);
		List<String> boxDesc2 = new ArrayList<>();
		boxDesc.add("§a 战魂点*2");
		customConfig.set("box.b2.desc", boxDesc2);
		customConfig.set("box.b2.point", 0);
		customConfig.set("box.b2.slot", 0);
		customConfig.set("box.b2.level", 20);
	}

	@Override
	protected void saveConfig(FileConfiguration config) {

	}

	@Override
	protected void loadConfig(FileConfiguration config) {
		Cls.E.ck();
		eMap = new HashMap<>();
		for (String key : rcm.setting.geteList()) {
			RcEvent ee = new RcEvent(key).setName(config.getString("event." + key + ".name"))
					.setType(config.getString("event." + key + ".type"))
					.setContent(config.getString("event." + key + ".content"))
					.setNumber(config.getInt("event." + key + ".number"))
					.setCmds(config.getStringList("event." + key + ".cmds"))
					.setDesc(config.getStringList("event." + key + ".desc"))
					.setPoint(config.getInt("event." + key + ".point"))
					.setSlot(config.getInt("event." + key + ".slot"))
					.setLevel(config.getInt("event." + key + ".level"))
					.setPermission(config.getString("event." + key + ".permission", ""));
			eMap.put(key, ee);
			switch (ee.getType()) {
			case "killPlayer":
			case "killEntity":
				Cls.E.ak(ee);
				break;
			case "damageType":
				Cls.E.ad(ee);
			default:
				break;
			}
		}
		bMap = new HashMap<>();
		for (String key : rcm.setting.getbList()) {
			RcEvent eb = new RcEvent(key).setName(config.getString("box." + key + ".name"))
					.setType(config.getString("box." + key + ".type"))
					.setContent(config.getString("box." + key + ".content"))
					.setNumber(config.getInt("box." + key + ".number"))
					.setCmds(config.getStringList("box." + key + ".cmds"))
					.setDesc(config.getStringList("box." + key + ".desc"))
					.setPoint(config.getInt("box." + key + ".point"))
					.setSlot(config.getInt("box." + key + ".slot"))
					.setLevel(config.getInt("box." + key + ".level"))
					.setPermission(config.getString("box." + key + ".permission", ""));
			bMap.put(key, eb);
		}
	}

	public Map<String, RcEvent> getEventTable() {
		return eMap;
	}

	public Map<String, RcEvent> getBoxTable() {
		return bMap;
	}

}
