package setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import top.aot.bean.Monster;
import top.aot.cls.Cls;
import top.aot.constant.StringConstant;
import top.aot.plugin.APlugin;
import top.aot.plugin.APlugin.AsxConfig;

/**
 * @author aoisa
 */
public class MonsterList extends AsxConfig {

    public static MonsterList list;

    static {
        Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
    }

    private static final Map<String, Monster> A_MAP = new HashMap<>();
    private static final Map<String, Monster> B_MAP = new HashMap<>();

    public MonsterList() {
        super(StringConstant.MONSTERLIST_FILE_NAME);
    }

    public int getMonsterNum() {
        return A_MAP.size();
    }

    public static void reload() {
        list = new MonsterList();
        APlugin.Msg.sendConMsgTrue(StringConstant.MONSTERLIST_LOAD_TIPS + A_MAP.size());
    }

    @Override
    protected void defaultValue() {
        customConfig.set("a.name", "§a等级 1 家养牛");
        List<String> attrs = new ArrayList<>();
        attrs.add("§a攻击力 4");
        attrs.add("§a锋利度 8");
        attrs.add("§a护甲值 6");
        customConfig.set("a.attrs", attrs);
        List<String> desc = new ArrayList<>();
        desc.add("§f常见的家养牛");
        customConfig.set("a.desc", desc);
        List<String> drops = new ArrayList<>();
        drops.add("§f牛皮*1 10%");
        customConfig.set("a.drops", drops);
        customConfig.set("a.location", "刘家村");
        customConfig.set("a.itemId", 397);
        customConfig.set("a.touId", 0);
        customConfig.set("a.health", "80");
        List<String> repeatList = new ArrayList<>();
        repeatList.add("/say §e无限次执行的命令列表");
        repeatList.add("/say §e带/的命令是玩家执行的命令");
        repeatList.add("/say §c玩家执行的命令同样无视权限");
        repeatList.add("say §e不带/的命令是后台执行的命令");
        customConfig.set("a.repeat.list", repeatList);
        customConfig.set("a.repeat.explain", "§a无限次数奖励说明");
        List<String> onlyList = new ArrayList<>();
        onlyList.add("/say §e单次执行的命令列表");
        customConfig.set("a.only.list", onlyList);
        customConfig.set("a.only.explain", "§a单次数奖励说明");
        customConfig.set("a.customDesc", new ArrayList<String>());
        customConfig.set("a.npc", false);
    }

    @Override
    protected void saveConfig(FileConfiguration config) {

    }

    @Override
    protected void loadConfig(FileConfiguration config) {
        A_MAP.clear();
        B_MAP.clear();
        for (String key : config.getKeys(false)) {
            Monster monster;
            A_MAP.put(key, monster = new Monster(key)
                    .setName(config.getString(key + ".name"))
                    .setAttrs(config.getStringList(key + ".attrs"))
                    .setDesc(config.getStringList(key + ".desc"))
                    .setDrops(config.getStringList(key + ".drops"))
                    .setLocation(config.getString(key + ".location", "未知"))
                    .setItemId(config.getInt(key + ".itemId", 0))
                    .setTouId(config.getInt(key + ".touId", 0))
                    .setHealth(config.getString(key + ".health", "1"))
                    .setOnlyList(config.getStringList(key + ".only.list"))
                    .setRepeatList(config.getStringList(key + ".repeat.list"))
                    .setOnlyExplain(config.getString(key + ".only.explan", ""))
                    .setOnlySlot(config.getInt(key + ".only.slot", 0))
                    .setRepeatExplain(config.getString(key + ".repeat.explan", ""))
                    .setCustomDesc(config.getStringList(key + ".customDesc"))
                    .setNpc(config.getBoolean(key + ".npc", false))
            );
            B_MAP.put(monster.getName(), monster);
        }
    }

    public Monster getMonsterById(String id) {
        if (A_MAP.containsKey(id)) {
            return A_MAP.get(id);
        }
        return null;
    }

    public static Monster getMonster(String id) {
        return A_MAP.getOrDefault(id, null);
    }

    public Map<String, Monster> getMonsterNameList() {
        return B_MAP;
    }

}
