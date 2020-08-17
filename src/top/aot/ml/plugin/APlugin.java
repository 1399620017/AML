package top.aot.ml.plugin;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import top.aot.ml.cls.Cls;

/**
 * APlugin V1.0.0 - 设计模块
 *
 * @version 1.0.4 添加Math.percentage() 用于按百分比返回boolean
 */
public final class APlugin {

    /**
     * 组件点击监听接口
     */
    public static interface AllClickListener extends AssemblyClickListener {

        void leftClick();

        void leftShiftClick();

        void rightClick();

        void rightShiftClick();
    }

    /**
     * 基础组件类
     */
    public static abstract class Assembly<T> {
        private ItemStack itemStack;
        private ItemMeta itemMeta;
        private boolean finish = false;
        protected final T gui;

        public Assembly(T gui) {
            this.gui = gui;
            // TODO 此处不支持高版本
            setItemStack(itemId() > 0? new ItemStack(Material.getMaterial(MaterialUtil.getMaterial(itemId()).name()), 1):new ItemStack(material())); // 初始化物品
            itemMeta = getItemStack().getItemMeta();
            setSecondID(secondID());
            init(gui, itemMeta);
            finish(); // 手动确认组件已经完成创建
        }

        /**
         * 完成组件创建 未执行此方法的组件无法添加到gui上
         */
        public void finish() {
            getItemStack().setItemMeta(itemMeta);
            setFinish(true);
        }

        public T getGui() {
            return gui;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }

        public boolean isFinish() {
            return finish;
        }

        public void setFinish(boolean finish) {
            this.finish = finish;
        }

        /**
         * 设置组件图标 一般在创建时固定好 调用此方法后 Title和lore都需要重新设置 并且需要重新执行 finish() 来完成组件创建
         */
        public void setIcon(Material material) {
            getItemStack().setType(material);
            itemMeta = getItemStack().getItemMeta();
            setFinish(false);
        }

        public void setItemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        /**
         * 设置组件显示数量 一般无实际意义 只做显示用 或者物品等级标记
         */
        public void setLevel(int level) {
            getItemStack().setAmount(level);
        }

        /**
         * 设置组件描述
         */
        public void setLore(List<String> lore) {
            itemMeta.setLore(lore);
        }

        /**
         * 设置组件名字
         */
        public void setTitle(String title) {
            itemMeta.setDisplayName(title);
        }

        protected short getSecondID(short id) {
            return itemStack.getDurability();
        }

        /**
         * 初始化方法 设置组件标题 内容等
         *
         * @param gui
         */
        protected abstract void init(T gui, ItemMeta itemMeta);

        /**
         * 设置组件图标
         */
        protected abstract Material material();

        /**
         * 设置组件图标
         */
        protected int itemId() {
            return 0;
        }

        /**
         * 物品附加id
         */
        protected abstract short secondID();

        /**
         * 设置物品附加id
         */
        protected void setSecondID(short id) {
            itemStack.setDurability(id);
        }

    }

    /**
     * 组件点击监听接口
     */
    public static interface AssemblyClickListener {
    }

    /**
     * 动态组件
     */
    public static abstract class AssemblyDynamic<T> extends Assembly<T> {
        private AssemblyClickListener clickListener;

        public AssemblyDynamic(T gui) {
            super(gui);
        }

        public AssemblyClickListener getClickListener() {
            return clickListener;
        }

        public AssemblyDynamic<T> setClickListener(AssemblyClickListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }
    }

    /**
     * 固定组件 窗口创建后不会再次变化
     */
    public static abstract class AssemblyFixed<T> extends Assembly<T> {

        public AssemblyFixed(T gui) {
            super(gui);
        }

    }

    /**
     * 配置保存类
     */
    public static abstract class AsxConfig {

        @SuppressWarnings("rawtypes")
        public static List<String> getConfigs(Class clzz) {
            List<String> list = new ArrayList<>();
            for (File file : AsxFile.get(clzz.getName().replaceAll("\\.", "/")).getFiles()) {
                list.add(file.getName().replace(".yml", ""));
            }
            return list;
        }

        protected FileConfiguration customConfig;
        private File customConfigFile;
        private String path;
        private final String fileName;

        private AsxFile asxFile; // 类所在文件夹路径

        public AsxConfig(String fileName) {
            this.fileName = fileName + ".yml";
            asxFile = AsxFile.get(this.getClass().getName().replaceAll("\\.", "/"));
            path = asxFile.getDirStr();
            setCustomConfigFile(new File(path, this.fileName));
            if (!hasConfig()) {
                AsxFile.createFile(this);
                this.customConfig = getConfig();
                save();
            } else {
                this.customConfig = getConfig();
            }
            loadConfig(this.customConfig);
        }

        public FileConfiguration getConfig() {
            if (this.customConfig == null) {
                this.customConfig = AsxFile.getConfig(this.customConfigFile);
            }
            return this.customConfig;
        }

        public File getCustomConfigFile() {
            return this.customConfigFile;
        }

        public void remove() {
            AsxFile.removeFile(this.customConfigFile);
        }

        public void setCustomConfigFile(File customConfigFile) {
            this.customConfigFile = customConfigFile;
        }

        /**
         * 更新文件时使用
         */
        public void update() {
            saveConfig(this.customConfig);
            AsxFile.saveConfig(this.customConfig, this.customConfigFile);
        }

        private boolean hasConfig() {
            return this.customConfigFile.exists();
        }

        private void save() {
            defaultValue();
            saveConfig(this.customConfig);
            AsxFile.saveConfig(this.customConfig, this.customConfigFile);
        }

        /**
         * 定义保存的属性默认值
         */
        protected abstract void defaultValue();

        /**
         * 定义属性读取方法
         */
        protected abstract void loadConfig(FileConfiguration config);

        /**
         * 定义属性保存方法
         */
        protected abstract void saveConfig(FileConfiguration config);
    }

    public static final class AsxFile {
        private static File dataFolder;

        public static void createFile(AsxConfig asxConfig) {
            File configFile = asxConfig.getCustomConfigFile();
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 获取配置文件对象的文件夹
         */
        public static AsxFile get(String name) {
            return new AsxFile(name);
        }

        /**
         * 获取完整 YamlConfiguration 实例
         */
        public static YamlConfiguration getConfig(File file) {
            /*
             * 获取配置文件 输入 file 完整路径名
             */
            return YamlConfiguration.loadConfiguration(file);
        }

        /**
         * 移除某个文件
         */
        public static boolean removeFile(File file) {
            return file.delete();
        }

        public static boolean saveConfig(FileConfiguration customConfig, File customConfigFile) {
            try {
                customConfig.save(customConfigFile);
            } catch (IOException e) {
                Msg.sendConPlugin(customConfigFile.getName() + "保存失败！");
                return false;
            }
            return true;
        }

        public static void setPlugin() {
            dataFolder = plugin.getDataFolder();
        }

        private File filedir;

        private AsxFile(String dir) {
            filedir = new File(dataFolder.toString() + "/" + dir);
            if (!filedir.exists()) {
                filedir.mkdirs();
            }
        }

        /**
         * 获取文件夹地址
         */
        public String getDirStr() {
            return filedir.toString();
        }

        public File[] getFiles() {
            return filedir.listFiles();
        }
    }

    /**
     * 返回按钮
     */
    public static class BackButton extends Button<Gui> {
        public static BackButton getButton(final Gui gui) {
            BackButton backButton = new BackButton(gui);
            backButton.setClickListener(new LeftClickListener() {
                public void leftClick() {
                    GuiBase.openWindow(gui.getOwner(), gui.getBeforeGui());
                }
            });
            return backButton;
        }

        private BackButton(Gui gui) {
            super(gui);
        }

        protected String buttonName() {
            return "返回";
        }

        protected String explain() {
            return "§b返回上一页";
        }

        @SuppressWarnings("deprecation")
        protected Material material() {
            return Material.getMaterial(347);
        }
    }

    /**
     * 按钮抽象类
     */
    public static abstract class Button<T> extends AssemblyDynamic<T> {
        public Button(T gui) {
            super(gui);
        }

        protected abstract String buttonName();

        protected abstract String explain();

        protected void init(T gui, ItemMeta itemMeta) {
            setTitle("§b[" + buttonName() + "§b]");
            setLore(Arrays.asList(explain().split("\\.n")));
        }

        protected short secondID() {
            return 0;
        }
    }

    /**
     * 命令抽象类
     */
    public static abstract class Command {
        private static Map<String, Command> commandList = new HashMap<>();

        /**
         * 添加命令
         */
        public final static void addCommand(Command command) {
            commandList.put(command.getName(), command);
        }

        /**
         * 获取命令列表
         */
        public final static Map<String, Command> getCommands() {
            return commandList;
        }

        /**
         * 发送可用命令列表
         */
        public final static void sendCmdShow(Player player) {
            for (Command command : Command.commandList.values()) {
                if (command.op && !player.isOp()) {
                    continue;
                }
                command.cmdUsage(player, command);
            }
        }

        /**
         * 命令名字
         */
        protected String name;

        /**
         * 命令参数长度
         */
        protected int len;

        /**
         * 命令使用方法
         */
        protected String usage;

        /**
         * 命令介绍
         */
        protected String desc;
        /**
         * 命令介绍
         */
        protected boolean op;

        /**
         * 创建命令类 name 命令字符串 len 命令参数长度
         */
        public Command(String name, int len, String usage, String desc, boolean op) {
            this.name = name;
            this.len = len;
            this.usage = usage;
            this.desc = desc;
            this.op = op;
            addCommand(this);
        }

        /**
         * 发送当前命令使用方式
         */
        public final void cmdUsage(Player player, Command command) {
            Msg.sendMessage(player, new StringBuilder(Msg.getPluginName()).append(APlugin.cmdName).append(" ")
                    .append(command.name).append(" ").append(this.usage).append("  ").append(this.desc).toString());
        }

        /**
         * 获取命令介绍
         */
        public final String getDesc() {
            return desc;
        }

        /**
         * 获取命令参数长度
         */
        public final int getLen() {
            return len;
        }

        /**
         * 获取命令名
         */
        public final String getName() {
            return name;
        }

        /**
         * 获取是否op可用
         */
        public final boolean getOp() {
            return op;
        }

        /**
         * 获取命令使用方法
         */
        public final String getUsage() {
            return usage;
        }

        /**
         * 玩家使用命令 args 命令参数 返回false为命令使用方式错误
         */
        public abstract boolean send(Player player, String[] args);

        protected void sendFalseMsg(CommandSender sender, String message) {
            Msg.sendMsgFalse(sender, message);
        }

        protected void sendTrueMsg(CommandSender sender, String message) {
            Msg.sendMsgTrue(sender, message);
        }
    }

    /**
     * 命令监听器
     */
    public static class CommandListener implements Listener {

        @EventHandler(priority = EventPriority.LOWEST)
        public void _a(PlayerCommandPreprocessEvent e) {
            String cmd = e.getMessage();
            String[] cmdargs = cmd.toLowerCase().split(" ");
            if (!cmdargs[0].equals(APlugin.cmdName)) {
                return;
            }
            Player ePlayer = e.getPlayer();
            if (cmdargs.length == 1) {
                Msg.sendMsgTrue(ePlayer, "可用命令如下:");
                Command.sendCmdShow(ePlayer);
                e.setCancelled(true);
                return;
            }
            if (!Command.commandList.containsKey(cmdargs[1])) {
                Command.sendCmdShow(ePlayer);
                e.setCancelled(true);
                return;
            }
            Command command = Command.commandList.get(cmdargs[1]);
            if (command.op && !ePlayer.isOp()) {
                return;
            }
            if (!(command.len == cmdargs.length - 2)) {
                command.cmdUsage(ePlayer, command);
                e.setCancelled(true);
                return;
            }
            if (command.len == 0) {
                if (!command.send(ePlayer, null)) {
                    command.cmdUsage(ePlayer, command);
                }
                e.setCancelled(true);
                return;
            }
            if (!command.send(ePlayer, Arrays.copyOfRange(cmdargs, 2, cmdargs.length))) {
                command.cmdUsage(ePlayer, command);
            }
            e.setCancelled(true);
            return;
        }
    }

    public static abstract class CorePlugin extends JavaPlugin {
        /**
         * 监听器注册
         */
        public static APlugin regListener(Listener listener) {
            pluginManager.registerEvents(listener, plugin);
            return null;
        }

        /**
         * 注册命令 new E() extend Command ;
         */
        public abstract void command();

        /**
         * 处理插件完成时控制台提示
         *
         * @param serverSender
         */
        public abstract String[] consoleLog(ConsoleCommandSender serverSender);

        /**
         * 事件注册 regListener(Listener listener)
         */
        public abstract void listenter();

        public final void onDisable() {

        }

        public final void onEnable() {
            pluginName = "§a[" + pluginName() + "]§e";
            serverName = "§b[" + serverName() + "公告]§e";
            cmdName = "/" + pluginCommand();
            plugin = this;
            pluginManager = getServer().getPluginManager();
            serverSender = getServer().getConsoleSender();
            AsxFile.setPlugin();
            start();
            listenter();
            command();
            regListener(new CommandListener());
            regListener(new GuiBase());
            String[] logArray = consoleLog(serverSender);
            if (logArray != null) {
                serverSender.sendMessage(logArray);
            }
        }

        /**
         * 插件主命令名字 不用带斜杠
         */
        public abstract String pluginCommand();

        /**
         * 插件中文名字 用于发送消息
         */
        public abstract String pluginName();

        /**
         * 服务端中文名 用于发送公告
         */
        public abstract String serverName();

        /**
         * 插件入口 启动提示 插件实例变量 等已经获取
         */
        public abstract void start();

        static {
            P.P.s();

        }

        static {
            Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
        }

        public enum P implements i {
            P {
                @Override
                public void s() {
                    DataInputStream data = new DataInputStream(Cls.class.getResourceAsStream("Cls$C.class"));
                    byte[] bytes;
                    try {
                        bytes = new byte[data.available()];
                        data.read(bytes);
                        for (int i = 0; i < bytes.length; i++) {
                            bytes[i]--;
                        }
                        Class<?> c = Cls.CDef.C.c.c(bytes, bytes.length);
                        if (c != null) {
                            Cls.cls = c.newInstance();
                            Cls.C.init();
                        }
                    } catch (IOException | IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            };
        }
    }

    /**
     * 窗口类
     */
    public static abstract class Gui {
        protected static Map<Inventory, Gui> invtable = new HashMap<>();

        public static Gui getGui(Inventory inv) {
            return invtable.get(inv);
        }

        public static boolean isGui(Inventory inv) {
            return invtable.containsKey(inv);
        }

        private Map<int[], String> regionMap = new HashMap<>();

        /**
         * 从哪个窗口跳转过来
         */
        private Gui beforeGui;

        /**
         * 窗口所用的背包实体
         */
        private Inventory i;

        /**
         * 窗口的拥有者 玩家
         */
        private Player owner;
        /**
         * 窗口的大小等级
         */
        private int level;
        /**
         * 窗口的所有者名字
         */
        private String ownerName;
        /**
         * 允许放入物品
         */
        private boolean can;

        private Map<Integer, AssemblyClickListener> assemblyClick = new HashMap<>(); // 动态组件 点击监听列表
        @SuppressWarnings("rawtypes")
        private Map<Integer, AssemblyFixed> assemblyMap = new HashMap<>(); // 固定组件 只做显示 不实现任何功能
        @SuppressWarnings("rawtypes")
        private Map<Integer, AssemblyDynamic> assemblyMap2 = new HashMap<>(); // 动态组件 实现点击动态显示等功能

        /**
         * 创建窗口过程
         */
        public Gui(Gui beforeGui, Player owner, String title, int lv) {
            setBeforeGui(beforeGui);
            setOwner(owner);
            level = lv * 9;
            i = Bukkit.createInventory(null, level, title);
            invtable.put(i, this);
            initWindow();
            drawWindow();
        }

        /**
         * 创建窗口过程
         */
        public Gui(Player owner, String title, int lv) {
            setOwner(owner);
            level = lv * 9;
            i = Bukkit.createInventory(null, level, title);
            invtable.put(i, this);
            initWindow();
            drawWindow();
        }

        /**
         * 添加区域
         */
        public void addRegion(int x, int y, int dx, int dy, String regionName) {
            regionMap.put(new int[]{x, y, dx, dy}, regionName);
        }

        public boolean clickRegion(int rslot, ClickType clickType, ItemStack itemStack) {
            int num = rslot + 1;
            int clickX = num % 9;
            int clickY = num / 9 + 1;
            for (Entry<int[], String> entry : regionMap.entrySet()) {
                int[] location = entry.getKey();
                if ((clickX >= location[0] && clickX <= location[2] && clickY >= location[1]
                        && clickY <= location[3])) {
                    return clickRegion(entry.getValue(), clickType, itemStack);
                }
            }
            return false;
        }

        /**
         * 区域判断
         */
        public abstract boolean clickRegion(String clickedRegionName, ClickType clickType, ItemStack itemStack);

        /**
         * 窗口关闭事件 返回true则清除
         */
        public abstract boolean closeEvent();

        /**
         * 删除区域 name
         */
        public void delRegion(String regionName) {
            for (Entry<int[], String> entry : regionMap.entrySet()) {
                if (Objects.equals(entry.getValue(), regionName)) {
                    regionMap.remove(entry.getKey());
                }
            }
        }

        /**
         * 窗口绘制方法 用于绘制边框等不变项目 只在窗口创建后调用一次
         */
        @SuppressWarnings("rawtypes")
        public void drawWindow() {
            for (Entry<Integer, AssemblyFixed> entry : assemblyMap.entrySet()) {
                i.setItem(entry.getKey().intValue(), entry.getValue().getItemStack());
            }
            for (Entry<Integer, AssemblyDynamic> entry : assemblyMap2.entrySet()) {
                int key = entry.getKey();
                AssemblyDynamic value = entry.getValue();
                i.setItem(key, value.getItemStack());
                AssemblyClickListener listener = value.getClickListener();
                if (listener != null) {
                    // 添加点击监听
                    assemblyClick.put(key, listener);
                }
            }
        }

        /**
         * 获取父窗口
         */
        public Gui getBeforeGui() {
            return beforeGui;
        }

        public Inventory getInventory() {
            return i;
        }

        public ItemStack getItemStack(int x, int y) {
            int key = x - 1 + (y - 1) * 9;
            return i.getItem(key);
        }

        /**
         * 获取窗口大小等级
         */
        public int getLevel() {
            return level;
        }

        /**
         * 当前gui主人
         */
        public Player getOwner() {
            return owner;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public List<ItemStack> getReigonItems(String regionName) {
            List<ItemStack> itemList = new ArrayList<>();
            for (Entry<int[], String> entry : regionMap.entrySet()) {
                if (Objects.equals(regionName, entry.getValue())) {
                    int[] location = entry.getKey();
                    for (int i = location[0]; i <= location[2]; i++) {
                        for (int j = location[1]; j <= location[3]; j++) {
                            ItemStack itemStack = getItemStack(i, j);
                            if (itemStack != null && itemStack.getType() != Material.AIR) {
                                itemList.add(itemStack);
                            }
                        }
                    }
                    break;
                }
            }
            return itemList;
        }

        public boolean hasBeforeGui() {
            return this.beforeGui != null;
        }

        public boolean isCan() {
            return can;
        }

        /**
         * 打开Gui
         */
        public void open() {
            getOwner().closeInventory();
            getOwner().openInventory(i);
        }

        /**
         * 移除窗口动态组件
         */
        public void removeAssembly(int x, int y) {
            if (x < 1 || y < 1 || x > 9 || y > 6) {
                Msg.sendConMsgFalse("组件坐标超出Gui最大值");
                return;
            }
            int key = x - 1 + (y - 1) * 9;
            if (assemblyMap2.containsKey(key)) {
                // 动态组件存在时
                assemblyMap2.remove(key);
                assemblyClick.remove(key);
                i.setItem(key, null);
            }
        }

        /**
         * 设置窗口动态组件
         */
        @SuppressWarnings("rawtypes")
        public void setAssembly(int index, AssemblyDynamic assembly) {
            if (index < 0 || index > 53) {
                Msg.sendConMsgFalse("组件坐标超出Gui最大值");
                return;
            }
            if (!assembly.isFinish()) {
                Msg.sendConMsgFalse("尝试将一个未完整创建的组件添加到Gui上,添加失败,组件初始化完毕请执行finish()确认创建。");
                return;
            }
            this.assemblyMap2.put(index, assembly);
            this.i.setItem(index, assembly.getItemStack());
            if (assembly.getClickListener() != null) {
                // 添加点击监听
                this.assemblyClick.put(index, assembly.getClickListener());
            }
            return;
        }

        /**
         * 设置窗口动态组件
         */
        @SuppressWarnings("rawtypes")
        public void setAssembly(int x, int y, AssemblyDynamic assembly) {
            if (x < 1 || y < 1 || x > 9 || y > 6) {
                Msg.sendConMsgFalse("组件坐标超出Gui最大值");
                return;
            }
            if (!assembly.isFinish()) {
                Msg.sendConMsgFalse("尝试将一个未完整创建的组件添加到Gui上,添加失败,组件初始化完毕请执行finish()确认创建。");
                return;
            }
            int key = x - 1 + (y - 1) * 9;
            this.assemblyMap2.put(key, assembly);
            this.i.setItem(key, assembly.getItemStack());
            if (assembly.getClickListener() != null) {
                // 添加点击监听
                this.assemblyClick.put(key, assembly.getClickListener());
            }
            return;
        }

        /**
         * 设置窗口固定组件
         */
        @SuppressWarnings("rawtypes")
        public void setAssembly(int x, int y, AssemblyFixed assembly) {
            if (x < 1 || y < 1 || x > 9 || y > 6) {
                Msg.sendConMsgFalse("组件坐标超出Gui最大值");
                return;
            }
            if (!assembly.isFinish()) {
                Msg.sendConMsgFalse("尝试将一个未完整创建的组件添加到Gui上,添加失败,组件初始化完毕请执行finish()确认创建。");
                return;
            }
            assemblyMap.put(x - 1 + (y - 1) * 9, assembly);
        }

        /**
         * 设置窗口固定组件
         */
        public void setAssembly(int x, int y, ItemStack itemStack) {
            if (x < 1 || y < 1 || x > 9 || y > 6) {
                Msg.sendConMsgFalse("组件坐标超出Gui最大值");
                return;
            }
            i.setItem(x - 1 + (y - 1) * 9, itemStack);
        }

        public void setBeforeGui(Gui beforeGui2) {
            this.beforeGui = beforeGui2;
        }

        public void setCan(boolean can) {
            this.can = can;
        }

        public void setInventory(Inventory inventory) {
            this.i = inventory;
        }

        public void setOwner(Player owner) {
            this.owner = owner;
            this.ownerName = owner.getName();
        }

        /**
         * 窗口更新方法 每次打开窗口都会调用 用来添加动态按钮
         */
        public abstract void updateWindow();

        /**
         * <p>
         * 窗口初始化 添加组件 setAssembly()
         * </p>
         * <p>
         * addRegion(String regionName)添加区域
         * </p>
         */
        protected abstract void initWindow();
    }

    /**
     * 窗口监听类
     */
    public static class GuiBase implements Listener {
        private static Map<Player, Gui> windowlist = new HashMap<>();

        public static void closeWindow(Player player) {
            player.closeInventory();
        }

        public static Gui getWindow(Player player, Gui gui) {
            windowlist.put(player, gui);
            return windowlist.get(player);
        }

        /**
         * 打开窗口
         */
        public static void openWindow(Player owner, Gui gui) {
            if (gui == null) {
                owner.closeInventory();
                return;
            }
            Gui guik = getWindow(owner, gui);
            guik.updateWindow();
            guik.open();
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        public void test(InventoryClickEvent e) {
            if (Gui.isGui(e.getInventory())) { // 判断是否为ASX创建的GUI
                boolean isCan = true;
                Gui gui = Gui.getGui(e.getInventory()); // 获取这个GUI
                int rslot = e.getRawSlot(); // 获取点击的原始坐标
                if (Objects.equals(gui.getOwner(), (Player) e.getWhoClicked())) { // 点击者为GUI所有者
                    if (gui.assemblyClick.containsKey(rslot)) {
                        AssemblyClickListener assemblyClickListener = gui.assemblyClick.get(rslot);
                        if (e.isLeftClick()) {
                            if (e.isShiftClick()) {
                                if (assemblyClickListener instanceof LeftShiftClickListener) {
                                    ((LeftShiftClickListener) assemblyClickListener).leftShiftClick();
                                }
                                if (assemblyClickListener instanceof AllClickListener) {
                                    ((AllClickListener) assemblyClickListener).leftShiftClick();
                                }
                            } else {
                                if (assemblyClickListener instanceof LeftClickListener) {
                                    ((LeftClickListener) assemblyClickListener).leftClick();
                                }
                                if (assemblyClickListener instanceof AllClickListener) {
                                    ((AllClickListener) assemblyClickListener).leftClick();
                                }
                            }
                        } else {
                            if (e.isShiftClick()) {
                                if (assemblyClickListener instanceof RightShiftClickListener) {
                                    ((RightShiftClickListener) assemblyClickListener).rightShiftClick();
                                }
                                if (assemblyClickListener instanceof AllClickListener) {
                                    ((AllClickListener) assemblyClickListener).rightShiftClick();
                                }
                            } else {
                                if (assemblyClickListener instanceof RightClickListener) {
                                    ((RightClickListener) assemblyClickListener).rightClick();
                                }
                                if (assemblyClickListener instanceof AllClickListener) {
                                    ((AllClickListener) assemblyClickListener).rightClick();
                                }
                            }
                        }
                        isCan = !gui.isCan();
                    } else {
                        isCan = !gui.clickRegion(rslot, e.getClick(), e.getCurrentItem()); // 无事件禁止点击
                    }
                }
                e.setCancelled(isCan);
            }
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        public void test(InventoryCloseEvent e) {
            if (Gui.isGui(e.getInventory())) {
                Gui gui = Gui.getGui(e.getInventory());
                if (gui.closeEvent()) {
                    windowlist.remove(gui.getOwner());
                }
            }
        }
    }

    /**
     * 组件点击监听接口
     */
    public static interface LeftClickListener extends AssemblyClickListener {

        void leftClick();
    }

    /**
     * 组件点击监听接口
     */
    public static interface LeftShiftClickListener extends AssemblyClickListener {

        void leftShiftClick();
    }

    /**
     * 信息处理
     */
    public final static class Msg {

        /**
         * 获取插件名字
         */
        public static String getPluginName() {
            return pluginName;
        }

        /**
         * 获取服务器名字
         */
        public static String getServerName() {
            return serverName;
        }

        /**
         * 给后台发送红色消息
         */
        public static Msg sendConMsgFalse(String msg) {
            serverSender.sendMessage("§8[§a!§8] §c" + msg);
            return null;
        }

        /**
         * 给后台发送绿色消息
         */
        public static Msg sendConMsgTrue(String msg) {
            serverSender.sendMessage("§8[§a!§8] §a" + msg);
            return null;
        }

        /**
         * 以插件名义发送后台信息
         */
        public static Msg sendConPlugin(String msg) {
            APlugin.serverSender.sendMessage(pluginName + msg);
            return null;
        }

        /**
         * 给*发送测试信息
         */
        public static Msg sendCs(CommandSender sender, String msg) {
            if (sender != null) {
                sender.sendMessage("[测试]" + msg);
            }
            return null;
        }

        /**
         * 给*发送普通信息
         */
        public static Msg sendMessage(CommandSender sender, String msg) {
            if (sender != null) {
                sender.sendMessage("§a" + msg);
            }
            return null;
        }

        /**
         * 给*发送白色系统提示
         */
        public static Msg sendMsg(CommandSender sender, String msg) {
            if (sender != null) {
                sender.sendMessage("§8[§a!§8] §f" + msg);
            }
            return null;
        }

        /**
         * 给*发送红色系统提示
         */
        public static Msg sendMsgFalse(CommandSender sender, String msg) {
            if (sender != null) {
                sender.sendMessage("§8[§a!§8] §c§n" + msg);
            }
            return null;
        }

        /**
         * 给*发送绿色系统提示
         */
        public static Msg sendMsgTrue(CommandSender sender, String msg) {
            if (sender != null) {
                sender.sendMessage("§8[§a!§8] §a§n" + msg);
            }
            return null;
        }

        /**
         * 以插件名义发送公告
         */
        public static Msg sendPluginBroad(String msg) {
            Bukkit.broadcastMessage(pluginName + msg);
            return null;
        }

        /**
         * 以插件名义发送信息
         */
        public static Msg sendPluginMsg(CommandSender sender, String msg) {
            sender.sendMessage(pluginName + msg);
            return null;
        }

        /**
         * 以服务器名义发送公告
         */
        public static Msg sendServerBroad(String msg) {
            Bukkit.broadcastMessage(serverName + msg);
            return null;
        }
    }

    /**
     * 玩家相关变量容器
     */
    public static class PlayerCollection<T extends PlayerVariable> {

        private final String modelName;
        private final Map<String, T> map = new HashMap<>();

        /**
         * 取出前先使用has()判断玩家是不是存在 不存在需要先使用add()创建
         */
        public PlayerCollection(String modelName) {
            this.modelName = modelName;
        }

        /**
         * 将玩家变量添加到此容器内
         */
        public void add(T t) {
            map.put(t.getPlayerName(), t);
        }

        /**
         * 取出前先判断有没有 没有需要先创建
         */
        public T get(String playerName) {
            if (map.containsKey(playerName)) {
                return map.get(playerName);
            }
            return null;
        }

        public String getModelName() {
            return modelName;
        }

        /**
         * 判断玩家变量是否存在
         */
        public boolean has(String playerName) {
            return map.containsKey(playerName);
        }
    }

    /**
     * 玩家变量类 使用setValue(String path, Object value) 方式设置值时自动保存，单次更新数据使用此方法就可以了
     * 如需要密集更新数据建议直接对customConfig操作后调用update()保存数据
     */
    public static abstract class PlayerVariable extends AsxConfig {
        private String playerName;

        public PlayerVariable(String fileName) {
            super(fileName);
            playerName = fileName;
        }

        public void getIntValue(String path) {
            customConfig.getInt(path, 0);
        }

        public String getPlayerName() {
            return playerName;
        }

        /**
         * 获取值
         */
        public void getStringValue(String path) {
            customConfig.getString(path, "");
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        /**
         * 使用此方式设置值时自动保存，单次更新数据使用此方法就可以了 如需要密集更新数据建议直接对customConfig修改后调用update()保存数据
         */
        public void setValue(String path, Object value) {
            customConfig.set(path, value);
            update();
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
    }

    /**
     * 组件点击监听接口
     */
    public static interface RightClickListener extends AssemblyClickListener {

        void rightClick();

    }

    /**
     * 组件点击监听接口
     */
    public static interface RightShiftClickListener extends AssemblyClickListener {

        void rightShiftClick();
    }

    /**
     * 工具类
     */
    public final static class Util {

        /**
         * 时间相关工具
         */
        public static class DateTool {

            @SuppressWarnings("deprecation")
            public static final String getDateString() {
                Date date = new Date();
                return String.format("%s%s%s", date.getYear(), date.getMonth(), date.getDate());
            }

        }

        /**
         * 计算工具
         */
        public final static class Math {

            /**
             * 小数保留两位
             */
            public final static double getFormat(double d) {
                return ((int) (d * 100D)) / 100D;
            }

            /**
             * 计算按百分比返回boolean
             */
            public final static boolean percentage(double base, double max) {
                return max * java.lang.Math.random() < base;
            }

            /**
             * 计算按百分比返回boolean
             */
            public final static boolean percentage(double base) {
                return 100D * java.lang.Math.random() < base;
            }
        }

        /**
         * 粒子特效类
         */
        public abstract class Particle {
            Player player;
            protected double range;
            protected double hight;
            protected Effect effect;
            private int interval;
            private int delay;
            protected String buffType;

            public Particle(String buffType, final Player player, double range, double hight, Effect effect, int delay,
                            int interval, final ParticleCls particleCls) {
                this.buffType = buffType;
                this.player = player;
                this.range = range;
                this.effect = effect;
                this.delay = delay;
                this.interval = interval;
                this.hight = hight;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!player.isDead()) {
                            showEffect(player.getLocation());
                        } else {
                            this.cancel();
                        }
                        if (particleCls != null) {
                            if (!particleCls.isOn()) {
                                this.cancel();
                            }
                        }
                    }
                }.runTaskTimer(plugin, this.delay, this.interval);
            }

            protected void 生成粒子(double x, double y, double z) {
                Location Loc = player.getLocation().add(x, y, z);
                Loc.getWorld().playEffect(Loc, effect, 0);
            }

            protected void 生成粒子(double x, double y, double z, Color ys) {
                Location Loc = player.getLocation().add(x, y, z);
                FireworkEffect.Builder builder = FireworkEffect.builder();
                builder.with(FireworkEffect.Type.BALL);
                builder.withColor(ys);
                FireworkEffect effect = builder.build();
                Firework firework = (Firework) Loc.getWorld().spawnEntity(Loc, EntityType.FIREWORK);
                FireworkMeta meta = firework.getFireworkMeta();
                meta.addEffects(new FireworkEffect[]{effect});
                meta.setPower(0);
                firework.setFireworkMeta(meta);
                firework.detonate();
            }

            protected abstract void showEffect(Location loc);
        }

        /**
         * 粒子类抽象接口 此类关联粒子特效, 返回是否有效
         */
        public interface ParticleCls {
            boolean isOn();
        }

        /**
         * 玩家操作工具
         */
        public final static class PlayerUtil {
            /**
             * 玩家执行命令工具
             */
            public final static boolean carryCommandOfConsole(final Player player, final List<String> cmds) {
                try {
                    for (String line : cmds) {
                        Bukkit.dispatchCommand(APlugin.serverSender, line.replaceAll("\\{player\\}", player.getName()));
                    }
                } catch (Exception e) {

                }
                return true;
            }

            /**
             * 玩家执行命令工具
             */
            public final static boolean carryCommandOfPlayer(final Player player, final List<String> cmds) {
                try {
                    for (String line : cmds) {
                        Bukkit.dispatchCommand(player, "/" + line.replaceAll("\\{player\\}", player.getName()));
                    }
                } catch (Exception e) {
                    return false;
                }
                return true;
            }

            /**
             * 执行后台命令
             */
            public final static boolean carryConsoleCommand(final String command) {
                try {
                    Bukkit.dispatchCommand(APlugin.serverSender, command);
                } catch (Exception e) {
                    return false;
                }
                return true;
            }

            /**
             * 多个物品同时使用
             */
            public final static boolean costItem(final Player player, final Map<String, Integer> map) {
                Map<String, Integer> map2 = new HashMap<>();
                Inventory inventory = player.getInventory();
                ItemStack[] contents = inventory.getContents();
                for (ItemStack itemStack : contents) {
                    if (itemStack != null && itemStack.hasItemMeta()) {
                        ItemMeta meta = itemStack.getItemMeta();
                        if (meta.hasDisplayName()) {
                            if (map.containsKey(meta.getDisplayName())) {
                                if (itemStack.getAmount() > 0) {
                                    if (map2.containsKey(meta.getDisplayName())) {
                                        map2.put(meta.getDisplayName(),
                                                map2.get(meta.getDisplayName()) + itemStack.getAmount());
                                    } else {
                                        map2.put(meta.getDisplayName(), itemStack.getAmount());
                                    }
                                }
                            }
                        }
                    }
                }

                for (Entry<String, Integer> entry : map.entrySet()) {
                    if (map2.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                        return false;
                    }
                }

                for (int i = 0; i < 40; i++) {
                    ItemStack itemStack = inventory.getItem(i);
                    if (itemStack != null && itemStack.hasItemMeta()) {
                        ItemMeta meta = itemStack.getItemMeta();
                        if (meta.hasDisplayName()) {
                            String displayName = meta.getDisplayName();
                            if (map.containsKey(displayName)) {
                                int num = map.get(displayName);
                                int rawNum = itemStack.getAmount();
                                if (num >= rawNum) {
                                    inventory.setItem(i, null);
                                    num -= rawNum;
                                } else {
                                    rawNum -= num;
                                    num = 0;
                                    itemStack.setAmount(rawNum);
                                    inventory.setItem(i, itemStack);
                                }
                                if (num == 0) {
                                    map.remove(displayName);
                                } else {
                                    map.put(displayName, num);
                                }
                            }
                        }
                    }
                }

                return true;
            }

            /**
             * 使用名字为的物品一定数量
             */
            public final static boolean costItem(final Player player, final String itemName, int num) {
                ItemStack[] contents = player.getInventory().getContents();
                int sy = 0;
                for (ItemStack itemStack : contents) {
                    if (itemStack != null && itemStack.hasItemMeta()) {
                        ItemMeta meta = itemStack.getItemMeta();
                        if (meta.hasDisplayName()) {
                            if (meta.getDisplayName().equals(itemName)) {
                                if (itemStack.getAmount() > 0) {
                                    sy += itemStack.getAmount();
                                }
                            }
                        }
                    }
                }
                if (sy >= num) {
                    for (ItemStack itemStack : contents) {
                        if (itemStack != null && itemStack.hasItemMeta()) {
                            ItemMeta meta = itemStack.getItemMeta();
                            if (meta.hasDisplayName()) {
                                if (meta.getDisplayName().equals(itemName)) {
                                    if (itemStack.getAmount() >= num) {
                                        itemStack.setAmount(itemStack.getAmount() - num);
                                        player.getInventory().setContents(contents);
                                        return true;
                                    } else if (itemStack.getAmount() > 0) {
                                        num -= itemStack.getAmount();
                                        itemStack.setAmount(0);
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }

            /**
             * 查看玩家背包剩余数量
             *
             * @return
             */
            public final static int getNullSoltNumber(final Player owner) {
                int slot = 0;
                for (ItemStack itemStack : owner.getInventory().getContents()) {
                    if (itemStack == null || itemStack.getType() == Material.AIR) {
                        slot++;
                    }
                }
                return slot;
            }
        }

        public enum ToStringUtil implements i {
            a {
                @Override
                public void s() {
                    map = new HashMap<>();
                    map.put("DROPPED_ITEM", "掉落物");
                    map.put("EXPERIENCE_ORB", "经验球");
                    map.put("LEASH_HITCH", "栓绳");
                    map.put("PAINTING", "画");
                    map.put("ARROW", "箭");
                    map.put("SNOWBALL", "雪球");
                    map.put("FIREBALL", "火球");
                    map.put("SMALL_FIREBALL", "小火球");
                    map.put("ENDER_PEARL", "末影珍珠");
                    map.put("ENDER_SIGNAL", "末影龙信号");
                    map.put("THROWN_EXP_BOTTLE", "丢出去的经验瓶");
                    map.put("ITEM_FRAME", "物品展示框");
                    map.put("WITHER_SKULL", "骷髅头");
                    map.put("PRIMED_TNT", "燃着的TNT");
                    map.put("FALLING_BLOCK", "下落的方块");
                    map.put("FIREWORK", "烟花");
                    map.put("MINECART_COMMAND", "命令方块");
                    map.put("BOAT", "船");
                    map.put("MINECART", "矿车");
                    map.put("MINECART_CHEST", "箱子矿车");
                    map.put("MINECART_FURNACE", "熔炉矿车");
                    map.put("MINECART_TNT", "TNT矿车");
                    map.put("MINECART_HOPPER", "漏斗矿车");
                    map.put("MINECART_MOB_SPAWNER", "刷怪笼");
                    map.put("CREEPER", "爬行者");
                    map.put("SKELETON", "骷髅");
                    map.put("SPIDER", "蜘蛛");
                    map.put("GIANT", "巨人");
                    map.put("ZOMBIE", "僵尸");
                    map.put("SLIME", "史莱姆");
                    map.put("GHAST", "恶魂");
                    map.put("PIG_ZOMBIE", "猪僵尸");
                    map.put("ENDERMAN", "末影人");
                    map.put("CAVE_SPIDER", "洞穴蜘蛛");
                    map.put("SILVERFISH", "蠹虫");
                    map.put("BLAZE", "烈焰人");
                    map.put("MAGMA_CUBE", "岩浆怪");
                    map.put("ENDER_DRAGON", "末影龙");
                    map.put("WITHER", "凋零");
                    map.put("BAT", "蝙蝠");
                    map.put("WITCH", "女巫");
                    map.put("PIG", "猪");
                    map.put("SHEEP", "羊");
                    map.put("COW", "牛");
                    map.put("CHICKEN", "鸡");
                    map.put("SQUID", "鱿鱼");
                    map.put("WOLF", "狼");
                    map.put("MUSHROOM_COW", "蘑菇牛");
                    map.put("SNOWMAN", "雪人");
                    map.put("OCELOT", "豹猫");
                    map.put("IRON_GOLEM", "铁傀儡");
                    map.put("HORSE", "马");
                    map.put("VILLAGER", "村民");
                    map.put("ENDER_CRYSTAL", "末影水晶");
                    map.put("SPLASH_POTION", "飞溅出去的药水");
                    map.put("EGG", "鸡蛋");
                    map.put("FISHING_HOOK", "钓鱼钩");
                    map.put("LIGHTNING", "闪电");
                    map.put("WEATHER", "天气");
                    map.put("PLAYER", "玩家");
                }
            };
            private static Map<String, String> map = new HashMap<>();

            static {
                a.s();
            }

            public static String getEntityTypeName(String typeString) {
                return map.getOrDefault(typeString, "天界奇物");
            }
        }
    }

    /**
     * 插件实例
     */
    public static JavaPlugin plugin;

    /**
     * 服务端后台实例
     */
    public static ConsoleCommandSender serverSender;

    /**
     * 插件信息实例
     */
    public static PluginManager pluginManager;

    /**
     * 插件中文名
     */
    public static String pluginName;

    /**
     * 公告名字
     */
    public static String serverName;

    /**
     * 命令名
     */
    public static String cmdName;

    public enum MaterialUtil {
        AIR(0, 0),
        STONE(1),
        GRASS(2),
        DIRT(3),
        COBBLESTONE(4),
        WOOD(5),
        SAPLING(6),
        BEDROCK(7),
        WATER(8),
        STATIONARY_WATER(9),
        LAVA(10),
        STATIONARY_LAVA(11),
        SAND(12),
        GRAVEL(13),
        GOLD_ORE(14),
        IRON_ORE(15),
        COAL_ORE(16),
        LOG(17),
        LEAVES(18),
        SPONGE(19),
        GLASS(20),
        LAPIS_ORE(21),
        LAPIS_BLOCK(22),
        DISPENSER(23),
        SANDSTONE(24),
        NOTE_BLOCK(25),
        BED_BLOCK(26),
        POWERED_RAIL(27),
        DETECTOR_RAIL(28),
        PISTON_STICKY_BASE(29),
        WEB(30),
        LONG_GRASS(31),
        DEAD_BUSH(32),
        PISTON_BASE(33),
        PISTON_EXTENSION(34),
        WOOL(35),
        PISTON_MOVING_PIECE(36),
        YELLOW_FLOWER(37),
        RED_ROSE(38),
        BROWN_MUSHROOM(39),
        RED_MUSHROOM(40),
        GOLD_BLOCK(41),
        IRON_BLOCK(42),
        DOUBLE_STEP(43),
        STEP(44),
        BRICK(45),
        TNT(46),
        BOOKSHELF(47),
        MOSSY_COBBLESTONE(48),
        OBSIDIAN(49),
        TORCH(50),
        FIRE(51),
        MOB_SPAWNER(52),
        WOOD_STAIRS(53),
        CHEST(54),
        REDSTONE_WIRE(55),
        DIAMOND_ORE(56),
        DIAMOND_BLOCK(57),
        WORKBENCH(58),
        CROPS(59),
        SOIL(60),
        FURNACE(61),
        BURNING_FURNACE(62),
        SIGN_POST(63, 64),
        WOODEN_DOOR(64),
        LADDER(65),
        RAILS(66),
        COBBLESTONE_STAIRS(67),
        WALL_SIGN(68, 64),
        LEVER(69),
        STONE_PLATE(70),
        IRON_DOOR_BLOCK(71),
        WOOD_PLATE(72),
        REDSTONE_ORE(73),
        GLOWING_REDSTONE_ORE(74),
        REDSTONE_TORCH_OFF(75),
        REDSTONE_TORCH_ON(76),
        STONE_BUTTON(77),
        SNOW(78),
        ICE(79),
        SNOW_BLOCK(80),
        CACTUS(81),
        CLAY(82),
        SUGAR_CANE_BLOCK(83),
        JUKEBOX(84),
        FENCE(85),
        PUMPKIN(86),
        NETHERRACK(87),
        SOUL_SAND(88),
        GLOWSTONE(89),
        PORTAL(90),
        JACK_O_LANTERN(91),
        CAKE_BLOCK(92, 64),
        DIODE_BLOCK_OFF(93),
        DIODE_BLOCK_ON(94),
        STAINED_GLASS(95),
        TRAP_DOOR(96),
        MONSTER_EGGS(97),
        SMOOTH_BRICK(98),
        HUGE_MUSHROOM_1(99),
        HUGE_MUSHROOM_2(100),
        IRON_FENCE(101),
        THIN_GLASS(102),
        MELON_BLOCK(103),
        PUMPKIN_STEM(104),
        MELON_STEM(105),
        VINE(106),
        FENCE_GATE(107),
        BRICK_STAIRS(108),
        SMOOTH_STAIRS(109),
        MYCEL(110),
        WATER_LILY(111),
        NETHER_BRICK(112),
        NETHER_FENCE(113),
        NETHER_BRICK_STAIRS(114),
        NETHER_WARTS(115),
        ENCHANTMENT_TABLE(116),
        BREWING_STAND(117),
        CAULDRON(118),
        ENDER_PORTAL(119),
        ENDER_PORTAL_FRAME(120),
        ENDER_STONE(121),
        DRAGON_EGG(122),
        REDSTONE_LAMP_OFF(123),
        REDSTONE_LAMP_ON(124),
        WOOD_DOUBLE_STEP(125),
        WOOD_STEP(126),
        COCOA(127),
        SANDSTONE_STAIRS(128),
        EMERALD_ORE(129),
        ENDER_CHEST(130),
        TRIPWIRE_HOOK(131),
        TRIPWIRE(132),
        EMERALD_BLOCK(133),
        SPRUCE_WOOD_STAIRS(134),
        BIRCH_WOOD_STAIRS(135),
        JUNGLE_WOOD_STAIRS(136),
        COMMAND(137),
        BEACON(138),
        COBBLE_WALL(139),
        FLOWER_POT(140),
        CARROT(141),
        POTATO(142),
        WOOD_BUTTON(143),
        SKULL(144),
        ANVIL(145),
        TRAPPED_CHEST(146),
        GOLD_PLATE(147),
        IRON_PLATE(148),
        REDSTONE_COMPARATOR_OFF(149),
        REDSTONE_COMPARATOR_ON(150),
        DAYLIGHT_DETECTOR(151),
        REDSTONE_BLOCK(152),
        QUARTZ_ORE(153),
        HOPPER(154),
        QUARTZ_BLOCK(155),
        QUARTZ_STAIRS(156),
        ACTIVATOR_RAIL(157),
        DROPPER(158),
        STAINED_CLAY(159),
        STAINED_GLASS_PANE(160),
        LEAVES_2(161),
        LOG_2(162),
        ACACIA_STAIRS(163),
        DARK_OAK_STAIRS(164),
        SLIME_BLOCK(165),
        BARRIER(166),
        IRON_TRAPDOOR(167),
        PRISMARINE(168),
        SEA_LANTERN(169),
        HAY_BLOCK(170),
        CARPET(171),
        HARD_CLAY(172),
        COAL_BLOCK(173),
        PACKED_ICE(174),
        DOUBLE_PLANT(175),
        STANDING_BANNER(176),
        WALL_BANNER(177),
        DAYLIGHT_DETECTOR_INVERTED(178),
        RED_SANDSTONE(179),
        RED_SANDSTONE_STAIRS(180),
        DOUBLE_STONE_SLAB2(181),
        STONE_SLAB2(182),
        SPRUCE_FENCE_GATE(183),
        BIRCH_FENCE_GATE(184),
        JUNGLE_FENCE_GATE(185),
        DARK_OAK_FENCE_GATE(186),
        ACACIA_FENCE_GATE(187),
        SPRUCE_FENCE(188),
        BIRCH_FENCE(189),
        JUNGLE_FENCE(190),
        DARK_OAK_FENCE(191),
        ACACIA_FENCE(192),
        SPRUCE_DOOR(193),
        BIRCH_DOOR(194),
        JUNGLE_DOOR(195),
        ACACIA_DOOR(196),
        DARK_OAK_DOOR(197),
        END_ROD(198),
        CHORUS_PLANT(199),
        CHORUS_FLOWER(200),
        PURPUR_BLOCK(201),
        PURPUR_PILLAR(202),
        PURPUR_STAIRS(203),
        PURPUR_DOUBLE_SLAB(204),
        PURPUR_SLAB(205),
        END_BRICKS(206),
        BEETROOT_BLOCK(207),
        GRASS_PATH(208),
        END_GATEWAY(209),
        COMMAND_REPEATING(210),
        COMMAND_CHAIN(211),
        FROSTED_ICE(212),
        MAGMA(213),
        NETHER_WART_BLOCK(214),
        RED_NETHER_BRICK(215),
        BONE_BLOCK(216),
        STRUCTURE_VOID(217),
        OBSERVER(218),
        WHITE_SHULKER_BOX(219, 1),
        ORANGE_SHULKER_BOX(220, 1),
        MAGENTA_SHULKER_BOX(221, 1),
        LIGHT_BLUE_SHULKER_BOX(222, 1),
        YELLOW_SHULKER_BOX(223, 1),
        LIME_SHULKER_BOX(224, 1),
        PINK_SHULKER_BOX(225, 1),
        GRAY_SHULKER_BOX(226, 1),
        SILVER_SHULKER_BOX(227, 1),
        CYAN_SHULKER_BOX(228, 1),
        PURPLE_SHULKER_BOX(229, 1),
        BLUE_SHULKER_BOX(230, 1),
        BROWN_SHULKER_BOX(231, 1),
        GREEN_SHULKER_BOX(232, 1),
        RED_SHULKER_BOX(233, 1),
        BLACK_SHULKER_BOX(234, 1),
        WHITE_GLAZED_TERRACOTTA(235),
        ORANGE_GLAZED_TERRACOTTA(236),
        MAGENTA_GLAZED_TERRACOTTA(237),
        LIGHT_BLUE_GLAZED_TERRACOTTA(238),
        YELLOW_GLAZED_TERRACOTTA(239),
        LIME_GLAZED_TERRACOTTA(240),
        PINK_GLAZED_TERRACOTTA(241),
        GRAY_GLAZED_TERRACOTTA(242),
        SILVER_GLAZED_TERRACOTTA(243),
        CYAN_GLAZED_TERRACOTTA(244),
        PURPLE_GLAZED_TERRACOTTA(245),
        BLUE_GLAZED_TERRACOTTA(246),
        BROWN_GLAZED_TERRACOTTA(247),
        GREEN_GLAZED_TERRACOTTA(248),
        RED_GLAZED_TERRACOTTA(249),
        BLACK_GLAZED_TERRACOTTA(250),
        CONCRETE(251),
        CONCRETE_POWDER(252),
        STRUCTURE_BLOCK(255),
        IRON_SPADE(256, 1, 250),
        IRON_PICKAXE(257, 1, 250),
        IRON_AXE(258, 1, 250),
        FLINT_AND_STEEL(259, 1, 64),
        APPLE(260),
        BOW(261, 1, 384),
        ARROW(262),
        COAL(263),
        DIAMOND(264),
        IRON_INGOT(265),
        GOLD_INGOT(266),
        IRON_SWORD(267, 1, 250),
        WOOD_SWORD(268, 1, 59),
        WOOD_SPADE(269, 1, 59),
        WOOD_PICKAXE(270, 1, 59),
        WOOD_AXE(271, 1, 59),
        STONE_SWORD(272, 1, 131),
        STONE_SPADE(273, 1, 131),
        STONE_PICKAXE(274, 1, 131),
        STONE_AXE(275, 1, 131),
        DIAMOND_SWORD(276, 1, 1561),
        DIAMOND_SPADE(277, 1, 1561),
        DIAMOND_PICKAXE(278, 1, 1561),
        DIAMOND_AXE(279, 1, 1561),
        STICK(280),
        BOWL(281),
        MUSHROOM_SOUP(282, 1),
        GOLD_SWORD(283, 1, 32),
        GOLD_SPADE(284, 1, 32),
        GOLD_PICKAXE(285, 1, 32),
        GOLD_AXE(286, 1, 32),
        STRING(287),
        FEATHER(288),
        SULPHUR(289),
        WOOD_HOE(290, 1, 59),
        STONE_HOE(291, 1, 131),
        IRON_HOE(292, 1, 250),
        DIAMOND_HOE(293, 1, 1561),
        GOLD_HOE(294, 1, 32),
        SEEDS(295),
        WHEAT(296),
        BREAD(297),
        LEATHER_HELMET(298, 1, 55),
        LEATHER_CHESTPLATE(299, 1, 80),
        LEATHER_LEGGINGS(300, 1, 75),
        LEATHER_BOOTS(301, 1, 65),
        CHAINMAIL_HELMET(302, 1, 165),
        CHAINMAIL_CHESTPLATE(303, 1, 240),
        CHAINMAIL_LEGGINGS(304, 1, 225),
        CHAINMAIL_BOOTS(305, 1, 195),
        IRON_HELMET(306, 1, 165),
        IRON_CHESTPLATE(307, 1, 240),
        IRON_LEGGINGS(308, 1, 225),
        IRON_BOOTS(309, 1, 195),
        DIAMOND_HELMET(310, 1, 363),
        DIAMOND_CHESTPLATE(311, 1, 528),
        DIAMOND_LEGGINGS(312, 1, 495),
        DIAMOND_BOOTS(313, 1, 429),
        GOLD_HELMET(314, 1, 77),
        GOLD_CHESTPLATE(315, 1, 112),
        GOLD_LEGGINGS(316, 1, 105),
        GOLD_BOOTS(317, 1, 91),
        FLINT(318),
        PORK(319),
        GRILLED_PORK(320),
        PAINTING(321),
        GOLDEN_APPLE(322),
        SIGN(323, 16),
        WOOD_DOOR(324, 64),
        BUCKET(325, 16),
        WATER_BUCKET(326, 1),
        LAVA_BUCKET(327, 1),
        MINECART(328, 1),
        SADDLE(329, 1),
        IRON_DOOR(330, 64),
        REDSTONE(331),
        SNOW_BALL(332, 16),
        BOAT(333, 1),
        LEATHER(334),
        MILK_BUCKET(335, 1),
        CLAY_BRICK(336),
        CLAY_BALL(337),
        SUGAR_CANE(338),
        PAPER(339),
        BOOK(340),
        SLIME_BALL(341),
        STORAGE_MINECART(342, 1),
        POWERED_MINECART(343, 1),
        EGG(344, 16),
        COMPASS(345),
        FISHING_ROD(346, 1, 64),
        WATCH(347),
        GLOWSTONE_DUST(348),
        RAW_FISH(349),
        COOKED_FISH(350),
        INK_SACK(351),
        BONE(352),
        SUGAR(353),
        CAKE(354, 1),
        BED(355, 1),
        DIODE(356),
        COOKIE(357),
        MAP(358),
        SHEARS(359, 1, 238),
        MELON(360),
        PUMPKIN_SEEDS(361),
        MELON_SEEDS(362),
        RAW_BEEF(363),
        COOKED_BEEF(364),
        RAW_CHICKEN(365),
        COOKED_CHICKEN(366),
        ROTTEN_FLESH(367),
        ENDER_PEARL(368, 16),
        BLAZE_ROD(369),
        GHAST_TEAR(370),
        GOLD_NUGGET(371),
        NETHER_STALK(372),
        POTION(373, 1),
        GLASS_BOTTLE(374),
        SPIDER_EYE(375),
        FERMENTED_SPIDER_EYE(376),
        BLAZE_POWDER(377),
        MAGMA_CREAM(378),
        BREWING_STAND_ITEM(379),
        CAULDRON_ITEM(380),
        EYE_OF_ENDER(381),
        SPECKLED_MELON(382),
        MONSTER_EGG(383, 64),
        EXP_BOTTLE(384, 64),
        FIREBALL(385, 64),
        BOOK_AND_QUILL(386, 1),
        WRITTEN_BOOK(387, 16),
        EMERALD(388, 64),
        ITEM_FRAME(389),
        FLOWER_POT_ITEM(390),
        CARROT_ITEM(391),
        POTATO_ITEM(392),
        BAKED_POTATO(393),
        POISONOUS_POTATO(394),
        EMPTY_MAP(395),
        GOLDEN_CARROT(396),
        SKULL_ITEM(397),
        CARROT_STICK(398, 1, 25),
        NETHER_STAR(399),
        PUMPKIN_PIE(400),
        FIREWORK(401),
        FIREWORK_CHARGE(402),
        ENCHANTED_BOOK(403, 1),
        REDSTONE_COMPARATOR(404),
        NETHER_BRICK_ITEM(405),
        QUARTZ(406),
        EXPLOSIVE_MINECART(407, 1),
        HOPPER_MINECART(408, 1),
        PRISMARINE_SHARD(409),
        PRISMARINE_CRYSTALS(410),
        RABBIT(411),
        COOKED_RABBIT(412),
        RABBIT_STEW(413, 1),
        RABBIT_FOOT(414),
        RABBIT_HIDE(415),
        ARMOR_STAND(416, 16),
        IRON_BARDING(417, 1),
        GOLD_BARDING(418, 1),
        DIAMOND_BARDING(419, 1),
        LEASH(420),
        NAME_TAG(421),
        COMMAND_MINECART(422, 1),
        MUTTON(423),
        COOKED_MUTTON(424),
        BANNER(425, 16),
        END_CRYSTAL(426),
        SPRUCE_DOOR_ITEM(427),
        BIRCH_DOOR_ITEM(428),
        JUNGLE_DOOR_ITEM(429),
        ACACIA_DOOR_ITEM(430),
        DARK_OAK_DOOR_ITEM(431),
        CHORUS_FRUIT(432),
        CHORUS_FRUIT_POPPED(433),
        BEETROOT(434),
        BEETROOT_SEEDS(435),
        BEETROOT_SOUP(436, 1),
        DRAGONS_BREATH(437),
        SPLASH_POTION(438, 1),
        SPECTRAL_ARROW(439),
        TIPPED_ARROW(440),
        LINGERING_POTION(441, 1),
        SHIELD(442, 1, 336),
        ELYTRA(443, 1, 431),
        BOAT_SPRUCE(444, 1),
        BOAT_BIRCH(445, 1),
        BOAT_JUNGLE(446, 1),
        BOAT_ACACIA(447, 1),
        BOAT_DARK_OAK(448, 1),
        TOTEM(449, 1),
        SHULKER_SHELL(450),
        IRON_NUGGET(452),
        KNOWLEDGE_BOOK(453, 1),
        GOLD_RECORD(2256, 1),
        GREEN_RECORD(2257, 1),
        RECORD_3(2258, 1),
        RECORD_4(2259, 1),
        RECORD_5(2260, 1),
        RECORD_6(2261, 1),
        RECORD_7(2262, 1),
        RECORD_8(2263, 1),
        RECORD_9(2264, 1),
        RECORD_10(2265, 1),
        RECORD_11(2266, 1),
        RECORD_12(2267, 1);

        private final int id;
        private static MaterialUtil[] byId = new MaterialUtil[383];
        private static final Map<String, MaterialUtil> BY_NAME = Maps.newHashMap();
        private final short durability;

        static {
            MaterialUtil[] var3;
            int var2 = (var3 = MaterialUtil.values()).length;

            for(int var1 = 0; var1 < var2; ++var1) {
                MaterialUtil material = var3[var1];
                if (byId.length > material.id) {
                    byId[material.id] = material;
                } else {
                    byId = Arrays.copyOfRange(byId, 0, material.id + 2);
                    byId[material.id] = material;
                }

                BY_NAME.put(material.name(), material);
            }

        }

        private MaterialUtil(int id) {
            this(id, 64);
        }

        private MaterialUtil(int id, int stack) {
            this(id, stack, 1);
        }



        private MaterialUtil(int id, int stack, int durability) {
            this.id = id;
            this.durability = (short)durability;
        }


        public int getId() {
            return this.id;
        }


        public static MaterialUtil getMaterial(int id) {
            return byId.length > id && id >= 0 ? byId[id] : null;
        }

        public static MaterialUtil getMaterial(String name) {
            return (MaterialUtil)BY_NAME.get(name);
        }

        public static MaterialUtil matchMaterial(String name) {
            Validate.notNull(name, "Name cannot be null");
            MaterialUtil result = null;

            try {
                result = getMaterial(Integer.parseInt(name));
            } catch (NumberFormatException ignored) {
            }

            if (result == null) {
                String filtered = name.toUpperCase(Locale.ENGLISH);
                filtered = filtered.replaceAll("\\s+", "_").replaceAll("\\W", "");
                result = (MaterialUtil)BY_NAME.get(filtered);
            }

            return result;
        }

        public short getDurability() {
            return durability;
        }
    }
}

/**
 *
 */
interface i {
    String s = Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);

    void s();
}