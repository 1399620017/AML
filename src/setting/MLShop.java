package setting;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import top.aot.plugin.APlugin.AsxConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/8/12 8:16
 * @description：
 */
public class MLShop extends AsxConfig {

    private static MLShop bfShop;

    private static final Map<Integer, Commodity> map = new HashMap<>();

    public static MLShop getInstance() {
        return bfShop;
    }

    public static void reload() {
        bfShop = new MLShop("BFShopList");
    }

    public MLShop(String fileName) {
        super(fileName);
    }

    @Override
    protected void defaultValue() {

    }

    @Override
    protected void loadConfig(FileConfiguration config) {
        map.clear();
        for (int i = 0; i < 54; i++) {
            if (customConfig.contains(i + ".point") && customConfig.contains(i + ".itemStack")) {
                map.put(i, new Commodity(customConfig.getString(i + ".monsterId"),
                        customConfig.getInt(i + ".point"),
                        customConfig.getItemStack(i + ".itemStack")));
            }
        }
    }

    @Override
    protected void saveConfig(FileConfiguration config) {

    }

    public int addCommodity(Commodity commodity) {
        for (int i = 0; i < 54; i++) {
            if (!map.containsKey(i)) {
                map.put(i, commodity);
                customConfig.set(i + ".monsterId", commodity.getMonsterId());
                customConfig.set(i + ".point", commodity.getPoint());
                customConfig.set(i + ".itemStack", commodity.getItemStack());
                update();
                return i;
            }
        }
        return -1;
    }

    /**
     * 返回true是替换 返回false是添加
     * */
    public boolean setCommandity(int index, Commodity commodity) {
        boolean isReplace = false;
        if (map.containsKey(index)) {
            isReplace = true;
        }
        map.put(index, commodity);
        customConfig.set(index + ".monsterId", commodity.getMonsterId());
        customConfig.set(index + ".point", commodity.getPoint());
        customConfig.set(index + ".itemStack", commodity.getItemStack());
        update();
        return isReplace;
    }

    public boolean delCommandity(int index) {
        if (map.containsKey(index)) {
            customConfig.set(index + ".monsterId", null);
            customConfig.set(index + ".point", null);
            customConfig.set(index + ".itemStack", null);
            update();
            return true;
        }
        return false;
    }

    public void clearCommandity() {
        map.clear();
        for (int i = 0; i < 54; i++) {
            customConfig.set(i + ".monsterId", null);
            customConfig.set(i + ".point", null);
            customConfig.set(i + ".itemStack", null);
        }
        update();
    }

    public Map<Integer, Commodity> getMap() {
        return map;
    }

    public static class Commodity {
        private final String monsterId;
        private final int point;
        private final ItemStack itemStack;

        public Commodity(String monsterId, int point, ItemStack itemStack) {
            this.monsterId = monsterId;
            this.point = point;
            this.itemStack = itemStack.clone();
        }

        public int getPoint() {
            return point;
        }

        public ItemStack getItemStack() {
            return itemStack.clone();
        }

        public String getMonsterId() {
            return monsterId;
        }
    }
}
