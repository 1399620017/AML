package setting;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import top.aot.cls.Cls;
import top.aot.constant.IntegerConstant;
import top.aot.constant.StringConstant;
import top.aot.itf.Tgi;
import top.aot.itf.Tsi;
import top.aot.plugin.aml.APlugin.Msg;
import top.aot.plugin.aml.APlugin.AsxConfig;

import java.util.*;

/**
 * @author root
 */
public class GuiSetup extends AsxConfig {

    private boolean enable;

    public static GuiSetup table;

    public static void reload() {
        table = new GuiSetup();
    }

    private GuiSetup() {
        super(StringConstant.MONSTER_GUI_SETUP_FILE_NAME);
    }

    private Map<String, Tgi> tgiMap;

    @Override
    protected void defaultValue() {
        customConfig.set("setting.enable", true);
        customConfig.set("item.a.name", "§a这是怪物名字");
        customConfig.set("item.a.itemId", 397);
        customConfig.set("item.a.dataId", IntegerConstant.CONFIG_FIELD_VALUE_0);
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
        ConfigurationSection itemConfig = config.getConfigurationSection("item");
        Set<String> typeNameSet = itemConfig.getKeys(false);
        tgiMap = new HashMap<>((int) (typeNameSet.size() * 1.25 + 1));
        for (String typeName : typeNameSet) {
            Tsi tsi = new Ti();
            String name = itemConfig.getString(typeName + ".name");
            if (Cls.C.ex(name, true) && name.length() > 0) {
                tsi.setName(name);
            } else {
                Msg.sendConMsgFalse(String.format("读取分类 %s 的name失败!", typeName));
                continue;
            }
            tsi.setItemId(itemConfig.getInt(typeName + ".itemId", IntegerConstant.CONFIG_FIELD_VALUE_0));
            tsi.setItemName(itemConfig.getString(typeName + ".itemName", ""));
            tsi.setDataId((short) itemConfig.getInt(typeName + ".dataId", IntegerConstant.CONFIG_FIELD_VALUE_0));
            tsi.setDesc(itemConfig.getStringList(typeName + ".desc"));
            tsi.setMonsterList(itemConfig.getStringList(typeName + ".monsterList"));
            int number = itemConfig.getInt(typeName + ".number", IntegerConstant.CONFIG_FIELD_VALUE_0);
            if (number > 0 && number < 65) {
                tsi.setNumber(number);
            } else {
                Msg.sendConMsgFalse(String.format("读取分类 %s 的number失败!", typeName));
                continue;
            }
            int slot = itemConfig.getInt(typeName + ".slot", IntegerConstant.CONFIG_FIELD_VALUE_0);
            if (slot > 0 && slot < 55) {
                tsi.setSlot(slot);
            } else {
                Msg.sendConMsgFalse(String.format("读取分类 %s 的slot失败!", typeName));
                continue;
            }
            tgiMap.put(typeName, tsi);
        }
    }

    public Map<String, Tgi> getTgiMap() {
        return tgiMap;
    }

    public boolean isEnable() {
        return enable;
    }

    public void switchEnable() {
        this.enable = !this.enable;
        update();
    }

    /**
     * NEWGUI设置
     *
     * @author aoisa
     * @date 2021/2/24 9:11
     */
    private static final class Ti implements Tsi {

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
