package top.aot.ml.cls;

import cn.hutool.core.convert.Convert;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import setting.MonsterList;
import setting.MonsterTable;
import top.aot.ml.MListMain;
import top.aot.ml.command.AMLCommand;
import top.aot.ml.command.OpenCommand;
import top.aot.ml.command.ReloadCommand;
import top.aot.ml.gui.Button;
import top.aot.ml.listener.KillEntityListener;
import top.aot.ml.listener.PlayerLoginListener;
import top.aot.ml.plugin.APlugin;
import top.aot.ml.utils.Ἐγὼὀκνοίην;
import top.aot.ml.variable.Variable;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：ZhangHe
 * @date ：Created in 2020/5/16 14:59
 * @description：ad
 */
public enum Cls implements i, iex, is, iu, ce {
    C {
        private Map<String, Method> mp = new HashMap<>();

        private Plugin papi = null;
        private String version = "";

        @Override
        public void init() {
            Method[] ms = cls.getClass().getDeclaredMethods();
            for (Method m : ms) {
                mp.put(m.getName(), m);
            }
        }

        /**
         * 获取开发者信息
         */
        public String version() {
            return ex(String.class, Cls.C.s(10));
        }

        public <T> T ex(Class<T> z, String n, Object... o) {
            try {
                return z.cast(mp.get(n).invoke(null, o));
            } catch (Exception e) {
                return null;
            }
        }

        public void ex(String n, Object... o) {
            try {
                mp.get(n).invoke(null, o);
            } catch (Exception ignored) {

            }
        }

        // 常量字符串表
        public String s(int s) {
            return ex(String.class, "s", s);
        }

        public void d(MListMain t) {
            version = Bukkit.getVersion();
            MonsterTable.getMonsterTable();
            MListMain.list = new MonsterList();
            t.getCommand(C.s(1)).setExecutor(new AMLCommand());
            papi = Bukkit.getPluginManager().getPlugin(Cls.C.s(11)); // 用服务端获取PAPI插件
            if (papi != null) {
                boolean b = new Variable().hook();
                if (b) {
                    APlugin.Msg.sendConMsgTrue(C.s(6));
                } else {
                    APlugin.Msg.sendConMsgFalse(C.s(7));
                }
            }
            // 初始化通用nms
            Cls.D.init();
            Convert.toStr(new int[] {1,2});
        }

        public void _kill(EntityDeathEvent e) {
            LivingEntity le = e.getEntity();
            if (!(le instanceof Player)) {
                Player killer = le.getKiller();
                if (C.ex(Boolean.class, "b", killer)) {
                    Role role = Role.getRole(killer);
                    Map<String, Monster> nameTable = MListMain.list.getMonsterNameList();

                    String typeString = le.getType().toString();
                    boolean isNpc = typeString.contains("CUSTOMNPC");
                    String customName = isNpc ? Cls.D.getName(le) : le.getCustomName();
                    if (nameTable.containsKey(customName)) {
                        Monster monster = nameTable.get(customName);
                        if (isNpc == monster.isNpc()) {
                            role.addKillNum(monster);
                            if (!role.isUnlock(monster)) {
                                role.unlockMonster(monster);
                                APlugin.Msg.sendMsgTrue(killer, C.s(5));
                            }
                        }
                    }
                }
            }
        }

        public void co() {
            new OpenCommand("m", 0, "", C.s(2), false);
            new ReloadCommand("r", 0, "", C.s(3), false);
        }

        public void e(MListMain t) {
            t.regListener(new KillEntityListener());
            t.regListener(new PlayerLoginListener());
        }

        public String f() {
            return C.s(4);
        }

        public boolean is17Version() {
            return version.contains("1.7");
        }

    },
    D {
        @Override
        public String getName(Entity entity) {
            return Ce.getName(entity);
        }

        private ce Ce;

        @Override
        public void init() {
            String packageName = Bukkit.getServer().getClass().getPackage().getName();
            if (packageName.contains("v1_7_R4")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            } else if (packageName.contains("v1_7_R2")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_7_R2.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            }  else if (packageName.contains("v1_7_R1")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_7_R1.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            } else if (packageName.contains("v1_8_R1")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            }  else if (packageName.contains("v1_8_R2")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            } else if (packageName.contains("v1_8_R3")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            } else if (packageName.contains("v1_9_R1")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            } else if (packageName.contains("v1_10_R1")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            } else if (packageName.contains("v1_11_R1")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            } else if (packageName.contains("v1_12_R1")) {
                Ce = new ce() {
                    @Override
                    public String getName(Entity entity) {
                        return ((org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity)
                                entity).getHandle().getName();
                    }
                };
            }
        }
    };
    public static Object cls;

    public static String 请勿随意反编译此插件此插件创作者aoisa() {
        return "";
    }

    public static String ts(istr istr) {
        return istr.v();
    }

    public static class C {

        private static final String[] s = new String[]{
                // "插件名称AML|开发人员aoisa|QQ1399620017"  0
                new String(new byte[]{
                        -26, -113, -110, -28, -69, -74, -27, -112, -115,
                        -25, -89, -80, 65, 77, 76, 124, -27, -68, -128,
                        -27, -113, -111, -28, -70, -70, -27, -111, -104,
                        97, 111, 105, 115, 97, 124, 81, 81, 49, 51, 57,
                        57, 54, 50, 48, 48, 49, 55
                }, StandardCharsets.UTF_8),
                // "aml" 1
                new String(new byte[]{
                        97, 109, 108
                }, StandardCharsets.UTF_8),
                // "打开怪物图鉴" 2
                new String(new byte[]{
                        -26, -119, -109, -27, -68, -128, -26, -128, -86,
                        -25, -119, -87, -27, -101, -66, -23, -119, -76
                }, StandardCharsets.UTF_8),
                // "重载怪物图鉴" 3
                new String(new byte[]{
                        -23, -121, -115, -24, -67, -67, -26, -128, -86,
                        -25, -119, -87, -27, -101, -66, -23, -119, -76
                }, StandardCharsets.UTF_8),
                // "怪物图鉴" 4
                new String(new byte[]{
                        -62, -89, 99, -26, -128, -86, -25, -119, -87,
                        -27, -101, -66, -23, -119, -76
                }, StandardCharsets.UTF_8),
                // "恭喜你已经解锁一项怪物图鉴,请使用/aml命令打开怪物图鉴查看" 5
                new String(new byte[]{
                        -26, -127, -83, -27, -106, -100, -28, -67, -96,
                        -27, -73, -78, -25, -69, -113, -24, -89, -93, -23,
                        -108, -127, -28, -72, -128, -23, -95, -71, -26,
                        -128, -86, -25, -119, -87, -27, -101, -66, -23,
                        -119, -76, 44, -24, -81, -73, -28, -67, -65, -25,
                        -108, -88, 47, 97, 109, 108, -27, -111, -67, -28,
                        -69, -92, -26, -119, -109, -27, -68, -128, -26, -128,
                        -86, -25, -119, -87, -27, -101, -66, -23, -119, -76,
                        -26, -97, -91, -25, -100, -117
                }, StandardCharsets.UTF_8),
                // "变量添加成功！" 6
                new String(new byte[]{
                        -27, -113, -104, -23, -121, -113, -26, -73, -69,
                        -27, -118, -96, -26, -120, -112, -27, -118, -97,
                        -17, -68, -127
                }, StandardCharsets.UTF_8),
                // "变量添加失败！" 7
                new String(new byte[]{
                        -27, -113, -104, -23, -121, -113, -26, -73, -69,
                        -27, -118, -96, -27, -92, -79, -24, -76, -91,
                        -17, -68, -127
                }, StandardCharsets.UTF_8),
                // "ml" 8
                new String(new byte[]{109, 108}, StandardCharsets.UTF_8),
                // "系统" 9
                new String(new byte[]{-25, -77, -69, -25, -69, -97},
                        StandardCharsets.UTF_8),
                // "version" 10
                new String(new byte[]{118, 101, 114, 115, 105, 111, 110},
                        StandardCharsets.UTF_8),
                // "PlaceholderAPI" 11
                new String(new byte[]{80, 108, 97, 99, 101, 104, 111, 108,
                        100, 101, 114, 65, 80, 73},
                        StandardCharsets.UTF_8),
        };

        public static String s(int S) {
            return s[S];
        }

        public static boolean b(Object object) {
            return object != null;
        }

        public static String i(String string) {
            return string;
        }

        public static <T> T j(T t) {
            return t;
        }

        // 开发者说明
        public static String version() {
            return s[0];
        }

    }

    public static class CDef extends ClassLoader {
        private static CDef cdef;

        static {
            CDef.C.c.init();
        }

        public enum C implements i, iex {
            c {
                @Override
                public void init() {
                    cdef = new CDef();
                }

                public Class<?> c(byte[] b, int len) {
                    return (Class<?>) cdef.defineClass(Cls.class.getName() + "$C", b, 0, len);
                }
            };
        }
    }

    public static class MLGui extends APlugin.Gui {

        private MonsterTable table;
        private String listType;

        public MLGui(Player owner) {
            super(owner, Cls.C.f(), 6);
        }

        @Override
        public boolean clickRegion(String clickedRegionName, ClickType clickType, ItemStack itemStack) {
            return false;
        }

        @Override
        protected void initWindow() {
            setListType("aj");
            setTable(MonsterTable.getMonsterTable());
        }

        @Override
        public void updateWindow() {
            int[] sizes = table.getSizes();
            if (sizes[0] > 0) {
                Button<MLGui> ajButton = new Button<MLGui>(this) {

                    @Override
                    protected String buttonName() {

                        return C.ex(String.class, "i", table.getAjName());
                    }

                    @Override
                    protected int itemId() {
                        return table.getAjItemId();
                    }

                    @Override
                    protected String explain() {
                        return "§a查看" + C.ex(String.class, "i", table.getAjName());
                    }

                    @Override
                    protected short secondID() {
                        return (short) MLGui.this.table.getAjId();
                    }
                };
                ajButton.setClickListener((APlugin.LeftClickListener) () -> {
                    setListType("aj");
                    APlugin.GuiBase.openWindow(getOwner(), new ListGui(MLGui.this, getOwner()));
                });
                setAssembly(table.getAjIndex(), ajButton);
            }
            if (sizes[1] > 0) {
                Button<MLGui> bjButton = new Button<MLGui>(this) {

                    @Override
                    protected String buttonName() {

                        return C.ex(String.class, "i", table.getBjName());
                    }

                    @Override
                    protected int itemId() {
                        return table.getBjItemId();
                    }

                    @Override
                    protected String explain() {
                        return "§a查看" + C.ex(String.class, "i", table.getBjName());
                    }

                    @Override
                    protected short secondID() {
                        return (short) MLGui.this.table.getBjId();
                    }
                };
                bjButton.setClickListener(new APlugin.LeftClickListener() {

                    @Override
                    public void leftClick() {
                        setListType("bj");
                        APlugin.GuiBase.openWindow(getOwner(), new ListGui(MLGui.this, getOwner()));
                    }
                });
                setAssembly(table.getBjIndex(), bjButton);
            }
            if (sizes[2] > 0) {
                Button<MLGui> cjButton = new Button<MLGui>(this) {

                    @Override
                    protected String buttonName() {

                        return C.ex(String.class, "i", table.getCjName());
                    }

                    @Override
                    protected int itemId() {
                        return table.getCjItemId();
                    }

                    @Override
                    protected String explain() {
                        return "§a查看" + C.ex(String.class, "i", table.getCjName());
                    }

                    @Override
                    protected short secondID() {
                        return (short) MLGui.this.table.getCjId();
                    }

                };
                cjButton.setClickListener((APlugin.LeftClickListener) () -> {
                    setListType("cj");
                    APlugin.GuiBase.openWindow(getOwner(), new ListGui(MLGui.this, getOwner()));
                });
                setAssembly(table.getCjIndex(), cjButton);
            }
            if (sizes[3] > 0) {
                Button<MLGui> djButton = new Button<MLGui>(this) {

                    @Override
                    protected String buttonName() {

                        return C.ex(String.class, "i", table.getDjName());
                    }

                    @Override
                    protected int itemId() {
                        return table.getDjItemId();
                    }

                    @Override
                    protected String explain() {
                        return "§a查看" + C.ex(String.class, "i", table.getDjName());
                    }

                    @Override
                    protected short secondID() {
                        return (short) MLGui.this.table.getDjId();
                    }

                };
                djButton.setClickListener((APlugin.LeftClickListener) () -> {
                    setListType("dj");
                    APlugin.GuiBase.openWindow(getOwner(), new ListGui(MLGui.this, getOwner()));
                });
                setAssembly(table.getDjIndex(), djButton);
            }

            if (sizes[4] > 0) {
                Button<MLGui> ejButton = new Button<MLGui>(this) {

                    @Override
                    protected String buttonName() {

                        return C.ex(String.class, "i", table.getEjName());
                    }

                    @Override
                    protected int itemId() {
                        return table.getEjItemId();
                    }

                    @Override
                    protected String explain() {
                        return "§a查看" + C.ex(String.class, "i", table.getEjName());
                    }

                    @Override
                    protected short secondID() {
                        return (short) MLGui.this.table.getEjId();
                    }
                };
                ejButton.setClickListener((APlugin.LeftClickListener) () -> {
                    setListType("ej");
                    APlugin.GuiBase.openWindow(getOwner(), new ListGui(MLGui.this, getOwner()));
                });
                setAssembly(table.getEjIndex(), ejButton);
            }
            if (sizes[5] > 0) {
                Button<MLGui> fjButton = new Button<MLGui>(this) {

                    @Override
                    protected String buttonName() {

                        return C.ex(String.class, "i", table.getFjName());
                    }

                    @Override
                    protected int itemId() {
                        return table.getFjItemId();
                    }

                    @Override
                    protected String explain() {
                        return "§a查看" + C.ex(String.class, "i", table.getFjName());
                    }

                    @Override
                    protected short secondID() {
                        return (short) MLGui.this.table.getFjId();
                    }

                };
                fjButton.setClickListener((APlugin.LeftClickListener) () -> {
                    setListType("fj");
                    APlugin.GuiBase.openWindow(getOwner(), new ListGui(MLGui.this, getOwner()));
                });
                setAssembly(table.getFjIndex(), fjButton);
            }
        }

        @Override
        public boolean closeEvent() {
            return false;
        }

        public String getListType() {
            return listType;
        }

        public void setListType(String listType) {
            this.listType = listType;
        }

        public MonsterTable getTable() {
            return table;
        }

        public void setTable(MonsterTable table) {
            this.table = table;
        }

    }

    public static class ListGui extends APlugin.Gui {

        MLGui mlGui;
        List<String> listName;

        public ListGui(APlugin.Gui beforeGui, Player owner) {
            super(beforeGui, owner, "§e怪物列表", 6);
        }

        @Override
        public boolean clickRegion(String clickedRegionName, ClickType clickType, ItemStack itemStack) {
            return false;
        }

        @Override
        protected void initWindow() {
            mlGui = (MLGui) getBeforeGui();
            switch (mlGui.getListType()) {
                case "aj":
                    listName = mlGui.getTable().getAj();
                    break;
                case "bj":
                    listName = mlGui.getTable().getBj();
                    break;
                case "cj":
                    listName = mlGui.getTable().getCj();
                    break;
                case "dj":
                    listName = mlGui.getTable().getDj();
                    break;
                case "ej":
                    listName = mlGui.getTable().getEj();
                    break;
                case "fj":
                    listName = mlGui.getTable().getFj();
                    break;

                default:
                    break;
            }
        }

        @Override
        public void updateWindow() {
            setAssembly(9, 6, APlugin.BackButton.getButton(this));
            int index = 0;
            Role role = Role.getRole(getOwnerName());
            for (String name : listName) {
                Monster monster = MListMain.list.getMonsterById(name);
                if (monster != null) {
                    boolean unlock = role.isUnlock(monster);
                    APlugin.AssemblyDynamic<ListGui> monsterAssembly = new APlugin.AssemblyDynamic<ListGui>(this) {

                        @Override
                        protected short secondID() {
                            return (short) (unlock ? monster.getTouId() : 0);
                        }

                        @Override
                        protected void init(ListGui gui, ItemMeta itemMeta) {
                            itemMeta.setDisplayName(monster.getName() + "§a§l【图鉴】");
                            List<String> lore = new ArrayList<>();
                            lore.add("");
                            if (role.isUnlock(monster)) {
                                List<String> customDesc = monster.getCustomDesc();
                                if (customDesc.size() > 0) {
                                    lore.addAll(customDesc);
                                } else {
                                    lore.add("§f§l[怪物介绍]");
                                    lore.addAll(monster.getDesc());
                                    lore.add("§f§l[怪物属性]");
                                    lore.addAll(monster.getAttrs());
                                    lore.add("§a生命值 " + monster.getHealth());
                                    lore.add("§f§l[怪物掉落]");
                                    lore.addAll(monster.getDrops());
                                    lore.add("§d§l[所在地点:" + monster.getLocation() + "]");
                                }
                                if (monster.getOnlyList().size() > 0 && !role.isReceive(monster)) {
                                    lore.add(monster.getOnlyExplain());
                                    lore.add("§a§l>左键点击领取奖励<§b 可领取一次");
                                } else if (monster.getRepeatList().size() > 0) {
                                    lore.add(monster.getRepeatExplain());
                                    lore.add("§a§l>左键点击执行<§b 图鉴解锁功能");
                                }
                            } else {
                                lore.add("§c*击杀一次此怪物后解锁此图鉴*");
                            }
                            itemMeta.setLore(lore);
                        }

                        @Override
                        protected Material material() {
                            return unlock ? Material.SKULL_ITEM : (C.is17Version() ? Material.ARROW : Material.BARRIER);
                        }

                        @Override
                        protected int itemId() {
                            return unlock ? monster.getItemId() : (C.is17Version() ? Material.ARROW.getId() : Material.BARRIER.getId());
                        }
                    };
                    if (role.isUnlock(monster)) {
                        if (monster.getOnlyList().size() > 0 && !role.isReceive(monster)) {
                            monsterAssembly.setClickListener((APlugin.LeftClickListener) () -> {
                                List<String> onlyList = monster.getOnlyList();
                                Player player = getOwner();
                                role.receive(monster);
                                if (player.isOp()) {
                                    exeOp(player, onlyList);
                                } else {
                                    exe(player, onlyList);
                                }
                                player.closeInventory();
                            });
                        } else if (monster.getRepeatList().size() > 0) {
                            monsterAssembly.setClickListener((APlugin.LeftClickListener) () -> {
                                List<String> repeatList = monster.getRepeatList();
                                Player player = getOwner();
                                if (player.isOp()) {
                                    exeOp(player, repeatList);
                                } else {
                                    exe(player, repeatList);
                                }
                                getOwner().closeInventory();
                            });
                        }
                    }
                    setAssembly(index++, monsterAssembly);
                }
            }
        }

        private void exe(Player player, List<String> onlyList) {
            Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
            try {
                Ἐγὼὀκνοίην.Nεοπτόλεμος(player);
                for (String cmd : onlyList) {
                    if (cmd.substring(0, 1).equals("/")) {
                        Bukkit.dispatchCommand(player, cmd.substring(1));
                    } else {
                        Bukkit.dispatchCommand(APlugin.serverSender,
                                cmd.replaceAll("<p>", player.getName()));
                    }
                }
            } catch (Exception ignored) {

            } finally {
                Ἐγὼὀκνοίην.Εἴθε(player);
            }
        }

        private void exeOp(Player player, List<String> onlyList) {
            Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);
            try {
                for (String cmd : onlyList) {
                    if (cmd.substring(0, 1).equals("/")) {
                        Bukkit.dispatchCommand(player, cmd.substring(1));
                    } else {
                        Bukkit.dispatchCommand(APlugin.serverSender,
                                cmd.replaceAll("<p>", player.getName()));
                    }
                }
            } catch (Exception ignored) {

            }
        }

        @Override
        public boolean closeEvent() {

            return false;
        }

    }

    public static class Role extends APlugin.AsxConfig {

        private static Map<String, Role> roleList = new HashMap<>();

        public static Role getRole(Player player) {
            return getRole(player.getName());
        }

        public static Role getRole(String name) {
            if (!roleList.containsKey(name)) {
                roleList.put(name, new Role(name));
            }
            return roleList.get(name);
        }

        public static void reloadRole(Player player) {
            reloadRole(player.getName());
        }

        public static void reloadRole(String name) {
            roleList.put(name, new Role(name));
        }

        private List<String> monsterList;
        private List<String> receiveList;

        public Role(String fileName) {
            super(fileName);
        }

        @Override
        protected void defaultValue() {
            monsterList = new ArrayList<>();
            receiveList = new ArrayList<>();
        }

        @Override
        protected void saveConfig(FileConfiguration config) {
            config.set("monsterList", monsterList);
            config.set("receiveList", receiveList);
        }

        @Override
        protected void loadConfig(FileConfiguration config) {
            monsterList = config.getStringList("monsterList");
            receiveList = config.getStringList("receiveList");
        }

        /**
         * 解锁怪物
         */
        public void unlockMonster(Monster monster) {
            if (!monsterList.contains(monster.getId())) {
                monsterList.add(monster.getId());
                update();
            }
        }

        /**
         * 锁定怪物
         */
        public void lockMonster(Monster monster) {
            if (monsterList.contains(monster.getId())) {
                monsterList.remove(monster.getId());
                update();
            }
        }

        /**
         * 领取奖励
         */
        public void receive(Monster monster) {
            if (!receiveList.contains(monster.getId())) {
                receiveList.add(monster.getId());
                update();
            }
        }

        /**
         * 判断是否解锁
         */
        public boolean isUnlock(Monster monster) {
            return monsterList.contains(monster.getId());
        }

        /**
         * 判断是否领取
         */
        public boolean isReceive(Monster monster) {
            return receiveList.contains(monster.getId());
        }

        public int getUnlockNum() {
            return monsterList.size();
        }

        /**
         * 获取击杀数量
         * */
        public int getKillNum(Monster monster) {
            return customConfig.getInt("killNumList." + monster.getId(), 0);
        }

        /**
         * 添加击杀数量
         * */
        public void addKillNum(Monster monster) {
            setKillNum(monster, getKillNum(monster) + 1);
            update();
        }

        /**
         * 添加击杀数量
         * */
        public void reduceKillNum(Monster monster, int num) {
            setKillNum(monster, getKillNum(monster) - num);
            update();
        }

        /**
         * 设置击杀数量
         * */
        private void setKillNum(Monster monster, int num) {
            customConfig.set("killNumList." + monster.getId(), num);
        }
    }

    public static class Monster {

        private String name; // 怪物名字
        private List<String> attrs;
        private List<String> desc;
        private List<String> drops;
        private String location;
        private int itemId; // 物品id
        private int touId; // 头id
        private final String id;
        private String health;

        private List<String> onlyList; // 单次执行的命令列表
        private String onlyExplain; // 单次奖励的提示信息
        private List<String> repeatList; // 无限次奖励的命令列表
        private String repeatExplain; // 无限制奖励的提示信息

        private List<String> customDesc; // 自定义提示

        private boolean npc; // 是否为npc

        public Monster(String key) {
            this.id = key;
        }

        public String getName() {
            return name;
        }

        public Monster setName(String name) {
            this.name = name;
            return this;
        }

        public List<String> getAttrs() {
            return attrs;
        }

        public Monster setAttrs(List<String> attrs) {
            this.attrs = attrs;
            return this;
        }

        public List<String> getDesc() {
            return desc;
        }

        public Monster setDesc(List<String> desc) {
            this.desc = desc;
            return this;
        }

        public String getId() {
            return id;
        }

        public int getTouId() {
            return touId;
        }

        public Monster setTouId(int touId) {
            this.touId = touId;
            return this;
        }

        public List<String> getDrops() {
            return drops;
        }

        public Monster setDrops(List<String> drops) {
            this.drops = drops;
            return this;
        }

        public String getLocation() {
            return location;
        }

        public Monster setLocation(String location) {
            this.location = location;
            return this;
        }

        public String getHealth() {
            return health;
        }

        public Monster setHealth(String health) {
            this.health = health;
            return this;
        }

        public List<String> getOnlyList() {
            return onlyList;
        }

        public Monster setOnlyList(List<String> onlyList) {
            this.onlyList = onlyList;
            return this;
        }

        public String getOnlyExplain() {
            return onlyExplain;
        }

        public Monster setOnlyExplain(String onlyExplain) {
            this.onlyExplain = onlyExplain;
            return this;
        }

        public List<String> getRepeatList() {
            return repeatList;
        }

        public Monster setRepeatList(List<String> repeatList) {
            this.repeatList = repeatList;
            return this;
        }

        public String getRepeatExplain() {
            return repeatExplain;
        }

        public Monster setRepeatExplain(String repeatExplain) {
            this.repeatExplain = repeatExplain;
            return this;
        }

        public List<String> getCustomDesc() {
            return customDesc;
        }

        public Monster setCustomDesc(List<String> customDesc) {
            this.customDesc = customDesc;
            return this;
        }

        public boolean isNpc() {
            return npc;
        }

        public Monster setNpc(boolean npc) {
            this.npc = npc;
            return this;
        }

        public int getItemId() {
            return itemId;
        }

        public Monster setItemId(int itemId) {
            this.itemId = itemId;
            return this;
        }
    }

}

interface i {

    String s = Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);

    void init();

}

interface iex {

    String s = Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);

    default <T> T ex(Class<T> z, String n, Object... o) {
        return null;
    }

    default void ex(String n, Object... o) {

    }

    default Class<?> c(byte[] b, int len) {
        return null;
    }
}

interface is {

    String s = Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);

    default String version() {
        return null;
    }

    default String s(int s) {
        return null;
    }

    default String f() {
        return null;
    }

}

interface iu {

    String s = Cls.ts(Cls::请勿随意反编译此插件此插件创作者aoisa);

    // 关联onEnable()
    default void d(MListMain t) {
    }

    // 关联击杀处理
    default void _kill(EntityDeathEvent e) {

    }

    // 命令注册
    default void co() {

    }

    // 监听器注册
    default void e(MListMain t) {

    }

    // 版本判断
    default boolean is17Version() {
        return false;
    }

}

// 通用实体接口
interface ce {

    default String getName(Entity entity) {
        return null;
    }
}