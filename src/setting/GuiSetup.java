package setting;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import top.aot.cls.Cls;
import top.aot.itf.tgi;
import top.aot.itf.tsi;
import top.aot.plugin.APlugin.Msg;
import top.aot.plugin.APlugin.AsxConfig;

import java.util.*;

public class GuiSetup extends AsxConfig {

    private boolean enable;

    static {
        Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
    }

    public static GuiSetup table;

    public static void reload() {
        table = new GuiSetup();
    }

    public GuiSetup() {
        super("monsterTableGuiSetup");
    }

    private Map<String, tgi> tgiMap;

    @Override
    protected void defaultValue() {
        customConfig.set("setting.enable", true);
        customConfig.set("item.a.name", "§a这是怪物名字");
        customConfig.set("item.a.itemId", 397);
        customConfig.set("item.a.dataId", 0);
        customConfig.set("item.a.itemName", Material.APPLE.name());
        List<String> list = new ArrayList<>();
        list.add("§a这是分类说明");
        list.add("§b可以填写多行");
        customConfig.set("item.a.desc", list);
        customConfig.set("item.a.number", 1);
        customConfig.set("item.a.slot", 1);
        List<String> monsterList = new ArrayList<>();
        monsterList.add("a");
        monsterList.add("填写monster项目");
        monsterList.add("可多行");
        customConfig.set("item.a.monsterList", monsterList);
    }

    @Override
    protected void saveConfig(FileConfiguration config) {
        config.set("setting.enable", enable);
    }

    @Override
    protected void loadConfig(FileConfiguration config) {
        enable = config.getBoolean("setting.enable", false);
        tgiMap = new HashMap<>();
        ConfigurationSection itemConfig = config.getConfigurationSection("item");
        Set<String> typeNameSet = itemConfig.getKeys(false);
        for (String typeName : typeNameSet) {
            tsi tsi = new ti();
            String name = itemConfig.getString(typeName + ".name");
            if (Cls.C.ex(name, true) && name.length() > 0) {
                tsi.setName(name);
            } else {
                Msg.sendConMsgFalse(String.format("读取分类 %s 的name失败!", typeName));
                continue;
            }
            tsi.setItemId(itemConfig.getInt(typeName + ".itemId", 0));
            tsi.setItemName(itemConfig.getString(typeName + ".itemName", ""));
            tsi.setDataId((short) itemConfig.getInt(typeName + ".dataId", 0));
            tsi.setDesc(itemConfig.getStringList(typeName + ".desc"));
            tsi.setMonsterList(itemConfig.getStringList(typeName + ".monsterList"));
            int number = itemConfig.getInt(typeName + ".number", 0);
            if (number > 0 && number < 65) {
                tsi.setNumber(number);
            } else {
                Msg.sendConMsgFalse(String.format("读取分类 %s 的number失败!", typeName));
                continue;
            }
            int slot = itemConfig.getInt(typeName + ".slot", 0);
            if (slot > 0 && slot < 55) {
                tsi.setSlot(slot);
            } else {
                Msg.sendConMsgFalse(String.format("读取分类 %s 的slot失败!", typeName));
                continue;
            }
            tgiMap.put(typeName, tsi);
        }
    }

    public Map<String, tgi> getTgiMap() {
        return tgiMap;
    }

    public boolean isEnable() {
        return enable;
    }

    public void switchEnable() {
        this.enable = !this.enable;
        update();
    }

    private static final class ti implements tsi{

        private String name;
        private int itemId;
        private String itemName;
        private short dataId;
        private List<String> desc;
        private List<String> monsterList;
        private int number;
        private int slot;

        @Override
        public String getName() {
            return name;
        }

        @SuppressWarnings("deprecation")
        @Override
        public Material getMaterial() {
            return itemName.length() > 0 ? Material.getMaterial(itemName) : Material.getMaterial(itemId);
        }

        @Override
        public short getDataId() {
            return dataId;
        }

        @Override
        public List<String> getDesc() {
            return desc;
        }

        @Override
        public int getNumber() {
            return number;
        }

        @Override
        public int getSlot() {
            return slot;
        }

        @Override
        public List<String> getMonsterList() {
            return monsterList;
        }


        @Override
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public void setItemId(int itemId) {
            this.itemId = itemId;
        }

        @Override
        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        @Override
        public void setDataId(short dataId) {
            this.dataId = dataId;
        }

        @Override
        public void setDesc(List<String> desc) {
            this.desc = desc;
        }

        @Override
        public void setMonsterList(List<String> monsterList) {
            this.monsterList = monsterList;
        }

        @Override
        public void setNumber(int number) {
            this.number = number;
        }

        @Override
        public void setSlot(int slot) {
            this.slot = slot;
        }
    }

}
