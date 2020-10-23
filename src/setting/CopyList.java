package setting;

import org.bukkit.configuration.file.FileConfiguration;
import top.aot.cls.Cls.Copy;
import top.aot.plugin.APlugin.Msg;
import top.aot.plugin.APlugin.AsxConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/10/22 21:52
 * @description：
 */
public class CopyList extends AsxConfig {

    public static CopyList instance;

    public static final Map<String, Copy> map = new HashMap<>();

    public static void reload() {
        instance = new CopyList();
        Msg.sendConMsgTrue("加载副本图鉴数量:" + map.size());
    }

    private CopyList() {
        super("CopyList");
    }

    @Override
    protected void defaultValue() {

    }

    @Override
    protected void loadConfig(FileConfiguration config) {
        for (String copyKey : config.getKeys(false)) {
            Copy copy = new Copy();
            copy.key = copyKey;
            copy.name = config.getString(copyKey + ".name");
            copy.timeType = config.getString(copyKey + ".timeType");
            copy.number = config.getInt(copyKey + ".number");
            copy.limitTime = config.getInt(copyKey + ".limitTime");
        }
    }

    @Override
    protected void saveConfig(FileConfiguration config) {

    }
}
