package setting;

import org.bukkit.configuration.file.FileConfiguration;
import top.aot.plugin.APlugin;

import java.util.ArrayList;
import java.util.List;

public class EventSetup extends APlugin.AsxConfig {

	private List<String> eList;
	private List<String> bList;

	public List<String> geteList() {
		return eList;
	}

	public List<String> getbList() {
		return bList;
	}

	public EventSetup() {
		super("Setup");
	}

	@Override
	protected void defaultValue() {
		eList = new ArrayList<>();
		eList.add("1");
		eList.add("2");
		eList.add("3");
		bList = new ArrayList<>();
		bList.add("b1");
		bList.add("b2");
	}

	@Override
	protected void saveConfig(FileConfiguration config) {
		config.set("eList", eList);
		config.set("bList", bList);
	}

	@Override
	protected void loadConfig(FileConfiguration config) {
		eList = config.getStringList("eList");
		bList = config.getStringList("bList");
	}

}
